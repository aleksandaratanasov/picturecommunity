package com.example.picturecommunity.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class AdminView extends VerticalLayout implements View {
	
	public AdminView() {
		setSizeFull();
		setSpacing(true);
		addComponent(new MenuViewComponent());
		addComponent(new Label("Hello AdminView!"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("In this view you can manage the users and show some statistics about them");
	}

}
