package com.example.picturecommunity;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class allUsersDashboardView extends VerticalLayout implements View {

	public allUsersDashboardView() {
		setSizeFull();
		setSpacing(true);
		addComponent(new Menu());
		addComponent(new Label("Hello AllUsersDashboardView!"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("In this view you can view a collection of selected pictures from other users");
	}

}
