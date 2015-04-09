package com.example.picturecommunity.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonalDashboardView extends VerticalLayout implements View {
	
	public PersonalDashboardView() {
		setSizeFull();
		setSpacing(true);
		addComponent(new MenuViewComponent());
		addComponent(new Label("Hello PersonalDashboardView!"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("In this view you can manage your contacts and picture collection");
	}

}
