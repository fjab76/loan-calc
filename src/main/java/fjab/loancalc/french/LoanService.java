package fjab.loancalc.french;

import fjab.loancalc.french.model.RepaymentPlan;

public interface LoanService {
	
	public void calculateRepaymentPlan(RepaymentPlan repaymentPlan) throws Exception;

}
