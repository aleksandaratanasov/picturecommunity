package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import com.example.picturecommunity.model.Image;
import com.vaadin.ui.Notification;

public class GalleryImageController {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	
	private Image img;
	
	public GalleryImageController(Image _img) {
		img = _img;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
	public void changeViewStatus(boolean viewStatus) {
		EntityManager em = factory.createEntityManager();
		
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
				Image imgEntity = em.find(Image.class, img.getId());
				imgEntity.setViewStatus(viewStatus);
			tx.commit();
		}
		catch(Exception ex) {
			Notification.show(ex.getMessage());
		}
		em.close();
	}
	
	public Image getImage() {
		return img;
	}
}
