package fjab.loancalc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representation of a single loan repayment. This representation is immutable outside its package.
 * 
 * A loan repayment is defined by the following
 * attributes:
 * -start balance: principal of the loan left before the repayment
 * -payment: amount of money paid in this repayment
 * -capital paid off: amount of the payment employed to pay off the principal balance of the loan
 * -interest paid: amount of the payment employed to pay interests
 * Thus payment = capitalPaidOff + interestPaid
 * 
 * -end balance: capital to be repaid after the repayment. Thus endBalance = startBalance - capitalPaidOff
 * 
 * -cumulative capital paid off: total capital paid off so far including this repayment
 * -cumulative interest: total interest paid so far including this repayment
 * -total cost to date: sum of all the repayments up to date. 
 * Thus totalCostToDate = cumulativeCapitalPaidOff + cumulativeInterest
 * 
 * When the repayment is considered as part of a repayment plan, two more attributes are needed:
 * -payment number: the number of this repayment in the list of repayments (1st, 2nd, 3rd and so on)
 * -period number: the number of the period (the loan repayment plan is divided into several periods) 
 * in which this repayment happens. In general, payment number and period number are the same as only a
 * payment happens during a period. Only when there is an overpayment (extra capital is paid off), 
 * there are 2 different payments in the same period.
 * 
 * 
 * @author franciscoalvarez
 *
 */
public class Repayment{
	
	//Number of this payment: the numbers are set consecutively and coincide with the order of the payment
	private Integer paymentNumber;
	//Number of the period when the payment is made: when an overpayment happens, there are 2 different payments
	//at the same periodNumber
	private Integer periodNumber;
	private BigDecimal startBalance;
	private BigDecimal payment;
	private BigDecimal capitalPaidOff;
	private BigDecimal interestPaid;
	private BigDecimal cumulativeCapitalPaidOff;
	private BigDecimal cumulativeInterest;
	private BigDecimal endBalance;
	private BigDecimal totalCostToDate;

	public Integer getPaymentNumber() {
		return paymentNumber;
	}

	void setPaymentNumber(Integer paymentNumber) {
		this.paymentNumber = paymentNumber;
	}


	public Integer getPeriodNumber() {
		return periodNumber;
	}

	void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}


	public BigDecimal getStartBalance() {
		return startBalance;
	}

	void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}


	public BigDecimal getPayment() {
		return payment;
	}

	void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public BigDecimal getCapitalPaidOff() {
		return capitalPaidOff;
	}

	void setCapitalPaidOff(BigDecimal capitalPaidOff) {
		this.capitalPaidOff = capitalPaidOff;
	}

	public BigDecimal getInterestPaid() {
		return interestPaid;
	}

	void setInterestPaid(BigDecimal interestPaid) {
		this.interestPaid = interestPaid;
	}

	public BigDecimal getCumulativeCapitalPaidOff() {
		return cumulativeCapitalPaidOff;
	}

	void setCumulativeCapitalPaidOff(BigDecimal cumulativeCapitalPaidOff) {
		this.cumulativeCapitalPaidOff = cumulativeCapitalPaidOff;
	}

	public BigDecimal getCumulativeInterest() {
		return cumulativeInterest;
	}

	void setCumulativeInterest(BigDecimal cumulativeInterest) {
		this.cumulativeInterest = cumulativeInterest;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public BigDecimal getTotalCostToDate() {
		return totalCostToDate;
	}

	void setTotalCostToDate(BigDecimal totalCostToDate) {
		this.totalCostToDate = totalCostToDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Repayment [periodNumber=");
		builder.append(periodNumber);
		builder.append(", paymentNumber=");
		builder.append(paymentNumber);
		builder.append(", startBalance=");
		builder.append(startBalance.setScale(2, RoundingMode.HALF_EVEN));
		builder.append(", payment=");
		builder.append(payment.setScale(2, RoundingMode.HALF_EVEN));
		builder.append(", capitalPaidOff=");
		builder.append(capitalPaidOff.setScale(2, RoundingMode.HALF_EVEN));
		builder.append(", interestPaid=");
		builder.append(interestPaid.setScale(2, RoundingMode.HALF_EVEN));
		builder.append(", cumulativeCapitalPaidOff=");
		builder.append(cumulativeCapitalPaidOff.setScale(2, RoundingMode.HALF_EVEN));
		builder.append(", cumulativeInterest=");
		builder.append(cumulativeInterest.setScale(2, RoundingMode.HALF_EVEN));
		builder.append(", endBalance=");
		builder.append(endBalance.setScale(2, RoundingMode.HALF_EVEN));
		builder.append(", totalCostToDate=");
		builder.append(totalCostToDate.setScale(2, RoundingMode.HALF_EVEN));
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((capitalPaidOff == null) ? 0 : capitalPaidOff.hashCode());
		result = prime
				* result
				+ ((cumulativeCapitalPaidOff == null) ? 0
						: cumulativeCapitalPaidOff.hashCode());
		result = prime
				* result
				+ ((cumulativeInterest == null) ? 0 : cumulativeInterest
						.hashCode());
		result = prime * result
				+ ((endBalance == null) ? 0 : endBalance.hashCode());
		result = prime * result
				+ ((interestPaid == null) ? 0 : interestPaid.hashCode());
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result
				+ ((paymentNumber == null) ? 0 : paymentNumber.hashCode());
		result = prime * result
				+ ((periodNumber == null) ? 0 : periodNumber.hashCode());
		result = prime * result
				+ ((startBalance == null) ? 0 : startBalance.hashCode());
		result = prime * result
				+ ((totalCostToDate == null) ? 0 : totalCostToDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Repayment other = (Repayment) obj;
		if (capitalPaidOff == null) {
			if (other.capitalPaidOff != null)
				return false;
		} else if (!capitalPaidOff.equals(other.capitalPaidOff))
			return false;
		if (cumulativeCapitalPaidOff == null) {
			if (other.cumulativeCapitalPaidOff != null)
				return false;
		} else if (!cumulativeCapitalPaidOff
				.equals(other.cumulativeCapitalPaidOff))
			return false;
		if (cumulativeInterest == null) {
			if (other.cumulativeInterest != null)
				return false;
		} else if (!cumulativeInterest.equals(other.cumulativeInterest))
			return false;
		if (endBalance == null) {
			if (other.endBalance != null)
				return false;
		} else if (!endBalance.equals(other.endBalance))
			return false;
		if (interestPaid == null) {
			if (other.interestPaid != null)
				return false;
		} else if (!interestPaid.equals(other.interestPaid))
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (paymentNumber == null) {
			if (other.paymentNumber != null)
				return false;
		} else if (!paymentNumber.equals(other.paymentNumber))
			return false;
		if (periodNumber == null) {
			if (other.periodNumber != null)
				return false;
		} else if (!periodNumber.equals(other.periodNumber))
			return false;
		if (startBalance == null) {
			if (other.startBalance != null)
				return false;
		} else if (!startBalance.equals(other.startBalance))
			return false;
		if (totalCostToDate == null) {
			if (other.totalCostToDate != null)
				return false;
		} else if (!totalCostToDate.equals(other.totalCostToDate))
			return false;
		return true;
	}
	
	
}
