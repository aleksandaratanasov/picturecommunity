package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;

public class LoginModel {

	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;

	public LoginModel() {
	}

	public boolean validate(String username, String password) {
		boolean isValid = false;
		if (username.isEmpty()) {
			return isValid;
		} else {
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			Query q = em
					.createQuery("SELECT u FROM User u WHERE u.username = :login AND u.password = :pass");
			q.setParameter("login", username);
			q.setParameter("pass", password);
			try {
				User user = (User) q.getSingleResult();
				if (username.equalsIgnoreCase(user.username)
						&& password.equals(user.password)) {
					isValid = true;
				}
			} catch (Exception e) {
				return false;
			}

			return isValid;
		}
	}
}
