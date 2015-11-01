package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class LoginController {

	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;

	public LoginController() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	/**
	 * Check if a given user is present in the database and the given password is correct
	 * @param username A valid username
	 * @param password A valid password for the given username
	 * @return 
	 */
	public boolean validate(String username, String password) {
		boolean isValid = true;
		if (username.isEmpty()) return false;
		else {
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :login AND u.password = :pass");
			q.setParameter("login", username);
			q.setParameter("pass", password);
			try {
				User user = (User) q.getSingleResult();
				// We only need to check if user == null since we already retrieve a possible hit using the given username and password from the table
				if(user == null) isValid = false;
				//if (username.equalsIgnoreCase(user.getUserName()) && password.equals(user.getPassword())) isValid = true;
				EntityTransaction entr = em.getTransaction();
				entr.begin();
				user.setStatus("Online");
				entr.commit();
			} catch (Exception e) {
				return false;
			}
			finally {
				em.close();
			}
			VaadinSession.getCurrent().setAttribute("username", username);
			//VaadinService.getCurrentRequest().getWrappedSession().setAttribute("username", username);

			return isValid;
		}
	}
}
