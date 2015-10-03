package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.vaadin.ui.Notification;

public class GalleryImageController {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	
	public GalleryImageController() {
		
	}
	
	public void changeViewStatus(boolean viewStatus) {
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
