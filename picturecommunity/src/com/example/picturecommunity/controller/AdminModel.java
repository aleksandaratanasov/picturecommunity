package com.example.picturecommunity.controller;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;

/*
 * NOTES:
 * http://www.javacodegeeks.com/2013/11/efficiently-delete-data-with-jpa-and-hibernate.html
 */

public class AdminModel {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	private LinkedList<User> usersForDeletion;
	
	public AdminModel() {
		
	}

	@SuppressWarnings("unchecked")
	public List<User> getUploadStats(int numOfUploaders) {
		factory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		return (List<User>)em.createQuery(
				"SELECT u FROM User u ORDER BY u.uploads DESC")
			    .setMaxResults(numOfUploaders)
			    .getResultList();
	}
	
	public void markUserForDeletion(User user) {
		usersForDeletion.add(user);
	}
	
	public void deleteUsers() {
		
		if(usersForDeletion.size() == 0) return;
		
		factory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("DELETE FROM my_user USING :dirtyUsers");
	}
}
