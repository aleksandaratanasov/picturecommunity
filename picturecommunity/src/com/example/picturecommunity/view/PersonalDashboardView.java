package com.example.picturecommunity.view;

import java.util.ArrayList;



import java.util.List;

import com.example.picturecommunity.controller.DashboardController;
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

	/*
	 * +----------------------------------------------+
	 * |				  MAIN MENU					  |-------------TOP MENU
	 * +----------------------------------------------+
	 * +----------------------------------------------+
	 * | +-------------+ +--------------------------+ |       /-----SIDE MENU + GALLERY LAYOUT
	 * | |             | |                          | |      /
	 * | |  CONTACTS   | |                          | |     /
	 * | |    LIST     | |          GALLERY         |-|----/
	 * | |    +        | |                          | |-------------MAIN LAYOUT
	 * | |  GREETING   | |                          | |
	 * | +-------------+ +--------------------------+ |
	 * | +------|-----------------------------------+ |
	 * | |      |        IMAGE UPLOADER             | |
	 * | +------|-----------------------------------+ |
	 * +--------|-------------------------------------+
	 * 			|
	 * 			|
	 * 			\
	 * 			 \-----SIDE MENU LAYOUT
	 * 
	 */
	
	VerticalLayout topLevelLayout;				// Contains top menu and main layout
	VerticalLayout mainView;					// Contains contacts, gallery and image uploader
	HorizontalLayout sideMenuAndGalleryView;	// Contains contacts+greeting and gallery
	VerticalLayout sideMenu;					// Contains contacts and a user greeting
	
	MenuViewComponent mvc;
	FriendsViewComponent fvc;
	ImageUploadViewComponent iuvc;
	GalleryViewComponent personal_gallery;
	Label greeting;
	//User current_user;
	boolean initialTrigger = true;
	

	public PersonalDashboardView(PicturecommunityMainController app) {
		setSizeFull();
		setSpacing(true);
		
		topLevelLayout = new VerticalLayout();
		mainView = new VerticalLayout();
		sideMenuAndGalleryView = new HorizontalLayout();
		sideMenu = new VerticalLayout();
		
		mvc = new MenuViewComponent(false);
		addComponent(topLevelLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("In this view you can manage your contacts and picture collection");
		
		String username = (String)VaadinSession.getCurrent().getAttribute("username");
		if(username == null) getUI().getNavigator().navigateTo("");
		greeting = new Label("Hello " + username + "!");
		fvc = new FriendsViewComponent();
		iuvc = new ImageUploadViewComponent();
		List<String> users = new ArrayList<String>();
		users.add(username);
		personal_gallery = new GalleryViewComponent(true);
		fvc.resetUserNotFoundLabel();
		// One time trigger for adding the required components upon entering the view
		// After that only the objects that the components contains will be updated (see above)
		if(initialTrigger) {
			
			
			// Populate the side menu
			sideMenu.addComponent(greeting);
			sideMenu.addComponent(fvc);
			// Populate the sideMenu + gallery view
			sideMenuAndGalleryView.addComponent(sideMenu);
			sideMenuAndGalleryView.addComponent(personal_gallery);
			// Populate the main view
			mainView.addComponent(iuvc);
			mainView.addComponent(sideMenuAndGalleryView);
			// Populate the top level layout
			topLevelLayout.addComponent(mvc);
			topLevelLayout.addComponent(mainView);
			// Set panel's layout to top level layout
			addComponent(topLevelLayout);
			
			// Deactivate component insertion
			initialTrigger = false;
		}
		
		/*sideMenu.replaceComponent(fvc, fvc);
		sideMenuAndGalleryView.replaceComponent(sideMenu, sideMenu);
		sideMenuAndGalleryView.replaceComponent(personal_gallery, personal_gallery);
		mainView.replaceComponent(sideMenuAndGalleryView, sideMenuAndGalleryView);
		topLevelLayout.replaceComponent(mainView, mainView);*/
		
		// Move this to the controller and populate GalleryViewComponent using "getAllGalleryImageComponents()" (returns a list of components
		// Code below is currently only FOR TESTING purposes
		/*current_user = UserController.findUserbyName(username);
		if(current_user != null)
			for (Image img : current_user.getImages()) {
				addComponent(new GalleryImageViewComponent(img));
			}*/
	}

}
