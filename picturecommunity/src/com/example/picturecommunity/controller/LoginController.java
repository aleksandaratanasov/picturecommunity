package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.example.picturecommunity.model.User;
import com.vaadin.server.VaadinSession;

public class LoginController {

	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;

	public LoginController() {
	}

	/**
	 * Check if a given user is present in the database and the given password is correct
	 * @param username A valid username
	 * @param password A valid password for the given username
	 * @return 
	 */
	public boolean validate(String username, String password) {
		boolean isValid = false;
		if (username.isEmpty()) return isValid;
		else {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :login AND u.password = :pass");
			q.setParameter("login", username);
			q.setParameter("pass", password);
			try {
				User user = (User) q.getSingleResult();
				if (username.equalsIgnoreCase(user.getUserName()) && password.equals(user.getPassword())) isValid = true;
			} catch (Exception e) {
				return false;
			}
			
			VaadinSession.getCurrent().setAttribute("username", username);

			return isValid;
		}
	}
}
