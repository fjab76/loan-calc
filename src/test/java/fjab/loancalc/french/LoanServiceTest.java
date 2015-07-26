package fjab.loancalc.french;

import java.util.List;

import org.apache.log4j.Logger;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import fjab.loancalc.french.model.Repayment;
import fjab.loancalc.french.model.RepaymentPlan;
import fjab.loancalc.french.model.RepaymentPlan.OverpaymentType;

import static org.junit.Assert.*;

public class LoanServiceTest {
	
	private static final Logger LOGGER = Logger.getLogger(LoanServiceTest.class);

	private LoanServiceImp loanServiceImp;
	
	private final double ANNUAL_INTEREST_RATE = 0.05;
	private final double START_BALANCE = 10000;
	private final int NUMBER_ANNUAL_PAYMENTS = 12;//12 months
	private final int LOAN_LENGTH = 12;//total number of payments
	
	@Before
	public void setup(){
				
		loanServiceImp = new LoanServiceImp();
		loanServiceImp.setLoanServiceHelper(new LoanServiceHelper());
	}
	
	@Test
	public void calculatePeriodicPayment(){	
		
		double monthlyInterestRate = Math.pow(1+ANNUAL_INTEREST_RATE,1./NUMBER_ANNUAL_PAYMENTS)-1;
		double periodicPayment = loanServiceImp.workOutPeriodicPayment(monthlyInterestRate, START_BALANCE, NUMBER_ANNUAL_PAYMENTS);
		
		assertEquals(856, periodicPayment,1);
	}
	
	@Test
	public void calculateRepaymentPlan() throws Exception{
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		double startBalanceForPeriod=START_BALANCE;
		double payment = 0;
		double capitalPaidOff=0;
		double interestPaid=0;
		double cumulativeCapitalPaidOff=0;
		double cumulativeInterest=0;
		double endBalance=START_BALANCE;
		double totalCostToDate=0;

		//repayment 1
		Repayment repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=1;
		periodNumber = 1;
		payment = 856;
		capitalPaidOff = 814.41;
		interestPaid = 41.67;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		
		//repayment 2
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=2;
		periodNumber = 2;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 818;
		interestPaid = 38;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		
		
		//repayment 3
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=3;
		periodNumber = 3;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 821;
		interestPaid = 35;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		//repayment 4
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=4;
		periodNumber = 4;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 825;
		interestPaid = 31;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		//repayment 5
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=5;
		periodNumber = 5;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 828;
		interestPaid = 28;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		//repayment 6
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=6;
		periodNumber = 6;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 832;
		interestPaid = 25;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 7
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=7;
		periodNumber = 7;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 835;
		interestPaid = 21;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 8
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=8;
		periodNumber = 8;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 838;
		interestPaid = 18;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 9
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=9;
		periodNumber = 9;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 842;
		interestPaid = 14;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 10
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=10;
		periodNumber = 10;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 845;
		interestPaid = 11;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 11
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=11;
		periodNumber = 11;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 849;
		interestPaid = 7;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
		setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);

		
		//repayment 12
		repayment = new Repayment();
		expectedRepaymentPlan.getRepaymentPlan().add(repayment);
		
		paymentNumber=12;
		periodNumber = 12;
		startBalanceForPeriod = endBalance;
		payment = 856;
		capitalPaidOff = 849;
		interestPaid = 4;
		cumulativeCapitalPaidOff += capitalPaidOff;
		cumulativeInterest += interestPaid;
		totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
		endBalance -= capitalPaidOff;
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
		
		final double START_BALANCE = 1000;
		final double ANNUAL_INTEREST_RATE = .05;
		final int LOAN_LENGTH = 9;
		final int OVERPAYMENT_PERIOD = 3;
		final double OVERPAYMENT_AMOUNT = 100;
		final OverpaymentType OVERPAYMENT_TYPE = OverpaymentType.KEEP_LOAN_LENGTH;
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		double startBalanceForPeriod=START_BALANCE;
		double payment = 0;
		double capitalPaidOff=0;
		double interestPaid=0;
		double cumulativeCapitalPaidOff=0;
		double cumulativeInterest=0;
		double endBalance=START_BALANCE;
		double totalCostToDate=0;
		
		//repayment 1
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=1;
			periodNumber = 1;
			payment = 113.44;
			capitalPaidOff = 109.27;
			interestPaid = 4.17;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;			 
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 2
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=2;
			periodNumber = 2;
			startBalanceForPeriod = endBalance;
			payment = 113.44;
			capitalPaidOff = 109.73;
			interestPaid = 3.71;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 3
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=3;
			periodNumber = 3;
			startBalanceForPeriod = endBalance;
			payment = 113.44;
			capitalPaidOff = 110.18;
			interestPaid = 3.25;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 4 --> Overpayment
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=4;
			periodNumber = 3;
			startBalanceForPeriod = endBalance;
			payment = 100;
			capitalPaidOff = 100;
			interestPaid = 0;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 5
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=5;
			periodNumber = 4;
			startBalanceForPeriod = endBalance;
			payment = 96.56;
			capitalPaidOff = 94.18;
			interestPaid = 2.38;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 6
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=6;
			periodNumber = 5;
			startBalanceForPeriod = endBalance;
			payment = 96.56;
			capitalPaidOff = 94.57;
			interestPaid = 1.99;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 7
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=7;
			periodNumber = 6;
			startBalanceForPeriod = endBalance;
			payment = 96.56;
			capitalPaidOff = 94.97;
			interestPaid = 1.59;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 8
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=8;
			periodNumber = 7;
			startBalanceForPeriod = endBalance;
			payment = 96.56;
			capitalPaidOff = 95.36;
			interestPaid = 1.2;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 9
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=9;
			periodNumber = 8;
			startBalanceForPeriod = endBalance;
			payment = 96.56;
			capitalPaidOff = 95.76;
			interestPaid = .8;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 10
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=10;
			periodNumber = 9;
			startBalanceForPeriod = endBalance;
			payment = 96.56;
			capitalPaidOff = 96.16;
			interestPaid = .4;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
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
		
