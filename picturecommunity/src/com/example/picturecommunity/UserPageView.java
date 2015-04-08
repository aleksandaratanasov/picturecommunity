package com.example.picturecommunity;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class UserPageView extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3612199777912616492L;

	TextField text = new TextField("Herzlich Willkommen auf Picture Community");

	public UserPageView() {
		Layout layout = new VerticalLayout();

		layout.addComponent(text);
		setCompositionRoot(layout);

		setSizeFull();
	}

}
