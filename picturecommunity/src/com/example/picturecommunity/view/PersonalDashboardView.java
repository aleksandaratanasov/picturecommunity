package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.PicturecommunityMainController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonalDashboardView extends VerticalLayout implements View {

	public PersonalDashboardView(PicturecommunityMainController app) {
		setSizeFull();
		setSpacing(true);
		addComponent(new MenuViewComponent());
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification
				.show("In this view you can manage your contacts and picture collection");

		String username = (String) VaadinSession.getCurrent().getAttribute(
				"username");
		Label greeting = new Label("Hello " + username + "!");
		addComponent(greeting);
		addComponent(new FriendsViewComponent());
		addComponents(new ImageUploadViewComponent());
	}

}
