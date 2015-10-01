package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.PicturecommunityMainController;
import com.example.picturecommunity.controller.UserController;
import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.model.User;
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
		addComponent(new MenuViewComponent(false));
		
		
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
		
		// Move this to the controller and populate GalleryViewComponent using "getAllGalleryImageComponents()" (returns a list of components
		User u = UserController.findUserbyName(username);
		if(u != null)
			for (Image img : u.getImages()) {
				addComponent(new GalleryImageViewComponent(img));
			}
	}

}
