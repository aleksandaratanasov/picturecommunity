package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;

public class RegisterModel {
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;

	public RegisterModel() {

	}

	public void createUser(String username, String password,
			String repeatedPassword) {
		if (checkUsername(username)
				&& checkPassword(password, repeatedPassword)) {
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			try {
				EntityTransaction entr = em.getTransaction();
				entr.begin();
				User u = new User();
				u.setUserName(username);
				u.setPassword(password);
				em.persist(u);
				entr.commit();
			} finally {
				em.close();
			}
		}
	}

	public boolean checkUsername(String username) {
		boolean isValid = false;

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em
				.createQuery("SELECT u FROM User u WHERE u.username = :login");
		q.setParameter("login", username);

		try {
			User user = (User) q.getSingleResult();
			if (username.equalsIgnoreCase(user.getUserName())) {
				isValid = false;
			} else {
				isValid = true;
			}
		} catch (Exception e) {
			return true;
		}

		return isValid;
	}

	public boolean checkPassword(String password, String repeatedPassword) {
		boolean isValid = false;
		if ( password.isEmpty() || !password.equals(repeatedPassword)) {

		} else {
			isValid = true;
		}
		return isValid;
	}

}
