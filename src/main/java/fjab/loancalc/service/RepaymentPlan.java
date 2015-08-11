package fjab.loancalc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representation of the repayment plan. The repayment plan is comprised of repayments spread over
 * time. The number of repayments and their value can be calculated according to different systems (capital
 * and interest, interest only, etc.).
 * For a French Amortization System with capital and interest
 * (see http://www.gyplan.com/amofran_en.html for further details), the repayment plan is characterised by
 * these attributes:
 * 
 * -loan amount: borrowed capital that must be repaid
 * -annual interest rate: the interest rate must be adapted to the number of periods of the loan. For instance,
 * if the repayment plan is scheduled on a monthly basis, the interest rate to use to calculate the periodic
 * payments is 1/12 of the annual interest rate
 * -number of annual payments: number of repayments in a year
 * -loan length: number of repayments needed to pay off the principal of the loan
 * -periodic payment: value of the periodic amortization
 * 
 * Periodic payment and loan length are mutually exclusive: only one can be fixed and the other
 * is calculated automatically.
 * 
 * In addition to the periodic payments, extra overpayments can be made during the life of the loan.
 * Every time an overpayment is made, the principal decreases and thus the amortization plan must be 
 * recalculated. When recalculating the amortization plan, the borrower can choose to fix the length of
 * the loan or the periodic amortization.
 * Consequently in order to recalculate the amortization plan after an overpayment, it is necessary
 * to specify the capital paid off (overpayment amount), the payment period when the overpayment
 * is made and the type of overpayment (to keep the length of the loan or to keep the periodic
 * amortization). 
 * 
 * 
 * @author franciscoalvarez
 *
 */
public class RepaymentPlan {
	
	//Loan coordinates
	private BigDecimal annualInterestRate;
	private BigDecimal loanAmount;
	private Integer numberAnnualPayments;
	
	//periodic payment and loan length are mutually exclusive: only one can be fixed and the other
	//is calculated automatically
	private Integer loanLength;//expressed as number of payments
	private BigDecimal periodicPayment;
	
	//Overpayment coordinates
	private BigDecimal overpaymentAmount;
	private Integer overpaymentPeriodNumber;//payment period when the overpayment happens
	private OverpaymentType overpaymentType;	
	public enum OverpaymentType{
		KEEP_LOAN_LENGTH,KEEP_PERIODIC_REPAYMENT;
	}
	
	//Repayment list
	private List<Repayment> repaymentPlan = new ArrayList<>();
	
	public RepaymentPlan(){}
	
	public RepaymentPlan(BigDecimal annualInterestRate, Integer numberAnnualPayments, BigDecimal loanAmount, Integer loanLength){
		this.annualInterestRate = annualInterestRate;
		this.numberAnnualPayments = numberAnnualPayments;
		this.loanAmount = loanAmount;
		this.loanLength = loanLength;
	}
	
	public List<Repayment> getRepaymentPlan(){
		return Collections.unmodifiableList(repaymentPlan);
	}

	public BigDecimal getAnnualInterestRate() {
		return annualInterestRate;
	}

	void setAnnualInterestRate(BigDecimal annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getNumberAnnualPayments() {
		return numberAnnualPayments;
	}

	void setNumberAnnualPayments(Integer numberAnnualPayments) {
		this.numberAnnualPayments = numberAnnualPayments;
	}

	public Integer getLoanLength() {
		return loanLength;
	}

	void setLoanLength(Integer loanLength) {
		this.loanLength = loanLength;
	}

	public BigDecimal getOverpaymentAmount() {
		return overpaymentAmount;
	}

	void setOverpaymentAmount(BigDecimal overpaymentAmount) {
		this.overpaymentAmount = overpaymentAmount;
	}

	public Integer getOverpaymentPeriodNumber() {
		return overpaymentPeriodNumber;
	}

	void setOverpaymentPeriodNumber(Integer overpaymentPeriodNumber) {
		this.overpaymentPeriodNumber = overpaymentPeriodNumber;
	}

	public OverpaymentType getOverpaymentType() {
		return overpaymentType;
	}

	void setOverpaymentType(OverpaymentType overpaymentType) {
		this.overpaymentType = overpaymentType;
	}

	public BigDecimal getPeriodicPayment() {
		return periodicPayment;
	}

	void setPeriodicPayment(BigDecimal periodicPayment) {
		this.periodicPayment = periodicPayment;
	}
	
	void addRepayment(Repayment repayment){
		repaymentPlan.add(repayment);
	}
	
	void deleteRepayments(int first, int end){
		repaymentPlan.subList(first, end).clear();
	}
}
