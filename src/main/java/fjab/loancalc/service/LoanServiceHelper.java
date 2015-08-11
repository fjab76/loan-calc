package fjab.loancalc.service;

import java.math.BigDecimal;
import java.util.List;

import fjab.loancalc.service.RepaymentPlan.OverpaymentType;

public class LoanServiceHelper {

	Repayment removeRepaymentsAfterOverpaymentPeriodNumber(RepaymentPlan repaymentPlan, Integer overpaymentPeriodNumber) throws Exception {
		
		int lastRepaymentBeforeOverpaymentIndex = getRepaymentByPeriodNumber(repaymentPlan.getRepaymentPlan(),overpaymentPeriodNumber);
		if(lastRepaymentBeforeOverpaymentIndex==-1){
			throw new Exception("The overpayment must happen in a valid payment period");
		}
		
		repaymentPlan.deleteRepayments(lastRepaymentBeforeOverpaymentIndex+1, repaymentPlan.getRepaymentPlan().size());
		
		Repayment lastRepaymentBeforeOverpayment = repaymentPlan.getRepaymentPlan().get(lastRepaymentBeforeOverpaymentIndex);
		return lastRepaymentBeforeOverpayment;		
	}
	
	private int getRepaymentByPeriodNumber(List<Repayment> repaymentPlan, Integer overpaymentPeriodNumber) {
		
		int j=0;
		for(Repayment repayment : repaymentPlan){
			if(repayment.getPeriodNumber().equals(overpaymentPeriodNumber)){
				return j;
			}
			j++;
		}
		return -1;
	}
	
	/**
	 * This implementation assumes that the overpayment happens after the scheduled payment of the period when the
	 * overpayment happens
	 */
	void createOverpayment(Repayment lastRepaymentBeforeOverpayment,RepaymentPlan repaymentPlan) {
		
		Repayment overpayment = new Repayment();
		repaymentPlan.addRepayment(overpayment);
		
		overpayment.setPaymentNumber(lastRepaymentBeforeOverpayment.getPaymentNumber()+1);		
		overpayment.setPeriodNumber(repaymentPlan.getOverpaymentPeriodNumber());
		overpayment.setStartBalance(lastRepaymentBeforeOverpayment.getEndBalance());
		overpayment.setPayment(repaymentPlan.getOverpaymentAmount());
		overpayment.setCapitalPaidOff(repaymentPlan.getOverpaymentAmount());
		overpayment.setInterestPaid(BigDecimal.ZERO);
		overpayment.setEndBalance(lastRepaymentBeforeOverpayment.getEndBalance().subtract(repaymentPlan.getOverpaymentAmount()));
		overpayment.setCumulativeCapitalPaidOff(lastRepaymentBeforeOverpayment.getCumulativeCapitalPaidOff().add(repaymentPlan.getOverpaymentAmount()));
		overpayment.setCumulativeInterest(lastRepaymentBeforeOverpayment.getCumulativeInterest());
		overpayment.setTotalCostToDate(lastRepaymentBeforeOverpayment.getTotalCostToDate().add(repaymentPlan.getOverpaymentAmount()));
		
		//System.out.println(overpayment.toString());
	}
	
	void recalculateLoanCoordinatesAfterOverpayment(RepaymentPlan repaymentPlan) throws Exception {
		
		/*
		 * Loan coordinates
		 * (1) annualInterestRate does not change
		 * (2) numberAnnualPayments does not change
		 * (3) loanAmount: is the end balance after the overpayment
		 * (4) loanLength:
		 * 		- if overpayment type is KEEP_LOAN_LENGTH, loanLength=loanLength-overpaymentPeriodNumber
		 * 		- if overpayment type is KEEP_PERIODIC_REPAYMENT, loanLength must be recalculated
		 */
		
		Repayment overpayment = getLastRepayment(repaymentPlan);
		if(overpayment==null){
			throw new Exception("Overpayment is not found");
		}
		
		repaymentPlan.setLoanAmount(overpayment.getEndBalance());
		if(OverpaymentType.KEEP_LOAN_LENGTH==repaymentPlan.getOverpaymentType()){
			repaymentPlan.setLoanLength(repaymentPlan.getLoanLength()-repaymentPlan.getOverpaymentPeriodNumber());
			repaymentPlan.setPeriodicPayment(null);
		}
		else if(OverpaymentType.KEEP_PERIODIC_REPAYMENT==repaymentPlan.getOverpaymentType()){
			repaymentPlan.setLoanLength(null);
		}
		else{
			throw new Exception();
		}
	}
	
	Repayment getLastRepayment(RepaymentPlan repaymentPlan){
		
		if(repaymentPlan!=null && repaymentPlan.getRepaymentPlan().size()>0){		
			return repaymentPlan.getRepaymentPlan().get(repaymentPlan.getRepaymentPlan().size()-1);
		}
		else{
			return null;
		}
	}
}
