package com.example.picturecommunity.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.example.picturecommunity.model.User;
import com.vaadin.server.VaadinSession;

public class UserController {
	
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;

	public static User findUserbyName(String username){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :user");
		q.setParameter("user", username);
		try{	
			User user =(User) q.getSingleResult();
			return user;
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally {
			em.close();
		}
		return null;
	}
	
	public static User findUserbyId(long id){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		try{
			User user = em.find(User.class, id);
			return user;
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally {
			em.close();
		}
		
		return null;
	}
	
	public static void updateUploads(User user){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		try{
			EntityTransaction entr = em.getTransaction();
			entr.begin();
				user.setUploads(user.getImages().size());
				em.merge(user);
			entr.commit();
		}finally{
			em.close();
		}
	}
	public static void updateCurrentUserStatus(String status) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		String username = (String)VaadinSession.getCurrent().getAttribute("username");
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :user");
		q.setParameter("user", username);
		try{	
			User user =(User) q.getSingleResult();
			
			EntityTransaction entr =em.getTransaction();
			entr.begin();
			user.setStatus(status);
			entr.commit();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally {
			em.close();
		}
	}
}
