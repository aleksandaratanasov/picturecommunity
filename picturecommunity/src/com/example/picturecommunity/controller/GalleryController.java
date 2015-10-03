package com.example.picturecommunity.controller;

import java.util.LinkedList;

import javax.persistence.EntityManagerFactory;
import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.model.User;
import com.example.picturecommunity.view.GalleryImageViewComponent;

public class GalleryController {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	private User current_user;
	
	public GalleryController(String username) {
		current_user = UserController.findUserbyName(username);
	}
	
	// Called in GalleryViewComponent when a GalleryImageViewComponents is to be added
	/*public GalleryImageViewComponent addGalleryImage(Image img) {
		return new GalleryImageViewComponent(img);
	}*/
	
	public LinkedList<GalleryImageViewComponent> getImages() {
		LinkedList<GalleryImageViewComponent> images = new LinkedList<GalleryImageViewComponent>();
		for (Image img : current_user.getImages()) {
			images.add(new GalleryImageViewComponent(img));
		}
		return images;
	}
}
