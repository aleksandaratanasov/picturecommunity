package com.example.picturecommunity.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.model.User;
import com.example.picturecommunity.view.GalleryImageViewComponent;
import com.vaadin.server.VaadinSession;

public class GalleryController {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	private List<User> users;
	private int height, width;
	private List<String> usernames;
	private int numOfImages = 0;
	
	public GalleryController(boolean isPersonal) {
		usernames = new ArrayList<String>();
		users = new ArrayList<User>();
		if(isPersonal) {
			String username = (String)VaadinSession.getCurrent().getAttribute("username");
			User currentUser = UserController.findUserbyName(username);
			users.add(currentUser);
		}else {
			users = UserController.getAllUsers();
			
		}
		
	
		
		try {
			numOfImages += getImages(isPersonal).size();
			
		}
		catch(NullPointerException e) {
			numOfImages = 0;
		}
		
		if(numOfImages == 0) return;
		
		// Currently the maximum number of GalleryImageViewComponents that can be displayed horizontally is 4 (this should be made adaptable to the size of the browser window)
		// Case 1: 
		if(numOfImages < 5) {
			height = 1;
			width = numOfImages;
		}
		else {
			for(int i = 0; i < numOfImages; i++) {
				if(i % 4 == 0) height++;
			}
			width = 4;
		}
		
	}
	
	// Called in GalleryViewComponent when a GalleryImageViewComponents is to be added
	/*public GalleryImageViewComponent addGalleryImage(Image img) {
		return new GalleryImageViewComponent(img);
	}*/
	
	public int getNumOfImages() {
		return numOfImages;
	}
	
	public int getGridCellsHorizontal() {
		return width;
	}
	
	public int getGridCellsVertical() {
		return height;
	}
	
	public LinkedList<GalleryImageViewComponent> getImages(boolean isPersonal) {
		
		LinkedList<GalleryImageViewComponent> images = new LinkedList<GalleryImageViewComponent>();
		if(isPersonal) {
			//Pictures from current user only  
			for(User user : users) {
				for (Image img : user.getImages()) {
					images.add(new GalleryImageViewComponent(img, isPersonal));
				}
			}
		}else {
			//ALL public pictures from ALL users 
			//ALL private pictures from current user and all users that have current user as friend
			String username = (String)VaadinSession.getCurrent().getAttribute("username");

			for(User user : users) {
				boolean isFriend = false;
				for(User contact : user.getContacts()) {
					if(contact.getUserName().equals(username)) {
						isFriend = true;
						break;
					}
				}
				if(user.getUserName().equals(username) || isFriend) {
					for(Image img : user.getImages()) {
						images.add(new GalleryImageViewComponent(img, isPersonal));
					}
				}
				else {
					for(Image img : user.getImages()) {
						if(img.getViewStatus()) {
							images.add(new GalleryImageViewComponent(img, isPersonal));
						}
					}
				}
			}
		}
		return images;
	}
}
