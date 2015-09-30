package com.example.picturecommunity.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;
import com.vaadin.server.VaadinSession;

public class FriendsController {

	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	
	
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
	
	public List<String> getFriendNames() {
		
		List<String> friendNames = new ArrayList<String>();
		String currentUsername = (String) VaadinSession.getCurrent().getAttribute("username");
		
		EntityManager em = factory.createEntityManager();
		Query q = em
				.createQuery("SELECT u FROM User u WHERE u.username = :login");
		q.setParameter("login", currentUsername);
		
		User user = (User) q.getSingleResult();
		LinkedList<User> friends = user.getContacts();
		for(User friend:friends) {
			friendNames.add(friend.getUserName());
		}
		return friendNames;
	}
	
	// TODO Add friends retrieval as a list of ids (more efficient then using strings)
	/*public List<Long> getFriendIds() {
		return null;
	}*/
}
