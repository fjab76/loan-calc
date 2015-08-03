package fjab.loancalc.presenter;

import fjab.loancalc.service.LoanService;
import fjab.loancalc.service.model.RepaymentPlan;
import fjab.loancalc.view.LoanBean;

public class LoanPresenter {
	
	private LoanService loanService;
	private LoanBean loanBean;
	
	public LoanPresenter(LoanService loanService, LoanBean loanBean){
		this.loanService = loanService;
		this.loanBean = loanBean;
	}
	
	public void calculateRepaymentPlan() throws Exception{
		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setAnnualInterestRate(loanBean.getAnnualInterestRate().doubleValue());
		repaymentPlan.setNumberAnnualPayments(loanBean.getNumberAnnualPayments());
		repaymentPlan.setLoanAmount(loanBean.getLoanAmount().doubleValue());
		repaymentPlan.setLoanLength(loanBean.getLoanLengthYears()*12+loanBean.getLoanLengthMonths());
		loanService.calculateRepaymentPlan(repaymentPlan);
	}

}
