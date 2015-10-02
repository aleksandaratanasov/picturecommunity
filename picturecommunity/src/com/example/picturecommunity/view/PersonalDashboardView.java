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
	
	MenuViewComponent mvc;
	FriendsViewComponent fvc;
	ImageUploadViewComponent iuvc;
	Label greeting;
	User current_user;
	boolean initialTrigger = true;

	public PersonalDashboardView(PicturecommunityMainController app) {
		setSizeFull();
		setSpacing(true);
		mvc = new MenuViewComponent(false);
		addComponent(mvc);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("In this view you can manage your contacts and picture collection");
		
		String username = (String)VaadinSession.getCurrent().getAttribute("username");
		greeting = new Label("Hello " + username + "!");
		fvc = new FriendsViewComponent();
		iuvc = new ImageUploadViewComponent();
		greeting = new Label("");
		
		// One time trigger for adding the required components upon entering the view
		// After that only the objects that the components contains will be updated (see above)
		if(initialTrigger) {
			addComponent(greeting);
			addComponent(fvc);
			addComponents(iuvc);
			initialTrigger = false;
		}
		
		// Move this to the controller and populate GalleryViewComponent using "getAllGalleryImageComponents()" (returns a list of components
		// Code below is currently only FOR TESTING purposes
		current_user = UserController.findUserbyName(username);
		if(current_user != null)
			for (Image img : current_user.getImages()) {
				addComponent(new GalleryImageViewComponent(img));
			}
	}

}
