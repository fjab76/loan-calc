package fjab.loancalc.service;

import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.log4j.Logger;

import fjab.loancalc.service.RepaymentPlan.OverpaymentType;

public class LoanServiceImp implements LoanService {
	
	private static final Logger LOGGER = Logger.getLogger(LoanServiceImp.class);
	
	private LoanServiceHelper loanServiceHelper;
	
	public LoanServiceImp(){
		super();
		loanServiceHelper = new LoanServiceHelper();
	}

	/**
	 * When calling this method, make sure that the interest rate corresponds to the payment period. For instance, if the
	 * interest rate is referred to a year and the payment happens every month, the interest rate must be changed to a
	 * monthly basis before being passed into this method.
	 * 
	 * @param interestRateForEveryPeriod Interest rate for every payment period
	 * @param capitalToPayBack Capital to pay back over the periods given by numberPaymentPeriods
	 * @param numberPaymentPeriods If the payment happens every month, this is the number of months of the loan
	 * @return
	 */
	private BigDecimal workOutPeriodicPayment(BigDecimal interestRateForEveryPeriod,BigDecimal capitalToPayBack,int numberPaymentPeriods){
		
		//simplifying the name of the variables to make the formula below more readable
		BigDecimal i = interestRateForEveryPeriod;
		BigDecimal p = capitalToPayBack;
		int n = numberPaymentPeriods;
		
		//periodic payment
		//BigDecimal periodicPayment = BigDecimal.valueOf(p*(i+(i/(Math.pow(1+i,n)-1))));
		BigDecimal periodicPayment = p.multiply(i.add(i.divide(BigDecimal.ONE.add(i).pow(n).subtract(BigDecimal.ONE),MathContext.DECIMAL128)));
		return periodicPayment;
	}

	
	private void calculateRepaymentPlanWithFixedPayment(RepaymentPlan repaymentPlan, BigDecimal interestRateForEveryPeriod) {
		
		BigDecimal periodicPayment = repaymentPlan.getPeriodicPayment();
		
		BigDecimal capitalToPayBackAtStartOfPeriod = repaymentPlan.getLoanAmount();
		
		int paymentNumber = 0;
		int periodNumber = 0;
		
		BigDecimal cumulativeCapitalPaidOff = BigDecimal.ZERO;
		BigDecimal cumulativeInterest = BigDecimal.ZERO;
		BigDecimal totalCostToDate = BigDecimal.ZERO;
		
		//In case there is already data in the repayment plan (e.g. after an overpayment), it is necessary to initialise
		//the cumulative values
		Repayment lastRepayment = loanServiceHelper.getLastRepayment(repaymentPlan);		
		if(lastRepayment!=null){
			cumulativeCapitalPaidOff = lastRepayment.getCumulativeCapitalPaidOff();
			cumulativeInterest = lastRepayment.getCumulativeInterest();
			totalCostToDate = lastRepayment.getTotalCostToDate();
			paymentNumber = lastRepayment.getPaymentNumber();
			periodNumber = lastRepayment.getPeriodNumber();
		}
		
		while(capitalToPayBackAtStartOfPeriod.compareTo(BigDecimal.ZERO)==1){
			
			//Last payment may be less than scheduled
			BigDecimal paymentOfThisPeriod = capitalToPayBackAtStartOfPeriod.multiply((BigDecimal.ONE.add(interestRateForEveryPeriod)));
			if(paymentOfThisPeriod.compareTo(periodicPayment)==-1){
				periodicPayment = paymentOfThisPeriod;
			}
			
			BigDecimal interestPaid = capitalToPayBackAtStartOfPeriod.multiply(interestRateForEveryPeriod);
			BigDecimal capitalPaidOff = periodicPayment.subtract(interestPaid);
			BigDecimal capitalToPayBackAtEndOfPeriod = capitalToPayBackAtStartOfPeriod.subtract(capitalPaidOff);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);			
			
			Repayment repayment = new Repayment();
			repaymentPlan.addRepayment(repayment);
			
			repayment.setPaymentNumber(++paymentNumber);
			repayment.setPeriodNumber(++periodNumber);
			repayment.setStartBalance(capitalToPayBackAtStartOfPeriod);
			repayment.setPayment(periodicPayment);
			repayment.setCapitalPaidOff(capitalPaidOff);
			repayment.setInterestPaid(interestPaid);
			repayment.setEndBalance(capitalToPayBackAtEndOfPeriod);
			repayment.setCumulativeCapitalPaidOff(cumulativeCapitalPaidOff);
			repayment.setCumulativeInterest(cumulativeInterest);
			repayment.setTotalCostToDate(totalCostToDate);
			
			capitalToPayBackAtStartOfPeriod = capitalToPayBackAtEndOfPeriod;
			
			//System.out.println(repayment.toString());
		}
		
