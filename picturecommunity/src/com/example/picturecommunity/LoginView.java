package com.example.picturecommunity;

import com.gargoylesoftware.htmlunit.javascript.host.Text;
import com.google.gwt.user.client.ui.TextArea;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

class LoginView extends CustomComponent {

	private static final long serialVersionUID = -3720772451603284972L;
	TextField username = new TextField("Benutzername");
	PasswordField password = new PasswordField("Passwort");
	Button loginbutton = new Button("Login");
	Link register = new Link("Registrieren!", new ExternalResource("http://www.google.de/"));

	public LoginView() {
		Layout layout = new VerticalLayout();

		layout.addComponent(username);
		layout.addComponent(password);
		layout.addComponent(loginbutton);
		layout.addComponent(register);

		setCompositionRoot(layout);

		setSizeFull();
	}

}
