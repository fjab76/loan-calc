package fjab.loancalc.french;

import org.apache.log4j.Logger;

import fjab.loancalc.LoanService;
import fjab.loancalc.french.model.Repayment;
import fjab.loancalc.french.model.RepaymentPlan;
import fjab.loancalc.french.model.RepaymentPlan.OverpaymentType;

public class LoanServiceImp implements LoanService {
	
	private static final Logger LOGGER = Logger.getLogger(LoanServiceImp.class);
	
	private LoanServiceHelper loanServiceHelper;

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
	//@Override
	public double workOutPeriodicPayment(double interestRateForEveryPeriod,double capitalToPayBack,int numberPaymentPeriods){
		
		//simplifying the name of the variables to make the formula below more readable
		double i = interestRateForEveryPeriod;
		double p = capitalToPayBack;
		int n = numberPaymentPeriods;
		
		//periodic payment
		double periodicPayment = p*(i+(i/(Math.pow(1+i,n)-1)));
		return periodicPayment;
	}

	@Override
	public void calculateRepaymentPlan(RepaymentPlan repaymentPlan) throws Exception {
		
		/*Checking the loan coordinates that are necessary to work out the repayment plan:
			(1) annual interest rate
			(2) number of annual payments
			(3) loan amount
			(4) loan length (expressed as number of payments)
			(5) periodic payment --> loan length and periodic payment are mutually exclusive
		 */
		if(!checkLoanCoordinates(repaymentPlan.getAnnualInterestRate(),repaymentPlan.getNumberAnnualPayments(),repaymentPlan.getLoanAmount(),repaymentPlan.getLoanLength(),repaymentPlan.getPeriodicPayment())){
			throw new Exception("loan coordinates are wrong");		
		}

		
		double interestRateForEveryPeriod = getInterestRateForEveryPeriod(repaymentPlan.getAnnualInterestRate(),repaymentPlan.getNumberAnnualPayments());

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
	
	private void calculateRepaymentPlanWithFixedPayment(RepaymentPlan repaymentPlan, double interestRateForEveryPeriod) {
		
		double periodicPayment = repaymentPlan.getPeriodicPayment();
		
		double capitalToPayBackAtStartOfPeriod = repaymentPlan.getLoanAmount();
		
		int paymentNumber = 0;
		int periodNumber = 0;
		
		double cumulativeCapitalPaidOff = 0;
		double cumulativeInterest = 0;
		double totalCostToDate = 0;
		
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
		
		while(capitalToPayBackAtStartOfPeriod>0){
			
			//Last payment may be less than scheduled
			double paymentOfThisPeriod = capitalToPayBackAtStartOfPeriod*(1+interestRateForEveryPeriod);
			if(paymentOfThisPeriod<periodicPayment){
				periodicPayment = paymentOfThisPeriod;
			}
			
			double interestPaid = capitalToPayBackAtStartOfPeriod * interestRateForEveryPeriod;
			double capitalPaidOff = periodicPayment - interestPaid;
			double capitalToPayBackAtEndOfPeriod = capitalToPayBackAtStartOfPeriod - capitalPaidOff;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;			
			
			Repayment repayment = new Repayment();
			repaymentPlan.getRepaymentPlan().add(repayment);
			
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

	private void calculateRepaymentPlanWithFixedLength(RepaymentPlan repaymentPlan, double interestRateForEveryPeriod) {
		
		double periodicPayment = workOutPeriodicPayment(interestRateForEveryPeriod,repaymentPlan.getLoanAmount(), repaymentPlan.getLoanLength());
		repaymentPlan.setPeriodicPayment(periodicPayment);
		
		double capitalToPayBackAtStartOfPeriod = repaymentPlan.getLoanAmount();
		
		int paymentNumber = 0;
		int periodNumber = 0;
		
		double cumulativeCapitalPaidOff = 0;
		double cumulativeInterest = 0;
		double totalCostToDate = 0;
		
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
			
			double interestPaid = capitalToPayBackAtStartOfPeriod * interestRateForEveryPeriod;
			double capitalPaidOff = periodicPayment - interestPaid;
			double capitalToPayBackAtEndOfPeriod = capitalToPayBackAtStartOfPeriod - capitalPaidOff;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;			
			
			Repayment repayment = new Repayment();
			repaymentPlan.getRepaymentPlan().add(repayment);
			
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

	//@Override
	public void calculateRepaymentPlanWithOverpayment(RepaymentPlan repaymentPlan) throws Exception {
		
		//Checking overpayment coordinates
		if(!checkOverpaymentCoordinates(repaymentPlan.getOverpaymentAmount(),repaymentPlan.getOverpaymentPeriodNumber(),repaymentPlan.getOverpaymentType())){
			throw new Exception();
		}
		
		/*
		 * A repayment with overpayment can be split up in 2 regular repayment plans: one before the overpayment and another
		 * after the overpayment
		 * Repayment plan with overpayment = regular repayment plan 1 + overpayment + regular repayment plan 2
		 */
		
		//STEP 1: regular repayment plan 1 
		calculateRepaymentPlan(repaymentPlan);
		
		//STEP 2: overpayment
		/*
		 * If overpayment happens in payment period N, all the payments greater than N must be discarded
		 * Besides, in payment period N there are 2 payments: the scheduled payment plus the overpayment
		 */		
		Repayment lastRepaymentBeforeOverpayment = loanServiceHelper.removeRepaymentsAfterOverpaymentPeriodNumber(repaymentPlan.getRepaymentPlan(),repaymentPlan.getOverpaymentPeriodNumber());
		loanServiceHelper.createOverpayment(lastRepaymentBeforeOverpayment,repaymentPlan);
		
		//STEP 3: regular repayment plan 
		loanServiceHelper.recalculateLoanCoordinatesAfterOverpayment(repaymentPlan);
		calculateRepaymentPlan(repaymentPlan);
		
		LOGGER.info("========> REPAYMENT PLAN");
		for(Repayment repayment : repaymentPlan.getRepaymentPlan()){
			LOGGER.info(repayment.toString());			
		}
		LOGGER.info("========> END REPAYMENT PLAN");
	}
	

	private boolean checkOverpaymentCoordinates(Double overpaymentAmount,Integer overpaymentPeriodNumber, OverpaymentType overpaymentType) {
		return overpaymentAmount!=null && overpaymentPeriodNumber!=null && overpaymentType!=null;
	}

	private boolean checkLoanCoordinates(Double annualInterestRate,Integer numberAnnualPayments, Double loanAmount, Integer loanLength, Double periodicPayment) {
		
		return annualInterestRate!=null && numberAnnualPayments!=null && loanAmount!=null && (loanLength!=null || periodicPayment!=null)
				&& (loanLength==null || periodicPayment==null);
	}
	


	private double getInterestRateForEveryPeriod(Double annualInterestRate,Integer numberAnnualPayments) {		
		return Math.pow(1+annualInterestRate,1./numberAnnualPayments)-1;
	}

	public void setLoanServiceHelper(LoanServiceHelper loanServiceHelper) {
		this.loanServiceHelper = loanServiceHelper;
	}
}
