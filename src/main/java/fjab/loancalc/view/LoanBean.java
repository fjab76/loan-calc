package fjab.loancalc.view;

import java.math.BigDecimal;

public class LoanBean {
	
	private BigDecimal annualInterestRate;
	private BigDecimal loanAmount;
	private Integer numberAnnualPayments;
	private Integer loanLengthYears;
	private Integer loanLengthMonths;
	
	public LoanBean(){}
	
	public LoanBean(BigDecimal annualInterestRate,
					BigDecimal loanAmount,
				    Integer numberAnnualPayments,
				    Integer loanLengthYears,
				    Integer loanLengthMonths){
		this.annualInterestRate = annualInterestRate;
		this.loanAmount = loanAmount;
		this.numberAnnualPayments = numberAnnualPayments;
		this.loanLengthYears = loanLengthYears;
		this.loanLengthMonths = loanLengthMonths;
	}
	
	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("annualInterestRate:").append(this.annualInterestRate).append("\n");
		sb.append("loanAmount:").append(this.loanAmount).append("\n");
		sb.append("numberAnnualPayments:").append(this.numberAnnualPayments).append("\n");
		sb.append("loanLengthYears:").append(this.loanLengthYears).append("\n");
		sb.append("loanLengthMonths:").append(this.loanLengthMonths).append("\n");
		return sb.toString();
	}
	
	public BigDecimal getAnnualInterestRate() {
		return annualInterestRate;
	}
	public void setAnnualInterestRate(BigDecimal annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getNumberAnnualPayments() {
		return numberAnnualPayments;
	}
	public void setNumberAnnualPayments(Integer numberAnnualPayments) {
		this.numberAnnualPayments = numberAnnualPayments;
	}
	public Integer getLoanLengthYears() {
		return loanLengthYears;
	}
	public void setLoanLengthYears(Integer loanLengthYears) {
		this.loanLengthYears = loanLengthYears;
	}
	public Integer getLoanLengthMonths() {
		return loanLengthMonths;
	}
	public void setLoanLengthMonths(Integer loanLengthMonths) {
		this.loanLengthMonths = loanLengthMonths;
	}

}