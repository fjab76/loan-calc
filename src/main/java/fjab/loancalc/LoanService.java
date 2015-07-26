package fjab.loancalc;

import fjab.loancalc.french.model.RepaymentPlan;

public interface LoanService {
	
	public void calculateRepaymentPlan(RepaymentPlan repaymentPlan) throws Exception;

}
