package fjab.loancalc.view;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
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
		
		//Configuring fields of the form
		annualInterestRate.setNullRepresentation("");
		annualInterestRate.addValidator(new NullValidator("Mandatory field", false));
		annualInterestRate.addValidator(new BigDecimalRangeValidator("Value must be between 0 and 1", BigDecimal.ZERO, BigDecimal.ONE));
		
		loanAmount.setNullRepresentation("");
		loanAmount.addValidator(new NullValidator("Mandatory field", false));
		loanAmount.addValidator(new BigDecimalRangeValidator("Value must be greater than 0", BigDecimal.ZERO, BigDecimal.valueOf(Double.MAX_VALUE)));
		
		repaymentPeriodicity.addItems("Monthly","Quarterly","Yearly");

		loanLengthYears.setNullRepresentation("");
		loanLengthYears.setRequired(true);
		loanLengthYears.setRequiredError("Both loan length years and loan length months cannot be empty");
		loanLengthYears.addValidator(new IntegerRangeValidator("Value must be a positive value", 1, Integer.MAX_VALUE));
		loanLengthYears.addValueChangeListener(event -> createLoanLengthValueChangeListener(event,loanLengthYears,loanLengthMonths));
		
		loanLengthMonths.setNullRepresentation("");
		loanLengthMonths.setRequired(true);
		loanLengthMonths.setRequiredError("Both loan length years and loan length months cannot be empty");
		loanLengthMonths.addValidator(new IntegerRangeValidator("Value must be a positive value", 1, Integer.MAX_VALUE));
		loanLengthMonths.addValueChangeListener(event -> createLoanLengthValueChangeListener(event,loanLengthMonths,loanLengthYears));
		
		okButton.addClickListener(event -> createClickListener(event));
		
		//Binding form elements to bean
		fieldGroup = new BeanFieldGroup<>(LoanBean.class);
		fieldGroup.setItemDataSource(new BeanItem<>(new LoanBean()));
		fieldGroup.bindMemberFields(this);
		
		//Adding elements to the form
		addComponent(annualInterestRate);
	    addComponent(loanAmount);
	    addComponent(repaymentPeriodicity);
	    addComponent(loanLength);
	    addComponent(loanLengthYears);
	    addComponent(loanLengthMonths);
	    addComponent(okButton);
	}

	private void createLoanLengthValueChangeListener(ValueChangeEvent event, TextField thisField, TextField theOther) {
		
		if((event.getProperty().getValue()!=null && !"".equals(event.getProperty().getValue())) || 
				(theOther.getValue()!=null && !"".equals(theOther.getValue()))){
			thisField.setRequired(false);
			theOther.setRequired(false);
		}
		else{
			thisField.setRequired(true);
			theOther.setRequired(true);
		}
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

          if(periodicPayment==null) {        	
        	  periodicPayment = new Label("Periodic payment: " + repaymentPlan.getPeriodicPayment().setScale(2, RoundingMode.HALF_EVEN));
        	  addComponent(periodicPayment);
          }
          else{
        	  periodicPayment.setValue("Periodic payment: " + repaymentPlan.getPeriodicPayment().setScale(2, RoundingMode.HALF_EVEN));
          }
        	  
          if(totalInterestPaid==null){
        	  totalInterestPaid = new Label("Total interest paid: " + repaymentPlan.getCumulativeInterest().setScale(2, RoundingMode.HALF_EVEN));
        	  addComponent(totalInterestPaid);
          }
          else{
        	  totalInterestPaid.setValue("Total interest paid: " + repaymentPlan.getCumulativeInterest().setScale(2, RoundingMode.HALF_EVEN));  
          }
          
        } 
		catch (CommitException e) {
			e.printStackTrace();
          Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
