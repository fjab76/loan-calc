package fjab.loancalc;

import fjab.loancalc.french.model.RepaymentPlan;

public interface LoanService {
	
	//public double workOutPeriodicPayment(double interestRateForEveryPeriod,double capitalToPayBack,int numberPaymentPeriods);
	
	public void calculateRepaymentPlan(RepaymentPlan repaymentPlan) throws Exception;

	//void calculateRepaymentPlanWithOverpayment(RepaymentPlan repaymentPlan) throws Exception;

}
