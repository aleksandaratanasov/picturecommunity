package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.PicturecommunityMainController;
import com.example.picturecommunity.controller.RegisterController;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@PreserveOnRefresh
public class RegisterView extends VerticalLayout implements View {

	public RegisterView() {
		setSizeFull();
		setSpacing(true);
		TextField username = new TextField("Username");
		PasswordField password = new PasswordField("Password");
		PasswordField repeatedPassword = new PasswordField("Repeat Password");
		Label userAlreadyExistsLabel = new Label("username already exists");
		Label passwordIsInvalidLabel = new Label("password is invalid");
		Label usernameIsMissingLabel = new Label("enter a username");
		userAlreadyExistsLabel.setVisible(false);
		passwordIsInvalidLabel.setVisible(false);
		usernameIsMissingLabel.setVisible(false);

		Button registerButton = new Button("Create Account",
				new Button.ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						RegisterController model = new RegisterController();
						if (!username.isEmpty()) {
							if (model.checkUsername(username.getValue())) {
								if (model.checkPassword(password.getValue(),
										repeatedPassword.getValue())) {
									model.createUser(username.getValue(),
											password.getValue(),
											repeatedPassword.getValue());
									
									VaadinSession.getCurrent().setAttribute("username", username.getValue());
									
									getUI().getNavigator().navigateTo(
											PicturecommunityMainController.PERSONALDASHBOARDVIEW);
								} else {
									passwordIsInvalidLabel.setVisible(true);
								}
							} else {
								userAlreadyExistsLabel.setVisible(true);
							}
						} else {
							usernameIsMissingLabel.setVisible(true);
						}

					}
				});

		addComponent(username);
		addComponent(password);
		addComponent(repeatedPassword);
		addComponent(registerButton);
		addComponent(userAlreadyExistsLabel);
		addComponent(passwordIsInvalidLabel);
		addComponent(usernameIsMissingLabel);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
