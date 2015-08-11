package fjab.loancalc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import fjab.loancalc.service.LoanServiceHelper;
import fjab.loancalc.service.LoanServiceImp;
import fjab.loancalc.service.Repayment;
import fjab.loancalc.service.RepaymentPlan;
import fjab.loancalc.service.RepaymentPlan.OverpaymentType;

import static org.junit.Assert.*;

public class LoanServiceTest {
	
	private static final Logger LOGGER = Logger.getLogger(LoanServiceTest.class);
	private static final int SCALE = 2;

	private LoanServiceImp loanServiceImp;
	
	private final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(0.05).setScale(SCALE,RoundingMode.HALF_EVEN);
	private final BigDecimal START_BALANCE = BigDecimal.valueOf(10000).setScale(SCALE,RoundingMode.HALF_EVEN);
	private final int NUMBER_ANNUAL_PAYMENTS = 12;//12 months
	private final int LOAN_LENGTH = 12;//total number of payments
	
	@Before
	public void setup(){
				
		loanServiceImp = new LoanServiceImp();
		loanServiceImp.setLoanServiceHelper(new LoanServiceHelper());
	}
	
	/*@Test
	public void calculatePeriodicPayment(){	
		
		BigDecimal monthlyInterestRate = Math.pow(1+ANNUAL_INTEREST_RATE,1./NUMBER_ANNUAL_PAYMENTS)-1;
		BigDecimal periodicPayment = loanServiceImp.workOutPeriodicPayment(monthlyInterestRate, START_BALANCE, NUMBER_ANNUAL_PAYMENTS);
		
		assertEquals(856, periodicPayment,1);
	}*/
	
	@Test
	public void calculateRepaymentPlan() throws Exception{
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		BigDecimal startBalanceForPeriod=START_BALANCE;
		BigDecimal payment = BigDecimal.ZERO.setScale(SCALE,RoundingMode.HALF_EVEN);
		BigDecimal capitalPaidOff=BigDecimal.ZERO.setScale(SCALE,RoundingMode.HALF_EVEN);
		BigDecimal interestPaid=BigDecimal.ZERO.setScale(SCALE,RoundingMode.HALF_EVEN);
		BigDecimal cumulativeCapitalPaidOff=BigDecimal.ZERO.setScale(SCALE,RoundingMode.HALF_EVEN);
		BigDecimal cumulativeInterest=BigDecimal.ZERO.setScale(SCALE,RoundingMode.HALF_EVEN);
		BigDecimal endBalance=START_BALANCE;
		BigDecimal totalCostToDate=BigDecimal.ZERO.setScale(SCALE,RoundingMode.HALF_EVEN);

		//repayment 1
		Repayment repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=1;
		periodNumber = 1;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(814.41);
		interestPaid = BigDecimal.valueOf(41.67);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		
		//repayment 2
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=2;
		periodNumber = 2;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(818);
		interestPaid = BigDecimal.valueOf(38);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		
		
		//repayment 3
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=3;
		periodNumber = 3;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(821);
		interestPaid = BigDecimal.valueOf(35);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		//repayment 4
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=4;
		periodNumber = 4;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(825);
		interestPaid = BigDecimal.valueOf(31);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		//repayment 5
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=5;
		periodNumber = 5;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(828);
		interestPaid = BigDecimal.valueOf(28);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		//repayment 6
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=6;
		periodNumber = 6;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(832);
		interestPaid = BigDecimal.valueOf(250);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 7
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=7;
		periodNumber = 7;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(835);
		interestPaid = BigDecimal.valueOf(21);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 8
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=8;
		periodNumber = 8;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(838);
		interestPaid = BigDecimal.valueOf(18);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 9
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=9;
		periodNumber = 9;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(842);
		interestPaid = BigDecimal.valueOf(14);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 10
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=10;
		periodNumber = 10;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(845);
		interestPaid = BigDecimal.valueOf(11);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 11
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=11;
		periodNumber = 11;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(849);
		interestPaid = BigDecimal.valueOf(7);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 12
		repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=12;
		periodNumber = 12;
		startBalanceForPeriod = endBalance;
		payment = BigDecimal.valueOf(856);
		capitalPaidOff = BigDecimal.valueOf(849);
		interestPaid = BigDecimal.valueOf(4);
		cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
		cumulativeInterest = cumulativeInterest.add(interestPaid);
		totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
		endBalance = endBalance.subtract(capitalPaidOff);
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setAnnualInterestRate(ANNUAL_INTEREST_RATE);
		repaymentPlan.setNumberAnnualPayments(NUMBER_ANNUAL_PAYMENTS);
		repaymentPlan.setLoanAmount(START_BALANCE);
		repaymentPlan.setLoanLength(LOAN_LENGTH);
		loanServiceImp.calculateRepaymentPlan(repaymentPlan);
		
		//assertEquals(repaymentPlanExpected.getRepaymentPlan(), repaymentPlan.getRepaymentPlan());
		assertThat(repaymentPlan.getRepaymentPlan(),matchRepayment(expectedRepaymentPlan.getRepaymentPlan()));
		
	}
	
