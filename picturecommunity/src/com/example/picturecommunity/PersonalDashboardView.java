package com.example.picturecommunity;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import com.example.picturecommunity.Menu;

@SuppressWarnings("serial")
public class PersonalDashboardView extends VerticalLayout implements View {
	
	public PersonalDashboardView() {
		setSizeFull();
		setSpacing(true);
		addComponent(new Menu());
		addComponent(new Label("Hello PersonalDashboardView!"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("In this view you can manage your contacts and picture collection");
	}

}
