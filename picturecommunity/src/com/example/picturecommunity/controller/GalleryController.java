package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.view.GalleryImageViewComponent;
import com.vaadin.ui.Notification;

public class GalleryController {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	
	public GalleryController() {
		
	}
	
	// Called in GalleryViewComponent when a GalleryImageViewComponents is to be added
	public GalleryImageViewComponent addGalleryImage(Image img) {
		return new GalleryImageViewComponent(img);
	}
	
	public void changeViewStatus(boolean viewStatus, GalleryImageViewComponent gimg) {
		EntityManager em = factory.createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
				
			tx.commit();
		}
		catch(Exception ex) {
			Notification.show(ex.getMessage());
		}
		em.close();
	}
}
