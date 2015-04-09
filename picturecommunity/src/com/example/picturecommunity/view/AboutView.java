package com.example.picturecommunity.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class AboutView extends VerticalLayout implements View {
	
	private Label someText() {
		Label label = new Label("<center><h1>About this project</h1><br/>This project is meant to demonstrate a relatively basic usage of Vaadin's Navigator.</center>");
		label.setContentMode(ContentMode.HTML);
		return label;
	}
	
	public AboutView() {
		setSizeFull();
		setSpacing(true);
		addComponent(new MenuViewComponent());
		addComponent(new Label("Hello AboutView!"));
		addComponent(someText());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Learn something about this project");
	}
}
