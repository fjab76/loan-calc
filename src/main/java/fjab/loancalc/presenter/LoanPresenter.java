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
	
	public RepaymentPlan calculateRepaymentPlan() throws Exception{
		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setAnnualInterestRate(loanBean.getAnnualInterestRate().doubleValue());
		//repaymentPlan.setNumberAnnualPayments(loanBean.getNumberAnnualPayments());
		String repaymentPeriodicity = loanBean.getRepaymentPeriodicity();
		
		if("Monthly".equals(repaymentPeriodicity)){
			repaymentPlan.setNumberAnnualPayments(12);
		}
		else if("Quarterly".equals(repaymentPeriodicity)){
			repaymentPlan.setNumberAnnualPayments(4);
		}
		else if("Yearly".equals(repaymentPeriodicity)){
			repaymentPlan.setNumberAnnualPayments(1);
		}
		else{
			throw new IllegalArgumentException("Wrong repayment periodicity value " + repaymentPeriodicity);
		}
			
		
		repaymentPlan.setLoanAmount(loanBean.getLoanAmount().doubleValue());
		int monthAdjustmentFactor = 12/repaymentPlan.getNumberAnnualPayments();
		repaymentPlan.setLoanLength((loanBean.getLoanLengthYears()==null?0:loanBean.getLoanLengthYears()*repaymentPlan.getNumberAnnualPayments()) + (loanBean.getLoanLengthMonths()==null?0:loanBean.getLoanLengthMonths()/monthAdjustmentFactor));
		loanService.calculateRepaymentPlan(repaymentPlan);
		
		return repaymentPlan;
	}

}
