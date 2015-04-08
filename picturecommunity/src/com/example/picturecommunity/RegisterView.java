package com.example.picturecommunity;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RegisterView extends CustomComponent {

	private static final long serialVersionUID = 8693696426180666493L;
	TextField username = new TextField("Benutzername");
	PasswordField password = new PasswordField("Passwort");
	PasswordField repeatedPassword = new PasswordField("Wiederhole Passwort");
	Button register = new Button("Registrieren");

	public RegisterView() {
		Layout layout = new VerticalLayout();

		layout.addComponent(username);
		layout.addComponent(password);
		layout.addComponent(repeatedPassword);
		layout.addComponent(register);

		setCompositionRoot(layout);

		setSizeFull();
	}
}
