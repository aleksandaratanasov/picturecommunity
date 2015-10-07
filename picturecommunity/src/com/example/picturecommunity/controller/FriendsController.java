package com.example.picturecommunity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;

public class FriendsController {

	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	
	private String 	username = (String)	VaadinSession.getCurrent().getAttribute("username");
	private User currentUser = UserController.findUserbyName(username);
	public FriendsController() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean addFriend(String friendName) {
		EntityManager em  = factory.createEntityManager();
		String currentUsername = (String) VaadinSession.getCurrent().getAttribute("username");
		List<User> allUsers = (List<User>)em.createQuery(
				"SELECT u FROM User u ORDER BY u.username ASC")
			    .getResultList();
		
		User currentUser = null;
		User friend = null;
		for(User user: allUsers) {
			
			if(user.getUserName().equals(currentUsername)) {
				currentUser = user;
			}
			else if(user.getUserName().equals(friendName)) {
				friend = user;
			}
		}
		if(currentUser != null && friend != null) {
		
			LinkedList<User> contacts = currentUser.getContacts();
			if(!contacts.contains(friend)) {
				try {
					EntityTransaction entr = em.getTransaction();
					entr.begin();
					currentUser.addContact(friend);
//				Query query = em.createQuery(
//						"UPDATE User u SET u.contacts = :contacts WHERE u.username = :username");
//				query.setParameter("contacts", currentUser.getContacts());
//				query.setParameter("username", currentUsername);
//				query.executeUpdate();
				entr.commit();
				return true;
				}
				finally {
					em.close();
				}
			}
		}
		return false;
	}
	
	public Map<String, String> getFriendNamesAndStatus() {
		
		Map<String, String> friendNames = new HashMap<String,String>();
		
		String 	username = (String)	VaadinSession.getCurrent().getAttribute("username");
		currentUser = UserController.findUserbyName(username);
		try {
			
			LinkedList<User> friends = currentUser.getContacts();
			for(User friend:friends) {
				friendNames.put(friend.getUserName(), friend.getStatus());
			}
		}
		catch(NoResultException ex) {
			System.out.println(ex.getMessage());
		}
		return friendNames;
	}
	
	public List<User> getSearchedUsers(String keyword) {
		
		List<User> result = new ArrayList<User>();
		if(keyword == null || keyword.isEmpty()) {
			return result;
		}
		EntityManager em = factory.createEntityManager();
		List<User> users;
		
		try {						
			users = (List<User>)em.createQuery("SELECT u FROM User u ORDER BY u.username ASC")
							.getResultList();
			for(User user : users) {
				
				if(!user.getUserName().contentEquals(username) && user.getUserName().contains(keyword)) {
					result.add(user);
				}
			}
		}catch(Exception ex) {
			Notification.show(ex.getMessage());
		}
		finally {
			em.close();
		}
		return result;
	}
	public User getCurrentUser() {
		return currentUser;
	}
	
	public static List<String> getFriendNames(String user) {
		
		List<String> friendNames = new ArrayList<String>();
		//String currentUsername = (String)VaadinSession.getCurrent().getAttribute("username");
		
		EntityManager em = factory.createEntityManager();
		Query q = em
				.createQuery("SELECT u FROM User u WHERE u.username = :login");
		q.setParameter("login", user);
		
		try {
			User u = (User)q.getSingleResult();
			LinkedList<User> friends = u.getContacts();
			for(User friend:friends) {
				friendNames.add(friend.getUserName());
			}
		}
		catch(NoResultException ex) {
			System.out.println(ex.getMessage());
		}
		return friendNames;
	}

	
	
	// TODO Add friends retrieval as a list of ids (more efficient then using strings)
	/*public List<Long> getFriendIds() {
		return null;
	}*/
}
