package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;

public class RegisterController {
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;

	public RegisterController() {

	}

	/**
	 * Creates a new user based on a username and a password
	 * @param username Currently any string can be used for a username
	 * @param password Currently any string can be used for a password
	 * @param repeatedPassword Has to be the same as password
	 */
	public void createUser(String username, String password,
		String repeatedPassword) {
		if (checkUsername(username) && checkPassword(password, repeatedPassword)) {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
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

	/**
	 * TODO User UserController for checking if a user is present
	 * @param username
	 * @return
	 */
	public boolean checkUsername(String username) {
		boolean isValid = false;

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :login");
		q.setParameter("login", username);

		try {
			User user = (User) q.getSingleResult();
			isValid = !username.equalsIgnoreCase(user.getUserName());
			/*if (username.equalsIgnoreCase(user.getUserName())) {
				isValid = false;
			} else {
				isValid = true;
			}*/
		} catch (Exception e) {
			return true;
		}

		return isValid;
	}

	/**
	 * Check if password and repeated password are the same
	 * @param password Currently any string can be used for a password
	 * @param repeatedPassword Has to be the same as password
	 * @return
	 */
	public boolean checkPassword(String password, String repeatedPassword) {
		/*boolean isValid = false;
		if (password.isEmpty() || !password.equals(repeatedPassword)) {

		} else {
			isValid = true;
		}
		return isValid;*/
		return !(password.isEmpty() || !password.equals(repeatedPassword));
	}

}
