package fjab.loancalc.view;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;

/**
 *
 */
@Theme("mytheme")
@Widgetset("fjab.MyAppWidgetset")
public class LoanUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

    	//Creating main container of UI
    	VerticalLayout main = new VerticalLayout();
    	setContent(main);
    	main.setMargin(true);
    	
    	//Creating horizontal container
    	HorizontalLayout hor = new HorizontalLayout();
    	main.addComponent(hor);
    	
    	//Creating panel
    	Panel userInfoPanel = new Panel("User info");
    	hor.addComponent(userInfoPanel);
    	userInfoPanel.setSizeUndefined();
    	
    	//Creating content for the panel. The panel contains user info
    	FormLayout userInfoPanelContent = new FormLayout();
    	userInfoPanel.setContent(userInfoPanelContent);
    	userInfoPanelContent.setSizeUndefined();
    	userInfoPanelContent.setMargin(true);
    	createNameComponent(userInfoPanelContent);
    	createDescriptionComponent(userInfoPanelContent);
    	
    	//Creating loan form
    	hor.addComponent(new LoanForm());
    	
        

    }
    /*protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);

    }*/
    
    private void createNameComponent(Layout layout){
    	TextField name = new TextField("Name");
    	Label greeting = new Label();
    	layout.addComponent(name);
    	layout.addComponent(greeting);
    	
    	name.addTextChangeListener(event -> createNameTextChangeListener(event,greeting));
		name.setTextChangeEventMode(TextChangeEventMode.EAGER);
    }
    
    private void createDescriptionComponent(Layout layout){
    	TextField description = new TextField("Loan description");
    	// Counter for input length
    	Label counter = new Label();
    	layout.addComponent(description);
    	layout.addComponent(counter);
    	
    	description.setMaxLength(20);
		description.addTextChangeListener(event -> createDescriptionTextChangeListener(event,counter,description));
		description.setTextChangeEventMode(TextChangeEventMode.EAGER);
    	
    }
    
    private void createNameTextChangeListener(TextChangeEvent event, Label label) {
		label.setValue(event.getText().length()==0 ? "" : "Hello " + event.getText());
	}
    
    private void createDescriptionTextChangeListener(TextChangeEvent event, Label label, TextField description) {
    	int len = event.getText().length();
        label.setValue(len + " of " + description.getMaxLength());
	}

    @WebServlet(urlPatterns = "/*", name = "LoanUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = LoanUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
