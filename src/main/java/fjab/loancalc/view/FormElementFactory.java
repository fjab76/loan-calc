package fjab.loancalc.view;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class FormElementFactory {
	
	private final static FormElementFactory instance = new FormElementFactory();
	private MyStringToBigDecimalConverter converter = new MyStringToBigDecimalConverter();
	
	private FormElementFactory(){}
	
	static FormElementFactory getInstance(){
		return instance;
	}
	
	Component createComponent(FormElement formElement){
		
		if(FormElement.ANNUAL_INTEREST_RATE==formElement){
			TextField component = new TextField("Annual interest rate");
			component.setNullRepresentation("");
			component.addValidator(new NullValidator("Mandatory field", false));
			component.addValidator(new BigDecimalRangeValidator("Value must be between 0 and 1", BigDecimal.ZERO, BigDecimal.ONE));	
			component.setConverter(converter);	
			return component;
		}
		else if(FormElement.LOAN_AMOUNT==formElement){
			TextField component = new TextField("Loan amount");
			component.setNullRepresentation("");
			component.addValidator(new NullValidator("Mandatory field", false));
			component.addValidator(new BigDecimalRangeValidator("Value must be greater than 0", BigDecimal.ZERO, BigDecimal.valueOf(Double.MAX_VALUE)));
			component.setConverter(converter);
			return component;
		}
		else if(FormElement.REPAYMENT_PERIODICITY==formElement){
			OptionGroup component = new OptionGroup("Repayment periodicity");
			component.addItems((Object[])Periodicity.values());
			return component;
		}
		else if(FormElement.LOAN_LENGTH==formElement){
			return new Label("<span style='font-size:125%;'>Loan length</span>",ContentMode.HTML);
		}
		else if(FormElement.LOAN_LENGTH_YEARS==formElement){
			TextField component = new TextField("years");
			component.setNullRepresentation("");
			component.setRequired(true);
			component.setRequiredError("Both loan length years and loan length months cannot be empty");
			component.addValidator(new IntegerRangeValidator("Value must be a positive value", 1, Integer.MAX_VALUE));			
			return component;
		}
		else if(FormElement.LOAN_LENGTH_MONTHS==formElement){
			TextField component = new TextField("months");
			component.setNullRepresentation("");
			component.setRequired(true);
			component.setRequiredError("Both loan length years and loan length months cannot be empty");
			component.addValidator(new IntegerRangeValidator("Value must be a positive value", 1, Integer.MAX_VALUE));			
			return component;
		}
		else if(FormElement.OK_BUTTON==formElement){
			return new Button("OK");
		}
		else if(FormElement.AMORTIZATION_TABLE==formElement){
			return new Table("Amortization table");
		}
		else{
			return null;
		}
	}
	
	private class MyStringToBigDecimalConverter extends StringToBigDecimalConverter{
		
		@Override
	    protected NumberFormat getFormat(Locale locale) {
	        NumberFormat numberFormat = super.getFormat(locale);
	        
	        //By default setMaximumFractionDigits=3 for the default Locale that is en_US
	        numberFormat.setMaximumFractionDigits(10);
	        return numberFormat;
	    }
	}
}
