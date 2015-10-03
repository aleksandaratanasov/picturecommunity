package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.PersonalDashboardController;
import com.example.picturecommunity.controller.PicturecommunityMainController;
import com.example.picturecommunity.controller.UserController;
import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.model.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PersonalDashboardView extends VerticalLayout implements View {
	
	HorizontalLayout mainView;
	VerticalLayout sideMenu;
	MenuViewComponent mvc;
	FriendsViewComponent fvc;
	ImageUploadViewComponent iuvc;
	GalleryViewComponent personal_gallery;
	Label greeting;
	//User current_user;
	boolean initialTrigger = true;
	private PersonalDashboardController controller;

	public PersonalDashboardView(PicturecommunityMainController app) {
		setSizeFull();
		setSpacing(true);
		mvc = new MenuViewComponent(false);
		mainView = new HorizontalLayout();
		sideMenu = new VerticalLayout();
		addComponent(mvc);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("In this view you can manage your contacts and picture collection");
		
		String username = (String)VaadinSession.getCurrent().getAttribute("username");
		greeting = new Label("Hello " + username + "!");
		fvc = new FriendsViewComponent();
		iuvc = new ImageUploadViewComponent();
		personal_gallery = new GalleryViewComponent(username);
		
		// One time trigger for adding the required components upon entering the view
		// After that only the objects that the components contains will be updated (see above)
		if(initialTrigger) {
			controller = new PersonalDashboardController();
			sideMenu.addComponent(greeting);
			sideMenu.addComponent(fvc);
			sideMenu.addComponent(iuvc);
			mainView.addComponent(sideMenu);
			mainView.addComponent(personal_gallery);	// Uncomment when ready or testing
			addComponent(mainView);
			initialTrigger = false;
		}
		
		// Move this to the controller and populate GalleryViewComponent using "getAllGalleryImageComponents()" (returns a list of components
		// Code below is currently only FOR TESTING purposes
		/*current_user = UserController.findUserbyName(username);
		if(current_user != null)
			for (Image img : current_user.getImages()) {
				addComponent(new GalleryImageViewComponent(img));
			}*/
	}

}
