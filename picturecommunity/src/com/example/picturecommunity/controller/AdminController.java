package com.example.picturecommunity.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;

import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.model.User;
import com.sun.xml.internal.ws.message.EmptyMessageImpl;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;

/*
 * NOTES:
 * http://www.javacodegeeks.com/2013/11/efficiently-delete-data-with-jpa-and-hibernate.html
 * http://www.objectdb.com/java/jpa/persistence/store
 * findUserbyId -> entitymanager.find(User.class, id) -> abholen, löschen und dann als transaktion zurück schreiben
 *  -----------------------> updateUploads()
 */

public class AdminController {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	//private List<Long> usersForDeletion;
	private Set<Long> usersForDeletion;
	private List<User> users;
	private boolean toggleAllForDeletionFlag;
	
	public AdminController() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		users = getUsers();
		//usersForDeletion = new LinkedList<Long>();
		usersForDeletion = new HashSet<Long>();
		toggleAllForDeletionFlag = false;
	}

	////////////////////////// User retrieval //////////////////////////
	// Retrieve a fixed number of users (used for the chart)
	// If amount == 0 complete list of users is retrieved from the database (see getUsers())
	@SuppressWarnings("unchecked")
	public List<User> getUsers(int amount) {
		EntityManager em = factory.createEntityManager();
		
		List<User> users = (List<User>)new Vector<User>();
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
		
		if(amount < 0) this.users = users;
		
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
		System.out.println("User \"" + userId + "\" marked for deletion");
		usersForDeletion.add(userId);
	}
	
	// Called when checking the checkbox for a selected user unmarking him/her as ready for deletion
	public void unmarkUserForDeletion(long userId) {
		System.out.println("User \"" + userId + "\" unmarked for deletion");
		usersForDeletion.remove(userId);	// remove() also checks whether the item is in the list so no need to do additional handling here
	}
	
	// Called when (un)checking the "select all" checkbox (un)marking all users as ready for deletion
	// Returned value is used for the CheckBox component responsible for triggering this function
	public boolean toggleAllUsersForDeletion() {
		System.out.println("All users " + (toggleAllForDeletionFlag ? "marked" : "unmarked") + " for deletion");
		if(toggleAllForDeletionFlag) usersForDeletion.clear();
		else for(User user : users) usersForDeletion.add(user.getId());
		
		toggleAllForDeletionFlag = !toggleAllForDeletionFlag;
		
		return toggleAllForDeletionFlag;
	}
	
	// Called when checking the "select all" checkbox unmarking all users as ready for deletion
	//public void unmarkAllUsersForDeletion() {
	//	usersForDeletion.clear();
	//}
	
	// Delete a single user from the database using his/her ID
	private void deleteUser(long userId) {
		System.out.println("Deleting user with ID " + userId);
		EntityManager em = factory.createEntityManager();
		EntityManager emImg = factory.createEntityManager();
		User u = UserController.findUserbyId(userId);
		
		// First we try to delete the files that are associated with the user and then if
		// the deletion was successful, we delete the user himself
		
		// Delete images uploaded by the user
		try {
			EntityTransaction tx = emImg.getTransaction();
			tx.begin();
			
			// Delete images uploaded by the selected user
			File imageFile;
			for (Image image : u.getImages()) {
				System.out.println("Deleting image \"" + image.getPath() + "\"");
				
				// Delete the image files
				imageFile = new File(image.getPath());
				if(!imageFile.delete()) {
					System.out.println("Failed to delete the images uploaded by the selected user");
					return;
				}
				
				Image imageEntity = emImg.getReference(Image.class, image.getId());
				emImg.remove(imageEntity);
			}	
			tx.commit();
		}
		catch(Exception ex) {
			// See what kind of handling is appropriate here
			System.out.println(ex.getMessage());
		}
		
		// Delete user's empty folder
		try {
			// Get folder
			File userDir = new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/" + u.getUserName() + "/");
			userDir.delete();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		// Check whether the user has been added to the contacts of other users and delete his entry if true
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			  /*List<User> relatedUsers = (List<User>)em.createQuery(
					"SELECT u FROM User u WHERE u.id != :id")
					  .setParameter("id", u.getId())
					  .getResultList();
			  for (User relatedUser : relatedUsers) {
				relatedUser.getContacts().remove(u.getId());
			  }*/
			  // Delete all entries of other users that have the selected user in their contacts
			  Query qDeleteAddedBy = em.createNativeQuery(
					  "DELETE FROM my_user_my_user WHERE contacts_ID = :id").setParameter("id", u.getId());
  			  // Delete all entries of selected user that contain his contacts
			  Query qOwn = em.createNativeQuery( //em.createQuery(
					  "DELETE FROM my_user_my_user WHERE User_ID = :id").setParameter("id", u.getId());
					  
			tx.commit();
		}
		catch(Exception ex) {
			System.out.println(ex. getMessage());
		}
		
		// Delete user
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			
			  User userEntity = em.getReference(User.class, u.getId());
			  em.remove(userEntity);
			
			/*Query q = em.createQuery(
					"DELETE FROM User u WHERE u.id = :id")
					.setParameter("id", userId);
			if(q.executeUpdate() < 0) throw new Exception("Shitty documention on executeUpdate() doesn't give any information on the return codes...Nice!");
			*/
			
			tx.commit();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		// Remove from list of marked for deletion
		usersForDeletion.remove((Long)userId);
		
		// Remove user from all other users who have him
		// Query all users and see who has our user marked for deletion in their contacts
		// ...
		
		
		em.close();
	}
	
	// Delete all users from the database marked for deletion
	public void deleteUsers() {
		System.out.println("Deleting all users");
		if(usersForDeletion.size() == 0) return;
		for(Long userId : usersForDeletion) deleteUser(userId);
	}
}