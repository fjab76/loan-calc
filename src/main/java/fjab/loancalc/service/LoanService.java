package fjab.loancalc.service;

import fjab.loancalc.service.model.RepaymentPlan;

public interface LoanService {
	
	public void calculateRepaymentPlan(RepaymentPlan repaymentPlan) throws Exception;

}
