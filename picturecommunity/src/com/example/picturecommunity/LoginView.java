package com.example.picturecommunity;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Alignment;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout implements View {
	
	public LoginView() {
		setSizeFull();
		setSpacing(true);
		Label label = new Label("<center><h1>Vaadin Navigator Views</h1><br/>Enter your information below to log in.</center>");
		label.setContentMode(ContentMode.HTML);
		TextField username = new TextField("Username");
		PasswordField password = new PasswordField("Password");
		Button loginButton = new Button("Log In", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// Verify if user is present in the DB
				// TODO
				
				// If login == ADMIN navigateTo ADMINVIEW else go navigateTo PERSONALDASHBOARDVIEW
				// Note: a more sophisticated mechanism for authentification is required here; this is just an example!
				if(username.getValue().equals("hello") && password.getValue().equals("world"))
					getUI().getNavigator().navigateTo(PicturecommunityUI.ADMINVIEW);
				else
					getUI().getNavigator().navigateTo(PicturecommunityUI.PERSONALDASHBOARDVIEW);
			}
		});
		loginButton.setStyleName(BaseTheme.BUTTON_LINK);
		
		addComponent(label);
		addComponent(username);
		addComponent(password);
		addComponent(loginButton);
		this.setComponentAlignment(label, Alignment.TOP_RIGHT);
		this.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(loginButton, Alignment.BOTTOM_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome! Please log in.");
	}

}
