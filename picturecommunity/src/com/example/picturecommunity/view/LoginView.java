package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.Broadcaster;
import com.example.picturecommunity.controller.PicturecommunityMainController;
import com.example.picturecommunity.controller.LoginController;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Alignment;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout implements View {
	
	private LoginController controller;

	public LoginView(PicturecommunityMainController app) {
		controller = new LoginController();
		setSizeFull();
		setSpacing(true);
		Label label = new Label(
				"<center><h1>Welcome to Picture Community!</h1><br/>Enter your information below to log in.</center>");
		label.setContentMode(ContentMode.HTML);
		TextField username = new TextField("Username");
		PasswordField password = new PasswordField("Password");
		Label failedLabel = new Label(
				"The username or password you entered is incorrect");
		failedLabel.setVisible(false);
		failedLabel.setSizeUndefined();
		failedLabel.setHeight("200px");
		Button loginButton = new Button("Log In", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// Verify if user is present in the DB
				if (controller.validate(username.getValue(), password.getValue())) {
					Broadcaster.broadcast(username.getValue(), "Online");
					//getUI().getSession().setAttribute("username", username);
					//VaadinService.getCurrentRequest().getWrappedSession().setAttribute("username", username);
					// check, if user is an admin
					if (username.getValue().equals("Admin")) {
						getUI().getNavigator().navigateTo(
								PicturecommunityMainController.ADMINVIEW);
					} else {
						getUI().getNavigator().navigateTo(
								PicturecommunityMainController.PERSONALDASHBOARDVIEW);
					}

				} else {
					failedLabel.setVisible(true);
				}
			}
		});

		Button registerButton = new Button("Create account",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						getUI().getNavigator().navigateTo(
								PicturecommunityMainController.REGISTERVIEW);

					}
				});
		loginButton.setStyleName(BaseTheme.BUTTON_LINK);
		registerButton.setStyleName(BaseTheme.BUTTON_LINK);

		addComponent(label);
		addComponent(username);
		addComponent(password);
		
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.addComponent(loginButton);
		buttonsLayout.addComponent(registerButton);
		//addComponent(loginButton);
		//addComponent(registerButton);
		addComponent(buttonsLayout);
		addComponent(failedLabel);
		this.setComponentAlignment(label, Alignment.TOP_RIGHT);
		this.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);
		//this.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);
		//this.setComponentAlignment(registerButton, Alignment.BOTTOM_RIGHT);
		this.setComponentAlignment(failedLabel, Alignment.BOTTOM_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome! Please log in.");
	}

}