		repaymentPlan.setLoanLength(periodNumber);
	}

	private void calculateRepaymentPlanWithFixedLength(RepaymentPlan repaymentPlan, BigDecimal interestRateForEveryPeriod) {
		
		BigDecimal periodicPayment = workOutPeriodicPayment(interestRateForEveryPeriod,repaymentPlan.getLoanAmount(), repaymentPlan.getLoanLength());
		repaymentPlan.setPeriodicPayment(periodicPayment);
		
		BigDecimal capitalToPayBackAtStartOfPeriod = repaymentPlan.getLoanAmount();
		
		int paymentNumber = 0;
		int periodNumber = 0;
		
		BigDecimal cumulativeCapitalPaidOff = BigDecimal.ZERO;
		BigDecimal cumulativeInterest = BigDecimal.ZERO;
		BigDecimal totalCostToDate = BigDecimal.ZERO;
		
		//In case there is already data in the repayment plan (e.g. after an overpayment), it is necessary to initialise
		//the cumulative values
		Repayment lastRepayment = loanServiceHelper.getLastRepayment(repaymentPlan);		
		if(lastRepayment!=null){
			cumulativeCapitalPaidOff = lastRepayment.getCumulativeCapitalPaidOff();
			cumulativeInterest = lastRepayment.getCumulativeInterest();
			totalCostToDate = lastRepayment.getTotalCostToDate();
			paymentNumber = lastRepayment.getPaymentNumber();
			periodNumber = lastRepayment.getPeriodNumber();
		}
		
		for(int j=0;j<repaymentPlan.getLoanLength();j++){
			
			BigDecimal interestPaid = capitalToPayBackAtStartOfPeriod.multiply(interestRateForEveryPeriod);
			BigDecimal capitalPaidOff = periodicPayment.subtract(interestPaid);
			BigDecimal capitalToPayBackAtEndOfPeriod = capitalToPayBackAtStartOfPeriod.subtract(capitalPaidOff);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);			
			
			Repayment repayment = new Repayment();
			repaymentPlan.addRepayment(repayment);
			
			repayment.setPaymentNumber(++paymentNumber);
			repayment.setPeriodNumber(++periodNumber);
			repayment.setStartBalance(capitalToPayBackAtStartOfPeriod);
			repayment.setPayment(periodicPayment);
			repayment.setCapitalPaidOff(capitalPaidOff);
			repayment.setInterestPaid(interestPaid);
			repayment.setEndBalance(capitalToPayBackAtEndOfPeriod);
			repayment.setCumulativeCapitalPaidOff(cumulativeCapitalPaidOff);
			repayment.setCumulativeInterest(cumulativeInterest);
			repayment.setTotalCostToDate(totalCostToDate);
			
			capitalToPayBackAtStartOfPeriod = capitalToPayBackAtEndOfPeriod;
			
			//System.out.println(repayment.toString());
		}
	}
	
	private void calculateSimpleRepaymentPlan(RepaymentPlan repaymentPlan) throws Exception{
		
		if(!checkLoanCoordinates(repaymentPlan.getAnnualInterestRate(),repaymentPlan.getNumberAnnualPayments(),repaymentPlan.getLoanAmount(),repaymentPlan.getLoanLength(),repaymentPlan.getPeriodicPayment())){
			throw new Exception("loan coordinates are wrong");		
		}
	
		
		BigDecimal interestRateForEveryPeriod = getInterestRateForEveryPeriod(repaymentPlan.getAnnualInterestRate(),repaymentPlan.getNumberAnnualPayments());
	
		if(repaymentPlan.getLoanLength()!=null){
			calculateRepaymentPlanWithFixedLength(repaymentPlan,interestRateForEveryPeriod);
		}
		else if(repaymentPlan.getPeriodicPayment()!=null){
			calculateRepaymentPlanWithFixedPayment(repaymentPlan,interestRateForEveryPeriod);
		}
		else{
			//This case should not be possible if the method checkLoanCoordinates works correctly
			throw new Exception("loan coordinates are wrong: loan length and periodic payment are exclusive");
		}
	}

	@Override
	public void calculateRepaymentPlan(RepaymentPlan repaymentPlan) throws Exception {
		
		/*
		 * A repayment with overpayment can be split up in 2 regular repayment plans: one before the overpayment and another
		 * after the overpayment
		 * Repayment plan with overpayment = regular repayment plan 1 + overpayment + regular repayment plan 2
		 */
		
		//STEP 1: regular repayment plan 1 
		/*Checking the loan coordinates that are necessary to work out the repayment plan:
		(1) annual interest rate
		(2) number of annual payments
		(3) loan amount
		(4) loan length (expressed as number of payments)
		(5) periodic payment --> loan length and periodic payment are mutually exclusive
	 */
		calculateSimpleRepaymentPlan(repaymentPlan);
		
		if(checkOverpaymentCoordinates(repaymentPlan.getOverpaymentAmount(),repaymentPlan.getOverpaymentPeriodNumber(),repaymentPlan.getOverpaymentType())){

			//STEP 2: overpayment
			/*
			 * If overpayment happens in payment period N, all the payments greater than N must be discarded
			 * Besides, in payment period N there are 2 payments: the scheduled payment plus the overpayment
			 */		
			Repayment lastRepaymentBeforeOverpayment = loanServiceHelper.removeRepaymentsAfterOverpaymentPeriodNumber(repaymentPlan,repaymentPlan.getOverpaymentPeriodNumber());
			loanServiceHelper.createOverpayment(lastRepaymentBeforeOverpayment,repaymentPlan);
			
			//STEP 3: regular repayment plan 
			loanServiceHelper.recalculateLoanCoordinatesAfterOverpayment(repaymentPlan);
			calculateSimpleRepaymentPlan(repaymentPlan);
		}
		
		Repayment lastRepayment = repaymentPlan.getRepaymentPlan().get(repaymentPlan.getRepaymentPlan().size()-1);
		repaymentPlan.setCumulativeCapitalPaidOff(lastRepayment.getCumulativeCapitalPaidOff());
		repaymentPlan.setCumulativeInterest(lastRepayment.getCumulativeInterest());
		repaymentPlan.setTotalCost(lastRepayment.getTotalCostToDate());
		
		LOGGER.info("========> REPAYMENT PLAN");
		for(Repayment repayment : repaymentPlan.getRepaymentPlan()){
			LOGGER.info(repayment.toString());			
		}
		LOGGER.info("========> END REPAYMENT PLAN");
	}
	

	private boolean checkOverpaymentCoordinates(BigDecimal overpaymentAmount,Integer overpaymentPeriodNumber, OverpaymentType overpaymentType) {
		return overpaymentAmount!=null && overpaymentPeriodNumber!=null && overpaymentType!=null;
	}

	private boolean checkLoanCoordinates(BigDecimal annualInterestRate,Integer numberAnnualPayments, BigDecimal loanAmount, Integer loanLength, BigDecimal periodicPayment) {
		
		return annualInterestRate!=null && numberAnnualPayments!=null && loanAmount!=null && (loanLength!=null || periodicPayment!=null)
				&& (loanLength==null || periodicPayment==null);
	}
	


	private BigDecimal getInterestRateForEveryPeriod(BigDecimal annualInterestRate,Integer numberAnnualPayments) {	
		//It is necessary to use Math.pow as BigDecimal.pow only accepts integer exponents
		//return new BigDecimal(Math.pow(1+annualInterestRate.doubleValue(),1./numberAnnualPayments.doubleValue())-1,MathContext.DECIMAL128);
		return annualInterestRate.divide(new BigDecimal(numberAnnualPayments), MathContext.DECIMAL128);
	}

	public void setLoanServiceHelper(LoanServiceHelper loanServiceHelper) {
		this.loanServiceHelper = loanServiceHelper;
	}
}