		final double START_BALANCE = 1000;
		final double ANNUAL_INTEREST_RATE = .05;
		final int LOAN_LENGTH = 12;
		final int OVERPAYMENT_PERIOD = 3;
		final double OVERPAYMENT_AMOUNT = 200;
		final OverpaymentType OVERPAYMENT_TYPE = OverpaymentType.KEEP_PERIODIC_REPAYMENT;
		
		RepaymentPlan expectedRepaymentPlan = new RepaymentPlan();
		int paymentNumber = 0;
		int periodNumber = 0;
		double startBalanceForPeriod=START_BALANCE;
		double payment = 0;
		double capitalPaidOff=0;
		double interestPaid=0;
		double cumulativeCapitalPaidOff=0;
		double cumulativeInterest=0;
		double endBalance=START_BALANCE;
		double totalCostToDate=0;
		
		//repayment 1		
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=1;
			periodNumber = 1;
			payment = 85.61;
			capitalPaidOff = 81.44;
			interestPaid = 4.17;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;			 
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 2
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=2;
			periodNumber = 2;
			startBalanceForPeriod = endBalance;
			payment = 86;
			capitalPaidOff = 82;
			interestPaid = 4;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 3
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=3;
			periodNumber = 3;
			startBalanceForPeriod = endBalance;
			payment = 86;
			capitalPaidOff = 82;
			interestPaid = 4;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 4 --> Overpayment
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=4;
			periodNumber = 3;
			startBalanceForPeriod = endBalance;
			payment = 200;
			capitalPaidOff = 200;
			interestPaid = 0;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 5
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=5;
			periodNumber = 4;
			startBalanceForPeriod = endBalance;
			payment = 86;
			capitalPaidOff = 83;
			interestPaid = 3;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 6
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=6;
			periodNumber = 5;
			startBalanceForPeriod = endBalance;
			payment = 86;
			capitalPaidOff = 83;
			interestPaid = 3;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 7
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=7;
			periodNumber = 6;
			startBalanceForPeriod = endBalance;
			payment = 86;
			capitalPaidOff = 83;
			interestPaid = 3;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 8
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=8;
			periodNumber = 7;
			startBalanceForPeriod = endBalance;
			payment = 86;
			capitalPaidOff = 84;
			interestPaid = 2;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 9
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=9;
			periodNumber = 8;
			startBalanceForPeriod = endBalance;
			payment = 86;
			capitalPaidOff = 85;
			interestPaid = 1;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 10
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=10;
			periodNumber = 9;
			startBalanceForPeriod = endBalance;
			payment = 86;
			capitalPaidOff = 85;
			interestPaid = 1;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
			setRepaymentValues(repayment,paymentNumber,periodNumber,payment,capitalPaidOff,interestPaid,cumulativeCapitalPaidOff,cumulativeInterest,endBalance,totalCostToDate, startBalanceForPeriod);
		}
		
		//repayment 11
		{
			Repayment repayment = new Repayment();
			expectedRepaymentPlan.getRepaymentPlan().add(repayment);
			
			paymentNumber=11;
			periodNumber = 10;
			startBalanceForPeriod = endBalance;
			payment = 50;
			capitalPaidOff = 50;
			interestPaid = 0;
			cumulativeCapitalPaidOff += capitalPaidOff;
			cumulativeInterest += interestPaid;
			totalCostToDate = cumulativeCapitalPaidOff+cumulativeInterest;	
			endBalance -= capitalPaidOff;
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
	
	private void setRepaymentValues(Repayment repayment,int paymentNumber,int periodNumber, double payment,
			double capitalPaidOff, double interestPaid,
			double cumulativeCapitalPaidOff, double cumulativeInterest, double endBalance,
			double totalCostToDate, double startBalanceForPeriod) {
		
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
								assertEquals("startBalance",theExpected.get(j).getStartBalance(), actual.get(j).getStartBalance(),10);
								assertEquals("payment",theExpected.get(j).getPayment(), actual.get(j).getPayment(),5);
								assertEquals("capitalPaidOff",theExpected.get(j).getCapitalPaidOff(), actual.get(j).getCapitalPaidOff(),10);
								assertEquals("interestPaid",theExpected.get(j).getInterestPaid(), actual.get(j).getInterestPaid(),10);
								assertEquals("endBalance",theExpected.get(j).getEndBalance(), actual.get(j).getEndBalance(),10);
								assertEquals("cumulativePaidOff",theExpected.get(j).getCumulativeCapitalPaidOff(), actual.get(j).getCumulativeCapitalPaidOff(),10);
								assertEquals("cumulativeInterest",theExpected.get(j).getCumulativeInterest(), actual.get(j).getCumulativeInterest(),10);
								assertEquals("totalCostDate",theExpected.get(j).getTotalCostToDate(), actual.get(j).getTotalCostToDate(),10);								
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
		final double START_BALANCE = 164677;
		final double ANNUAL_INTEREST_RATE = .01131;
		final int LOAN_LENGTH = 287;
		final int OVERPAYMENT_PERIOD = 1;
		final double OVERPAYMENT_AMOUNT = 10000;
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
		final double START_BALANCE = 164677;
		final double ANNUAL_INTEREST_RATE = .01131;
		final int LOAN_LENGTH = 287;
		final int OVERPAYMENT_PERIOD = 1;
		final double OVERPAYMENT_AMOUNT = 10000;
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
