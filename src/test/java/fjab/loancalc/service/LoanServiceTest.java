package fjab.loancalc.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
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

/**
 * The tests on this cass have been prepared using a simulation on a spreadsheet:
 * https://docs.google.com/spreadsheets/d/1I_CvScNBBzZ3mwUkiKMRTwnzlHzdb1MXtsII6qVM7jc
 *
 */
public class LoanServiceTest {
	
	private static final Logger LOGGER = Logger.getLogger(LoanServiceTest.class);

	private LoanServiceImp loanServiceImp;
	
	private final BigDecimal ANNUAL_INTEREST_RATE = new BigDecimal("0.05",MathContext.DECIMAL128);
	private final BigDecimal START_BALANCE = new BigDecimal("10000",MathContext.DECIMAL128);
	private final int NUMBER_ANNUAL_PAYMENTS = 12;//12 months
	private final int LOAN_LENGTH = 12;//total number of payments
	
	@Before
	public void setup(){
				
		loanServiceImp = new LoanServiceImp();
		loanServiceImp.setLoanServiceHelper(new LoanServiceHelper());
	}
	
	@Test
	public void getInterestRateForEveryPeriod() throws Exception{
		
		double annualInterestRate = 0.05; 
		int numberAnnualPayments = 12;
		
		Class<?> clazz = Class.forName(LoanServiceImp.class.getName());
		Method method = clazz.getDeclaredMethod("getInterestRateForEveryPeriod", BigDecimal.class, Integer.class);
		method.setAccessible(true);
		BigDecimal interest = (BigDecimal) method.invoke(loanServiceImp, new BigDecimal(annualInterestRate),numberAnnualPayments);

		assertEquals(0.05/12,interest.doubleValue(),0.00000000001);
	}
	
	@Test
	public void calculatePeriodicPayment() throws Exception{	
		
		BigDecimal monthlyInterestRate = ANNUAL_INTEREST_RATE.divide(BigDecimal.valueOf(NUMBER_ANNUAL_PAYMENTS),MathContext.DECIMAL128);

		//BigDecimal periodicPayment = loanServiceImp.workOutPeriodicPayment(monthlyInterestRate, START_BALANCE, NUMBER_ANNUAL_PAYMENTS);
		
		Class<?> clazz = Class.forName(LoanServiceImp.class.getName());
		Method method = clazz.getDeclaredMethod("workOutPeriodicPayment", BigDecimal.class, BigDecimal.class, int.class);
		method.setAccessible(true);
		BigDecimal periodicPayment = (BigDecimal) method.invoke(loanServiceImp, monthlyInterestRate, START_BALANCE, NUMBER_ANNUAL_PAYMENTS);

		
		assertEquals(856.074817884668, periodicPayment.doubleValue(),.00000000001);
	}
	
	@Test
	public void calculateRepaymentPlan() throws Exception{
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		BigDecimal startBalanceForPeriod=START_BALANCE;
		BigDecimal payment = BigDecimal.ZERO;
		BigDecimal capitalPaidOff=BigDecimal.ZERO;
		BigDecimal interestPaid=BigDecimal.ZERO;
		BigDecimal cumulativeCapitalPaidOff=BigDecimal.ZERO;
		BigDecimal cumulativeInterest=BigDecimal.ZERO;
		BigDecimal endBalance=START_BALANCE;
		BigDecimal totalCostToDate=BigDecimal.ZERO;

		//repayment 1
		Repayment repayment = new Repayment();
		expectedRepaymentPlan.addRepayment(repayment);
		
		paymentNumber=1;
		periodNumber = 1;
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(814.408151218001);
		interestPaid = BigDecimal.valueOf(41.666666666667);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(817.801518514743);
		interestPaid = BigDecimal.valueOf(38.273299369925);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(821.209024841887);
		interestPaid = BigDecimal.valueOf(34.865793042780);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(824.630729112062);
		interestPaid = BigDecimal.valueOf(31.444088772606);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(828.066690483362);
		interestPaid = BigDecimal.valueOf(28.008127401305);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(831.516968360376);
		interestPaid = BigDecimal.valueOf(24.557849524291);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(834.981622395211);
		interestPaid = BigDecimal.valueOf(21.093195489457);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(838.460712488524);
		interestPaid = BigDecimal.valueOf(17.614105396143);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(841.954298790560);
		interestPaid = BigDecimal.valueOf(14.120519094108);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(845.462441702187);
		interestPaid = BigDecimal.valueOf(10.612376182480);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(848.985201875946);
		interestPaid = BigDecimal.valueOf(7.089616008721);
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
		payment = BigDecimal.valueOf(856.074817884668);
		capitalPaidOff = BigDecimal.valueOf(852.522640217096);
		interestPaid = BigDecimal.valueOf(3.552177667571);
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
		
		assertThat(repaymentPlan.getRepaymentPlan(),matchRepayment(expectedRepaymentPlan.getRepaymentPlan()));
		
	}
	
