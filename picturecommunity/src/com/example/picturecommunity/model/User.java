package com.example.picturecommunity.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "my_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6583325101179874986L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;
	private String password;
	private LinkedList<Long> contacts;
	private long uploads;
	private LinkedList<Long> imageIds;	// stores only the ID of the image object, which can then be retrieved from the "my_image" table

	public User(){
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		contacts = new LinkedList<Long>();
		imageIds = new LinkedList<Long>();
		uploads = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public LinkedList<Long> getContacts() {
		return contacts;
	}
	
	public void addContact(long contactId) {
		contacts.add(contactId);
	}
	
	public long getUploads() {
		return uploads;
	}

	public void addImage(long imageId) {
		imageIds.add(imageId);
		uploads++;
	}
	
	public LinkedList<Long> getImages() {
		return imageIds;
	}
}
