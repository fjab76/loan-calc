package fjab.loancalc.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

import fjab.loancalc.presenter.LoanPresenter;
import fjab.loancalc.service.LoanServiceImp;
import fjab.loancalc.service.Repayment;
import fjab.loancalc.service.RepaymentPlan;

public class LoanForm extends FormLayout {
	
	private static final int VAADIN_DEFAULT_PAGE_LENGTH = 15;
	
	private final TextField annualInterestRate;
	private final TextField loanAmount;
	// A single-select radio button group
	private final OptionGroup repaymentPeriodicity;
	private final Label loanLength;
	private final TextField loanLengthYears;
	private final TextField loanLengthMonths;
	private final Button okButton;
	private final Table table;
	
	private Label periodicPayment;
	private Label totalInterestPaid;
	
	

	public LoanForm(){				

		//Customising form layout
		setSizeUndefined();
		setMargin(true);
						
		//Creating and configuring fields of the form
		FormElementFactory fef = FormElementFactory.getInstance();
		annualInterestRate = (TextField) fef.createComponent(FormElement.ANNUAL_INTEREST_RATE);
		addComponent(annualInterestRate);
		loanAmount = (TextField) fef.createComponent(FormElement.LOAN_AMOUNT);
		addComponent(loanAmount);
		repaymentPeriodicity = (OptionGroup) fef.createComponent(FormElement.REPAYMENT_PERIODICITY);
		addComponent(repaymentPeriodicity);
		loanLength = (Label) fef.createComponent(FormElement.LOAN_LENGTH);
		addComponent(loanLength);
		loanLengthYears = (TextField)fef.createComponent(FormElement.LOAN_LENGTH_YEARS);
		addComponent(loanLengthYears);
		loanLengthMonths = (TextField)fef.createComponent(FormElement.LOAN_LENGTH_MONTHS);
		addComponent(loanLengthMonths);
		okButton = (Button) fef.createComponent(FormElement.OK_BUTTON);
		addComponent(okButton);
		table = (Table) fef.createComponent(FormElement.AMORTIZATION_TABLE);
		
		//Binding form elements to bean
		FieldGroup fieldGroup = new BeanFieldGroup<>(LoanBean.class);
		fieldGroup.setItemDataSource(new BeanItem<>(new LoanBean()));
		fieldGroup.bindMemberFields(this);
		
		
		//Adding listeners to fields of the form
		loanLengthYears.addValueChangeListener(event -> createLoanLengthValueChangeListener(event,loanLengthYears,loanLengthMonths));
		loanLengthMonths.addValueChangeListener(event -> createLoanLengthValueChangeListener(event,loanLengthMonths,loanLengthYears));
		okButton.addClickListener(event -> createClickListener(event,fieldGroup));
		

		
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

	private void createClickListener(ClickEvent event, FieldGroup fieldGroup) {
		try 
		{
          fieldGroup.commit();
          Notification.show("Form committed");
          
          LoanBean loanBean = new LoanBean((BigDecimal)fieldGroup.getItemDataSource().getItemProperty("annualInterestRate").getValue(),
        		  						   (BigDecimal)fieldGroup.getItemDataSource().getItemProperty("loanAmount").getValue(),
        		  						   (Periodicity)fieldGroup.getItemDataSource().getItemProperty("repaymentPeriodicity").getValue(),        		  						   
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
          
          renderAmortizationTable(repaymentPlan.getRepaymentPlan());
          
        } 
		catch (CommitException e) {
			e.printStackTrace();
          Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void renderAmortizationTable(List<Repayment> repaymentPlan) {

		if(table.getItemIds().size()>0){
			removeComponent(table);
			table.removeAllItems();
		}
		addComponent(table);
		
		// Define columns for the built-in container
		//[periodNumber=1, paymentNumber=1, startBalance=10000.00, payment=855.57, capitalPaidOff=814.82, interestPaid=40.74, cumulativeCapitalPaidOff=814.82, cumulativeInterest=40.74, endBalance=9185.18, totalCostToDate=855.57]
		table.addContainerProperty("periodNumber", Integer.class, null);
		table.addContainerProperty("paymentNumber",  Integer.class, null);
		table.addContainerProperty("startBalance",  BigDecimal.class, null);
		table.addContainerProperty("payment",  BigDecimal.class, null);
		table.addContainerProperty("capitalPaidOff",  BigDecimal.class, null);
		table.addContainerProperty("interestPaid",  BigDecimal.class, null);
		table.addContainerProperty("endBalance",  BigDecimal.class, null);
		
		// Add a few other rows using shorthand addItem()
		for(int j=0; j<repaymentPlan.size(); j++){
			table.addItem(new Object[]{repaymentPlan.get(j).getPeriodNumber(),
									   repaymentPlan.get(j).getPeriodNumber(),
									   repaymentPlan.get(j).getStartBalance().setScale(2, RoundingMode.HALF_EVEN),
									   repaymentPlan.get(j).getPayment().setScale(2, RoundingMode.HALF_EVEN),
									   repaymentPlan.get(j).getCapitalPaidOff().setScale(2, RoundingMode.HALF_EVEN),
									   repaymentPlan.get(j).getInterestPaid().setScale(2, RoundingMode.HALF_EVEN),
									   repaymentPlan.get(j).getEndBalance().setScale(2, RoundingMode.HALF_EVEN)},j);
		}

		if(table.size()<VAADIN_DEFAULT_PAGE_LENGTH){
			table.setPageLength(table.size());
		}	
		else{
			table.setPageLength(VAADIN_DEFAULT_PAGE_LENGTH);
		}
		
	}
	
	
}