	@Test
	public void calculateRepaymentPlanWithOverpaymentToKeepLoanLength() throws Exception{
		
		final BigDecimal START_BALANCE = BigDecimal.valueOf(1000).setScale(SCALE);
		final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(.05).setScale(SCALE);
		final int LOAN_LENGTH = 9;
		final int OVERPAYMENT_PERIOD = 3;
		final BigDecimal OVERPAYMENT_AMOUNT = BigDecimal.valueOf(100).setScale(SCALE);
		final OverpaymentType OVERPAYMENT_TYPE = OverpaymentType.KEEP_LOAN_LENGTH;
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		BigDecimal startBalanceForPeriod=START_BALANCE;
		BigDecimal payment = BigDecimal.ZERO.setScale(SCALE);
		BigDecimal capitalPaidOff=BigDecimal.ZERO.setScale(SCALE);
		BigDecimal interestPaid=BigDecimal.ZERO.setScale(SCALE);
		BigDecimal cumulativeCapitalPaidOff=BigDecimal.ZERO.setScale(SCALE);
		BigDecimal cumulativeInterest=BigDecimal.ZERO.setScale(SCALE);
		BigDecimal endBalance=START_BALANCE;
		BigDecimal totalCostToDate=BigDecimal.ZERO.setScale(SCALE);
		
		//repayment 1
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=1;
			periodNumber = 1;
			payment = BigDecimal.valueOf(113.44);
			capitalPaidOff = BigDecimal.valueOf(109.27);
			interestPaid = BigDecimal.valueOf(4.17);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);			 
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 2
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=2;
			periodNumber = 2;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(113.44);
			capitalPaidOff = BigDecimal.valueOf(109.73);
			interestPaid = BigDecimal.valueOf(3.71);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 3
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=3;
			periodNumber = 3;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(113.44);
			capitalPaidOff = BigDecimal.valueOf(110.18);
			interestPaid = BigDecimal.valueOf(3.25);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 4 --> Overpayment
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=4;
			periodNumber = 3;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(100);
			capitalPaidOff = BigDecimal.valueOf(100);
			interestPaid = BigDecimal.ZERO;
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 5
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=5;
			periodNumber = 4;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(96.56);
			capitalPaidOff = BigDecimal.valueOf(94.18);
			interestPaid = BigDecimal.valueOf(2.38);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 6
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=6;
			periodNumber = 5;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(96.56);
			capitalPaidOff = BigDecimal.valueOf(94.57);
			interestPaid = BigDecimal.valueOf(1.99);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 7
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=7;
			periodNumber = 6;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(96.56);
			capitalPaidOff = BigDecimal.valueOf(94.97);
			interestPaid = BigDecimal.valueOf(1.59);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 8
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=8;
			periodNumber = 7;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(96.56);
			capitalPaidOff = BigDecimal.valueOf(95.36);
			interestPaid = BigDecimal.valueOf(1.2);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 9
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=9;
			periodNumber = 8;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(96.56);
			capitalPaidOff = BigDecimal.valueOf(95.76);
			interestPaid = BigDecimal.valueOf(.8);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 10
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=10;
			periodNumber = 9;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(96.56);
			capitalPaidOff = BigDecimal.valueOf(96.16);
			interestPaid = BigDecimal.valueOf(.4);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		
		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setAnnualInterestRate(ANNUAL_INTEREST_RATE);
		repaymentPlan.setNumberAnnualPayments(NUMBER_ANNUAL_PAYMENTS);
		repaymentPlan.setLoanAmount(START_BALANCE);
		repaymentPlan.setLoanLength(LOAN_LENGTH);
		repaymentPlan.setOverpaymentAmount(OVERPAYMENT_AMOUNT);
		repaymentPlan.setOverpaymentPeriodNumber(OVERPAYMENT_PERIOD);
		repaymentPlan.setOverpaymentType(OVERPAYMENT_TYPE);
		