	@Test
	public void calculateRepaymentPlanWithOverpaymentToKeepLoanLength() throws Exception{
		
		final BigDecimal START_BALANCE = BigDecimal.valueOf(1000);
		final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(.05);
		final int LOAN_LENGTH = 9;
		final int OVERPAYMENT_PERIOD = 3;
		final BigDecimal OVERPAYMENT_AMOUNT = BigDecimal.valueOf(100);
		final OverpaymentType OVERPAYMENT_TYPE = OverpaymentType.KEEP_LOAN_LENGTH;
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		BigDecimal startBalanceForPeriod=START_BALANCE;
		BigDecimal payment = BigDecimal.ZERO;
		BigDecimal capitalPaidOff=BigDecimal.ZERO;
		BigDecimal interestPaid=BigDecimal.ZERO;
		BigDecimal cumulativeCapitalPaidOff=BigDecimal.ZERO;
		BigDecimal cumulativeInterest=BigDecimal.ZERO;
		BigDecimal endBalance=START_BALANCE;
		BigDecimal totalCostToDate=BigDecimal.ZERO;
		
		//repayment 1
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=1;
			periodNumber = 1;
			payment = BigDecimal.valueOf(113.438758987361);
			capitalPaidOff = BigDecimal.valueOf(109.272092320695);
			interestPaid = BigDecimal.valueOf(4.166666666667);
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
			payment = BigDecimal.valueOf(113.438758987361);
			capitalPaidOff = BigDecimal.valueOf(109.727392705364);
			interestPaid = BigDecimal.valueOf(3.711366281997);
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
			payment = BigDecimal.valueOf(113.438758987361);
			capitalPaidOff = BigDecimal.valueOf(110.184590174970);
			interestPaid = BigDecimal.valueOf(3.254168812391);
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
			payment = BigDecimal.valueOf(96.528194584562);
			capitalPaidOff = BigDecimal.valueOf(94.149794897899);
			interestPaid = BigDecimal.valueOf(2.378399686662);
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
			payment = BigDecimal.valueOf(96.528194584562);
			capitalPaidOff = BigDecimal.valueOf(94.542085709974);
			interestPaid = BigDecimal.valueOf(1.986108874588);
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
			payment = BigDecimal.valueOf(96.528194584562);
			capitalPaidOff = BigDecimal.valueOf(94.936011067099);
			interestPaid = BigDecimal.valueOf(1.592183517463);
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
			payment = BigDecimal.valueOf(96.528194584562);
			capitalPaidOff = BigDecimal.valueOf(95.331577779879);
			interestPaid = BigDecimal.valueOf(1.196616804683);
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
			payment = BigDecimal.valueOf(96.528194584562);
			capitalPaidOff = BigDecimal.valueOf(95.728792687295);
			interestPaid = BigDecimal.valueOf(0.799401897267);
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
			payment = BigDecimal.valueOf(96.528194584562);
			capitalPaidOff = BigDecimal.valueOf(96.127662656825);
			interestPaid = BigDecimal.valueOf(0.400531927737);
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
		
		final BigDecimal START_BALANCE = BigDecimal.valueOf(1000);
		final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(.05);
		final int LOAN_LENGTH = 12;
		final int OVERPAYMENT_PERIOD = 3;
		final BigDecimal OVERPAYMENT_AMOUNT = BigDecimal.valueOf(200);
		final OverpaymentType OVERPAYMENT_TYPE = OverpaymentType.KEEP_PERIODIC_REPAYMENT;
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		BigDecimal startBalanceForPeriod=START_BALANCE;
		BigDecimal payment = BigDecimal.ZERO;
		BigDecimal capitalPaidOff=BigDecimal.ZERO;
		BigDecimal interestPaid=BigDecimal.ZERO;
		BigDecimal cumulativeCapitalPaidOff=BigDecimal.ZERO;
		BigDecimal cumulativeInterest=BigDecimal.ZERO;
		BigDecimal endBalance=START_BALANCE;
		BigDecimal totalCostToDate=BigDecimal.ZERO;
		
		//repayment 1		
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.addRepayment(repayment);
			
			paymentNumber=1;
			periodNumber = 1;
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(81.440815121800);
			interestPaid = BigDecimal.valueOf(4.166666666667);
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
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(81.780151851474);
			interestPaid = BigDecimal.valueOf(3.827329936993);
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
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(82.120902484189);
			interestPaid = BigDecimal.valueOf(3.486579304278);
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
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(83.296406244540);
			interestPaid = BigDecimal.valueOf(2.311075543927);
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
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(83.643474603892);
			interestPaid = BigDecimal.valueOf(1.964007184575);
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
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(83.991989081408);
			interestPaid = BigDecimal.valueOf(1.615492707059);
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
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(84.341955702581);
			interestPaid = BigDecimal.valueOf(1.265526085886);
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
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(84.693380518008);
			interestPaid = BigDecimal.valueOf(0.914101270459);
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
			payment = BigDecimal.valueOf(85.607481788467);
			capitalPaidOff = BigDecimal.valueOf(85.046269603500);
			interestPaid = BigDecimal.valueOf(0.561212184967);
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
			payment = BigDecimal.valueOf(49.851507516896);
			capitalPaidOff = BigDecimal.valueOf(49.644654788610);
			interestPaid = BigDecimal.valueOf(0.206852728286);
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
								assertEquals("startBalance",theExpected.get(j).getStartBalance().doubleValue(), actual.get(j).getStartBalance().doubleValue(),.0000000001);
								assertEquals("payment",theExpected.get(j).getPayment().doubleValue(), actual.get(j).getPayment().doubleValue(),.00000000001);
								assertEquals("capitalPaidOff",theExpected.get(j).getCapitalPaidOff().doubleValue(), actual.get(j).getCapitalPaidOff().doubleValue(),.00000000001);
								assertEquals("interestPaid",theExpected.get(j).getInterestPaid().doubleValue(), actual.get(j).getInterestPaid().doubleValue(),.00000000001);
								assertEquals("endBalance",theExpected.get(j).getEndBalance().doubleValue(), actual.get(j).getEndBalance().doubleValue(),.0000000001);
								assertEquals("cumulativePaidOff",theExpected.get(j).getCumulativeCapitalPaidOff().doubleValue(), actual.get(j).getCumulativeCapitalPaidOff().doubleValue(),.0000000001);
								assertEquals("cumulativeInterest",theExpected.get(j).getCumulativeInterest().doubleValue(), actual.get(j).getCumulativeInterest().doubleValue(),.00000000001);
								assertEquals("totalCostDate",theExpected.get(j).getTotalCostToDate().doubleValue(), actual.get(j).getTotalCostToDate().doubleValue(),.0000000001);								
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
		final BigDecimal START_BALANCE = BigDecimal.valueOf(164677);
		final BigDecimal ANNUAL_INTEREST_RATE = BigDecimal.valueOf(.01131);
		final int LOAN_LENGTH = 287;
		final int OVERPAYMENT_PERIOD = 1;
		final BigDecimal OVERPAYMENT_AMOUNT = BigDecimal.valueOf(10000);
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
		final BigDecimal START_BALANCE = new BigDecimal(231750,MathContext.DECIMAL128);
		final BigDecimal ANNUAL_INTEREST_RATE = new BigDecimal(.0455,MathContext.DECIMAL128);
		final int LOAN_LENGTH = 420;
		final int OVERPAYMENT_PERIOD = 1;
		final BigDecimal OVERPAYMENT_AMOUNT = BigDecimal.valueOf(0);
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
