package fjab.loancalc.presenter;

import fjab.loancalc.service.LoanService;
import fjab.loancalc.service.RepaymentPlan;
import fjab.loancalc.view.LoanBean;

public class LoanPresenter {
	
	private LoanService loanService;
	private LoanBean loanBean;
	
	public LoanPresenter(LoanService loanService, LoanBean loanBean){
		this.loanService = loanService;
		this.loanBean = loanBean;
	}
	
	public RepaymentPlan calculateRepaymentPlan() throws Exception{
		
		String repaymentPeriodicity = loanBean.getRepaymentPeriodicity();
		Integer numberAnnualPayments = null;
		if("Monthly".equals(repaymentPeriodicity)){
			numberAnnualPayments = 12;
		}
		else if("Quarterly".equals(repaymentPeriodicity)){
			numberAnnualPayments = 4;
		}
		else if("Yearly".equals(repaymentPeriodicity)){
			numberAnnualPayments = 1;
		}
		else{
			throw new IllegalArgumentException("Wrong repayment periodicity value " + repaymentPeriodicity);
		}
		
		int monthAdjustmentFactor = 12/numberAnnualPayments;
		Integer loanLength = (loanBean.getLoanLengthYears()==null?0:loanBean.getLoanLengthYears()*numberAnnualPayments) + (loanBean.getLoanLengthMonths()==null?0:loanBean.getLoanLengthMonths()/monthAdjustmentFactor);
		
		RepaymentPlan repaymentPlan = new RepaymentPlan(loanBean.getAnnualInterestRate().doubleValue(),
														numberAnnualPayments,
														loanBean.getLoanAmount().doubleValue(),
														loanLength);
		loanService.calculateRepaymentPlan(repaymentPlan);
		
		return repaymentPlan;
	}

}
