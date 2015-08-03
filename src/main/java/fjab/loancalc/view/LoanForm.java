package fjab.loancalc.view;

import java.math.BigDecimal;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import fjab.loancalc.presenter.LoanPresenter;
import fjab.loancalc.service.LoanServiceImp;

import com.vaadin.ui.Button.ClickEvent;

public class LoanForm extends FormLayout {
	
	private TextField name = new TextField("Name:");
	private Label greeting = new Label();
	
	private TextField description = new TextField("Write a description for this loan:");
	// Counter for input length
	private Label counter = new Label();
	
	private TextField annualInterestRate = new TextField("Annual interest rate:");
	private TextField loanAmount = new TextField("Loan amount:");
	private TextField numberAnnualPayments = new TextField("Number annual payments:");
	private TextField loanLengthYears = new TextField("Loan length in years:");
	private TextField loanLengthMonths = new TextField("Loan length in months:");
	private Button okButton = new Button("OK");
	FieldGroup fieldGroup;

	public LoanForm(){
		
		//Customising form layout
		setSizeUndefined();
		setMargin(true);
		
		//Customising form elements
		name.addTextChangeListener(event -> createNameTextChangeListener(event));
		name.setTextChangeEventMode(TextChangeEventMode.EAGER);
		
		description.setMaxLength(20);
		description.addTextChangeListener(event -> createDescriptionTextChangeListener(event));
		description.setTextChangeEventMode(TextChangeEventMode.EAGER);
		counter.setValue(description.getValue().length() +" of " + description.getMaxLength());
		
		annualInterestRate.setNullRepresentation("");
		loanAmount.setNullRepresentation("");
		numberAnnualPayments.setNullRepresentation("");
		loanLengthYears.setNullRepresentation("");
		loanLengthMonths.setNullRepresentation("");
		
		okButton.addClickListener(event -> createClickListener(event));
		
		//Binding form elements to bean
		fieldGroup = new BeanFieldGroup<>(LoanBean.class);
		fieldGroup.setItemDataSource(new BeanItem<>(new LoanBean()));
		fieldGroup.bindMemberFields(this);
		
		//Adding elements to the form
		addComponent(name);
		addComponent(greeting);
		addComponent(description);
		addComponent(counter);
		addComponent(annualInterestRate);
	    addComponent(loanAmount);
	    addComponent(numberAnnualPayments);
	    addComponent(loanLengthYears);
	    addComponent(loanLengthMonths);
	    addComponent(okButton);
	    
	    
		
	}
	
	private void createNameTextChangeListener(TextChangeEvent event) {
		greeting.setValue(event.getText().length()==0 ? "" : "Hello " + event.getText());
	}

	private void createClickListener(ClickEvent event) {
		try 
		{
          fieldGroup.commit();
          Notification.show("Form committed");
          
          LoanBean loanBean = new LoanBean((BigDecimal)fieldGroup.getItemDataSource().getItemProperty("annualInterestRate").getValue(),
        		  						   (BigDecimal)fieldGroup.getItemDataSource().getItemProperty("loanAmount").getValue(),
        		  						   (Integer)fieldGroup.getItemDataSource().getItemProperty("numberAnnualPayments").getValue(),
        		  						   (Integer)fieldGroup.getItemDataSource().getItemProperty("loanLengthYears").getValue(),
        		  						   (Integer)fieldGroup.getItemDataSource().getItemProperty("loanLengthMonths").getValue());
          System.out.println(loanBean);
          new LoanPresenter(new LoanServiceImp(), loanBean).calculateRepaymentPlan();
        } 
		catch (CommitException e) {
          Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createDescriptionTextChangeListener(TextChangeEvent event){
		int len = event.getText().length();
        counter.setValue(len + " of " + description.getMaxLength());
	}
}
