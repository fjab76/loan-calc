package fjab.loancalc.view;

import java.math.BigDecimal;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class LoanForm extends FormLayout {
	
	private TextField annualInterestRate = new TextField("Annual interest rate:");
	private TextField loanAmount = new TextField("Loan amount:");
	private TextField numberAnnualPayments = new TextField("Number annual payments:");
	private TextField loanLengthYears = new TextField("Loan length in years:");
	private TextField loanLengthMonths = new TextField("Loan length in months:");
	
	FieldGroup fieldGroup;

	public LoanForm(){
		
		setSizeUndefined();
		setMargin(true);
		
		fieldGroup = new BeanFieldGroup<>(LoanBean.class);
		fieldGroup.setItemDataSource(new BeanItem<>(new LoanBean()));
		fieldGroup.bindMemberFields(this);
		
		addComponent(annualInterestRate);
	    addComponent(loanAmount);
	    addComponent(numberAnnualPayments);
	    addComponent(loanLengthYears);
	    addComponent(loanLengthMonths);
	    addComponent(createOkButton());
		
	}
	
	private Button createOkButton() {
	    Button okButton = new Button("OK");
	    okButton.addClickListener(new ClickListener() {
	      @Override
	      public void buttonClick(ClickEvent event) {
	        try {
	          fieldGroup.commit();
	          Notification.show("Form committed");
	          
	          LoanBean loanBean = new LoanBean((BigDecimal)fieldGroup.getItemDataSource().getItemProperty("annualInterestRate").getValue(),
	        		  						   (BigDecimal)fieldGroup.getItemDataSource().getItemProperty("loanAmount").getValue(),
	        		  						   (Integer)fieldGroup.getItemDataSource().getItemProperty("numberAnnualPayments").getValue(),
	        		  						   (Integer)fieldGroup.getItemDataSource().getItemProperty("loanLengthYears").getValue(),
	        		  						   (Integer)fieldGroup.getItemDataSource().getItemProperty("loanLengthMonths").getValue());
	          System.out.println(loanBean);
	        } catch (CommitException e) {
	          Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
	        }
	      }
	    });
	    return okButton;
	  }

}
