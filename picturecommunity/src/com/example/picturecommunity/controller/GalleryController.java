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
	private int height, width;
	
	public GalleryController(String username) {
		current_user = UserController.findUserbyName(username);
		int numOfImages;
		try {
			numOfImages = current_user.getImages().size();
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
	
	public User getCurrentUser() {
		return current_user;
	}
	
	public int getGridCellsHorizontal() {
		return width;
	}
	
	public int getGridCellsVertical() {
		return height;
	}
	
	public LinkedList<GalleryImageViewComponent> getImages() {
		LinkedList<GalleryImageViewComponent> images = new LinkedList<GalleryImageViewComponent>();
		for (Image img : current_user.getImages()) {
			images.add(new GalleryImageViewComponent(img));
		}
		return images;
	}
}
