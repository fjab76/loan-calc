package fjab.loancalc.presenter;

import fjab.loancalc.service.LoanService;
import fjab.loancalc.service.RepaymentPlan;
import fjab.loancalc.view.LoanBean;
import fjab.loancalc.view.Periodicity;

public class LoanPresenter {
	
	private LoanService loanService;
	private LoanBean loanBean;
	
	public LoanPresenter(LoanService loanService, LoanBean loanBean){
		this.loanService = loanService;
		this.loanBean = loanBean;
	}
	
	public RepaymentPlan calculateRepaymentPlan() throws Exception{
		
		Periodicity repaymentPeriodicity = loanBean.getRepaymentPeriodicity();
		Integer numberAnnualPayments = null;
		if(Periodicity.MONTHLY==repaymentPeriodicity){
			numberAnnualPayments = 12;
		}
		else if(Periodicity.QUARTERLY==repaymentPeriodicity){
			numberAnnualPayments = 4;
		}
		else if(Periodicity.YEARLY==repaymentPeriodicity){
			numberAnnualPayments = 1;
		}
		else{
			throw new IllegalArgumentException("Wrong repayment periodicity value " + repaymentPeriodicity);
		}
		
		int monthAdjustmentFactor = 12/numberAnnualPayments;
		Integer loanLength = (loanBean.getLoanLengthYears()==null?0:loanBean.getLoanLengthYears()*numberAnnualPayments) + (loanBean.getLoanLengthMonths()==null?0:loanBean.getLoanLengthMonths()/monthAdjustmentFactor);
		
		RepaymentPlan repaymentPlan = new RepaymentPlan(loanBean.getAnnualInterestRate(),
														numberAnnualPayments,
														loanBean.getLoanAmount(),
														loanLength);
		loanService.calculateRepaymentPlan(repaymentPlan);
		
		return repaymentPlan;
	}

}
