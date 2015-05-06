package com.example.picturecommunity.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;
import com.vaadin.ui.Notification;

/*
 * NOTES:
 * http://www.javacodegeeks.com/2013/11/efficiently-delete-data-with-jpa-and-hibernate.html
 * http://www.objectdb.com/java/jpa/persistence/store
 */

/*
public class AdminModel {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	private Set<Long> usersForDeletion;
	private List<User> users;
	
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
	
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		factory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		return (List<User>)em.createQuery(
				"SELECT u FROM User u ORDER BY u.username ASC")
			    .getResultList();
	}
	
	public void markUserForDeletion(User user) {
		// since we are using a set repetition is not possible so no need to check if ID is already present
		usersForDeletion.add(user.getId());
	}
	
	public void unmarkUserForDeletion(User user) {
		// since we are using a set repetition is not possible so no need to check if ID is already present (besides remove() also does a check internally)
		usersForDeletion.remove(user.getId());
	}
	
	public void deleteUsers() {
		
		if(usersForDeletion.size() == 0) return;
		
		factory = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q;
		
		for (Long userId : usersForDeletion) {
			q = em.createQuery("DELETE FROM User u WHERE u.id = :dirtyUserId")
				.setParameter("dirtyUserId", userId);
		}
	}
}
*/

public class AdminModel {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	private List<Long> usersForDeletion;
	private List<User> users;
	private boolean toggleAllForDeletionFlag;
	
	public AdminModel() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		users = getUsers();
		usersForDeletion = new LinkedList<Long>();
		toggleAllForDeletionFlag = false;
	}

	////////////////////////// User retrieval //////////////////////////
	// Retrieve a fixed number of users (used for the chart)
	// If amount == 0 complete list of users is retrieved from the database (see getUsers())
	@SuppressWarnings("unchecked")
	public List<User> getUsers(int amount) {
		EntityManager em = factory.createEntityManager();
		
		List<User> users = null;
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			if(amount < 0)
				users = (List<User>)em.createQuery(
						"SELECT u FROM User u ORDER BY u.username ASC")
						.getResultList();
			else
				users = (List<User>)em.createQuery(
						"SELECT u FROM User u ORDER BY u.uploads DESC")
					    .setMaxResults(amount)
					    .getResultList();
			tx.commit();
		}
		catch(Exception ex) {
			Notification.show(ex.getMessage());
		}
		em.close();
		
		if(amount < 0)
			this.users = users;
		
		return users;
	}
	
	// Retrieve complete list of users (used for the user management table)
	public List<User> getUsers() {
		return getUsers(-1);
	}
	
	////////////////////////// User deletion //////////////////////////
	// Get the deletion status of a user
	// If user is present in the list usersForDeletion then he/she is marked as ready for deletion and TRUE will be returned
	public boolean checkUserStatusForDeletion(long userId) {
		return usersForDeletion.contains(userId);
	}
	
	// Called when checking the checkbox for a selected user marking him/her as ready for deletion
	public void markUserForDeletion(long userId) {
		usersForDeletion.add(userId);
	}
	
	// Called when checking the checkbox for a selected user unmarking him/her as ready for deletion
	public void unmarkUserForDeletion(long userId) {
		usersForDeletion.remove(userId);	// remove() also checks whether the item is in the list so no need to do additional handling here
	}
	
	// Called when (un)checking the "select all" checkbox (un)marking all users as ready for deletion
	// Returned value is used for the CheckBox component responsible for triggering this function
	public boolean toggleAllUsersForDeletion() {
		if(toggleAllForDeletionFlag) usersForDeletion.clear();
		else for (User user : users) usersForDeletion.add(user.getId());
		
		toggleAllForDeletionFlag = !toggleAllForDeletionFlag;
		
		return toggleAllForDeletionFlag;
	}
	
	// Called when checking the "select all" checkbox unmarking all users as ready for deletion
	//public void unmarkAllUsersForDeletion() {
	//	usersForDeletion.clear();
	//}
	
	// Delete a single user from the database using his/her ID
	private void deleteUser(long userId) {
		EntityManager em = factory.createEntityManager();

		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Query q = em.createQuery(
					"DELETE FROM User u WHERE u.id = :id")
					.setParameter("id", userId);
			if(q.executeUpdate() < 0) throw new Exception("Shitty documention on executeUpdate() doesn't give any information on the return codes...Nice!");
			tx.commit();
		}
		catch(Exception ex) {
			Notification.show(ex.getMessage());
		}
		em.close();
	}
	
	// Delete all users from the database marked for deletion
	public void deleteUsers() {
		if(usersForDeletion.size() == 0) return;
		for (Long userId : usersForDeletion) deleteUser(userId);
	}
}