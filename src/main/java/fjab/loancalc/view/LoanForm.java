package fjab.loancalc.view;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

import fjab.loancalc.presenter.LoanPresenter;
import fjab.loancalc.service.LoanServiceImp;
import fjab.loancalc.service.RepaymentPlan;

import com.vaadin.ui.Button.ClickEvent;

public class LoanForm extends FormLayout {
	
	private TextField annualInterestRate = new TextField("Annual interest rate");
	private TextField loanAmount = new TextField("Loan amount");
	// A single-select radio button group
	private OptionGroup repaymentPeriodicity = new OptionGroup("Repayment periodicity");
	//private TextField numberAnnualPayments = new TextField("Number annual payments");
	private Label loanLength = new Label("<span style='font-size:125%;'>Loan length</span>",ContentMode.HTML);
	private TextField loanLengthYears = new TextField("years");
	private TextField loanLengthMonths = new TextField("months");
	private Button okButton = new Button("OK");
	
	private Label periodicPayment;
	private Label totalInterestPaid;
	
	FieldGroup fieldGroup;

	public LoanForm(){
		
		//Customising form layout
		setSizeUndefined();
		setMargin(true);
		
		annualInterestRate.setNullRepresentation("");
		loanAmount.setNullRepresentation("");
		repaymentPeriodicity.addItems("Monthly","Quarterly","Yearly");
		//numberAnnualPayments.setNullRepresentation("");
		loanLengthYears.setNullRepresentation("");
		loanLengthMonths.setNullRepresentation("");
		
		okButton.addClickListener(event -> createClickListener(event));
		
		//Binding form elements to bean
		fieldGroup = new BeanFieldGroup<>(LoanBean.class);
		fieldGroup.setItemDataSource(new BeanItem<>(new LoanBean()));
		fieldGroup.bindMemberFields(this);
		
		//Adding elements to the form
		addComponent(annualInterestRate);
	    addComponent(loanAmount);
	    addComponent(repaymentPeriodicity);
	    //addComponent(numberAnnualPayments);
	    addComponent(loanLength);
	    addComponent(loanLengthYears);
	    addComponent(loanLengthMonths);
	    addComponent(okButton);
	    
	    
		
	}

	private void createClickListener(ClickEvent event) {
		try 
		{
          fieldGroup.commit();
          Notification.show("Form committed");
          
          LoanBean loanBean = new LoanBean((BigDecimal)fieldGroup.getItemDataSource().getItemProperty("annualInterestRate").getValue(),
        		  						   (BigDecimal)fieldGroup.getItemDataSource().getItemProperty("loanAmount").getValue(),
        		  						   (String)fieldGroup.getItemDataSource().getItemProperty("repaymentPeriodicity").getValue(),        		  						   
        		  						   (Integer)fieldGroup.getItemDataSource().getItemProperty("loanLengthYears").getValue(),
        		  						   (Integer)fieldGroup.getItemDataSource().getItemProperty("loanLengthMonths").getValue());
          System.out.println(loanBean);
          RepaymentPlan repaymentPlan = new LoanPresenter(new LoanServiceImp(), loanBean).calculateRepaymentPlan();
          if(periodicPayment!=null) removeComponent(periodicPayment);
          if(totalInterestPaid!=null) removeComponent(totalInterestPaid);
          periodicPayment = new Label("Periodic payment: " + repaymentPlan.getPeriodicPayment().setScale(2, RoundingMode.HALF_EVEN));
          totalInterestPaid = new Label("Total interest paid: " + repaymentPlan.getRepaymentPlan().get(repaymentPlan.getRepaymentPlan().size()-1).getCumulativeInterest().setScale(2, RoundingMode.HALF_EVEN));
          addComponent(periodicPayment);
          addComponent(totalInterestPaid);
        } 
		catch (CommitException e) {
          Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