		loanServiceImp.calculateRepaymentPlan(repaymentPlan);
		
		assertThat(repaymentPlan.getRepaymentPlan(),matchRepayment(expectedRepaymentPlan.getRepaymentPlan()));
	}
	
	@Test
	public void calculateRepaymentPlanWithOverpaymentToKeepPeriodicPayment() throws Exception{
		
		final BigDecimal START_BALANCE = BigDecimal.valueOf(1000).setScale(SCALE);
		final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(.05).setScale(SCALE);
		final int LOAN_LENGTH = 12;
		final int OVERPAYMENT_PERIOD = 3;
		final BigDecimal OVERPAYMENT_AMOUNT = BigDecimal.valueOf(200).setScale(SCALE);
		final OverpaymentType OVERPAYMENT_TYPE = OverpaymentType.KEEP_PERIODIC_REPAYMENT;
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		BigDecimal startBalanceForPeriod=START_BALANCE;
		BigDecimal payment = BigDecimal.ZERO.setScale(SCALE);
		BigDecimal capitalPaidOff=BigDecimal.ZERO.setScale(SCALE);
		BigDecimal interestPaid=BigDecimal.ZERO.setScale(SCALE);
		BigDecimal cumulativeCapitalPaidOff=BigDecimal.ZERO.setScale(SCALE);
		BigDecimal cumulativeInterest=BigDecimal.ZERO.setScale(SCALE);
		BigDecimal endBalance=START_BALANCE;
		BigDecimal totalCostToDate=BigDecimal.ZERO.setScale(SCALE);
		
		//repayment 1		
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=1;
			periodNumber = 1;
			payment = BigDecimal.valueOf(85.61);
			capitalPaidOff = BigDecimal.valueOf(81.44);
			interestPaid = BigDecimal.valueOf(4.17);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);			 
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 2
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=2;
			periodNumber = 2;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(86);
			capitalPaidOff = BigDecimal.valueOf(82);
			interestPaid = BigDecimal.valueOf(4);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 3
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=3;
			periodNumber = 3;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(86);
			capitalPaidOff = BigDecimal.valueOf(82);
			interestPaid = BigDecimal.valueOf(4);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 4 --> Overpayment
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=4;
			periodNumber = 3;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(200);
			capitalPaidOff = BigDecimal.valueOf(200);
			interestPaid = BigDecimal.valueOf(0);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 5
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=5;
			periodNumber = 4;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(86);
			capitalPaidOff = BigDecimal.valueOf(83);
			interestPaid = BigDecimal.valueOf(3);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 6
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=6;
			periodNumber = 5;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(86);
			capitalPaidOff = BigDecimal.valueOf(83);
			interestPaid = BigDecimal.valueOf(3);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 7
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=7;
			periodNumber = 6;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(86);
			capitalPaidOff = BigDecimal.valueOf(83);
			interestPaid = BigDecimal.valueOf(3);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 8
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=8;
			periodNumber = 7;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(86);
			capitalPaidOff = BigDecimal.valueOf(84);
			interestPaid = BigDecimal.valueOf(2);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 9
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=9;
			periodNumber = 8;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(86);
			capitalPaidOff = BigDecimal.valueOf(85);
			interestPaid = BigDecimal.valueOf(1);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 10
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=10;
			periodNumber = 9;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(86);
			capitalPaidOff = BigDecimal.valueOf(85);
			interestPaid = BigDecimal.valueOf(1);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 11
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=11;
			periodNumber = 10;
			startBalanceForPeriod = endBalance;
			payment = BigDecimal.valueOf(50);
			capitalPaidOff = BigDecimal.valueOf(50);
			interestPaid = BigDecimal.valueOf(0);
			cumulativeCapitalPaidOff = cumulativeCapitalPaidOff.add(capitalPaidOff);
			cumulativeInterest = cumulativeInterest.add(interestPaid);
			totalCostToDate = cumulativeCapitalPaidOff.add(cumulativeInterest);	
			endBalance = endBalance.subtract(capitalPaidOff);
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		
		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setAnnualInterestRate(ANNUAL_INTEREST_RATE);
		repaymentPlan.setNumberAnnualPayments(NUMBER_ANNUAL_PAYMENTS);
		repaymentPlan.setLoanAmount(START_BALANCE);
		repaymentPlan.setLoanLength(LOAN_LENGTH);
		repaymentPlan.setOverpaymentAmount(OVERPAYMENT_AMOUNT);
		repaymentPlan.setOverpaymentPeriodNumber(OVERPAYMENT_PERIOD);
		repaymentPlan.setOverpaymentType(OVERPAYMENT_TYPE);
		
		loanServiceImp.calculateRepaymentPlan(repaymentPlan);
		
		assertThat(repaymentPlan.getRepaymentPlan(),matchRepayment(expectedRepaymentPlan.getRepaymentPlan()));
		
		
	}
	
	private void setRepaymentValues(Repayment repayment,int paymentNumber,int periodNumber, BigDecimal payment,
			BigDecimal capitalPaidOff, BigDecimal interestPaid,
			BigDecimal cumulativeCapitalPaidOff, BigDecimal cumulativeInterest, BigDecimal endBalance,
			BigDecimal totalCostToDate, BigDecimal startBalanceForPeriod) {
		
		repayment.setPaymentNumber(paymentNumber);
		repayment.setPeriodNumber(periodNumber);
		repayment.setStartBalance(startBalanceForPeriod);
		repayment.setPayment(payment);
		repayment.setCapitalPaidOff(capitalPaidOff);			
		repayment.setInterestPaid(interestPaid);		
		repayment.setCumulativeCapitalPaidOff(cumulativeCapitalPaidOff);
		repayment.setCumulativeInterest(cumulativeInterest);				
		repayment.setEndBalance(endBalance);
		repayment.setTotalCostToDate(totalCostToDate);
		
	}

	public Matcher<List<Repayment>> matchRepayment(final List<Repayment> expected){
		
		return new BaseMatcher<List<Repayment>>(){

			private List<Repayment> theExpected = expected;
			
			@SuppressWarnings("unchecked")
			@Override
			public boolean matches(Object item) {
				
				if(theExpected==null){					
					return item==null;
				}
				else if(item==null){
					return false;
				}
				else{
					if(item instanceof List){
						List<Repayment> actual = (List<Repayment>) item;
						if(theExpected.size()!=actual.size()){
							return false;
						}
						else{
							for(int j=0;j<theExpected.size();j++){
								assertEquals("paymentNumber",theExpected.get(j).getPaymentNumber(), actual.get(j).getPaymentNumber());
								assertEquals("periodNumber",theExpected.get(j).getPeriodNumber(), actual.get(j).getPeriodNumber());
								assertEquals("startBalance",theExpected.get(j).getStartBalance().doubleValue(), actual.get(j).getStartBalance().doubleValue(),10);
								assertEquals("payment",theExpected.get(j).getPayment().doubleValue(), actual.get(j).getPayment().doubleValue(),5);
								assertEquals("capitalPaidOff",theExpected.get(j).getCapitalPaidOff().doubleValue(), actual.get(j).getCapitalPaidOff().doubleValue(),10);
								assertEquals("interestPaid",theExpected.get(j).getInterestPaid().doubleValue(), actual.get(j).getInterestPaid().doubleValue(),10);
								assertEquals("endBalance",theExpected.get(j).getEndBalance().doubleValue(), actual.get(j).getEndBalance().doubleValue(),10);
								assertEquals("cumulativePaidOff",theExpected.get(j).getCumulativeCapitalPaidOff().doubleValue(), actual.get(j).getCumulativeCapitalPaidOff().doubleValue(),10);
								assertEquals("cumulativeInterest",theExpected.get(j).getCumulativeInterest().doubleValue(), actual.get(j).getCumulativeInterest().doubleValue(),10);
								assertEquals("totalCostDate",theExpected.get(j).getTotalCostToDate().doubleValue(), actual.get(j).getTotalCostToDate().doubleValue(),10);								
							}
							return true;
						}
						
					}
					else{
						return false;
					}
				}
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(theExpected.toString());
				
			}
		};
		
	}
	
	@Test
	public void simulateMortgageWithOverpaymentKeepingLoanLength() throws Exception{
		
		//DATA COLLECTED ON 10-01-2014
		final BigDecimal START_BALANCE = BigDecimal.valueOf(164677).setScale(SCALE,RoundingMode.HALF_EVEN);
		final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(.01131).setScale(SCALE,RoundingMode.HALF_EVEN);
		final int LOAN_LENGTH = 287;
		final int OVERPAYMENT_PERIOD = 1;
		final BigDecimal OVERPAYMENT_AMOUNT = BigDecimal.valueOf(10000).setScale(SCALE);
		final OverpaymentType OVERPAYMENT_TYPE = OverpaymentType.KEEP_LOAN_LENGTH;
		

		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setAnnualInterestRate(ANNUAL_INTEREST_RATE);
		repaymentPlan.setNumberAnnualPayments(NUMBER_ANNUAL_PAYMENTS);
		repaymentPlan.setLoanAmount(START_BALANCE);
		repaymentPlan.setLoanLength(LOAN_LENGTH);
		repaymentPlan.setOverpaymentAmount(OVERPAYMENT_AMOUNT);
		repaymentPlan.setOverpaymentPeriodNumber(OVERPAYMENT_PERIOD);
		repaymentPlan.setOverpaymentType(OVERPAYMENT_TYPE);
		
		loanServiceImp.calculateRepaymentPlan(repaymentPlan);
	}
	
	@Test
	public void simulateMortgageWithOverpaymentKeepingPayment() throws Exception{
		
		//DATA COLLECTED ON 10-01-2014
		final BigDecimal START_BALANCE = BigDecimal.valueOf(164677).setScale(SCALE,RoundingMode.HALF_EVEN);
		final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(.01131).setScale(SCALE,RoundingMode.HALF_EVEN);
		final int LOAN_LENGTH = 287;
		final int OVERPAYMENT_PERIOD = 1;
		final BigDecimal OVERPAYMENT_AMOUNT = BigDecimal.valueOf(10000).setScale(SCALE,RoundingMode.HALF_EVEN);
		final OverpaymentType OVERPAYMENT_TYPE = OverpaymentType.KEEP_PERIODIC_REPAYMENT;
		

		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setAnnualInterestRate(ANNUAL_INTEREST_RATE);
		repaymentPlan.setNumberAnnualPayments(NUMBER_ANNUAL_PAYMENTS);
		repaymentPlan.setLoanAmount(START_BALANCE);
		repaymentPlan.setLoanLength(LOAN_LENGTH);
		repaymentPlan.setOverpaymentAmount(OVERPAYMENT_AMOUNT);
		repaymentPlan.setOverpaymentPeriodNumber(OVERPAYMENT_PERIOD);
		repaymentPlan.setOverpaymentType(OVERPAYMENT_TYPE);
		
		loanServiceImp.calculateRepaymentPlan(repaymentPlan);
	}

}
