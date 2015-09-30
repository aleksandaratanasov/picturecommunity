package com.example.picturecommunity.model;

import java.io.Serializable;
import java.util.LinkedList;

import javax.persistence.*;

import org.eclipse.persistence.annotations.ChangeTracking;

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
	private LinkedList<User> contacts = new LinkedList<User>();
	private long uploads;
	
	@OneToMany(mappedBy="uploader")
	private LinkedList<Image> imageIds;	// stores only the ID of the image object, which can then be retrieved from the "my_image" table

	public User(){
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		
		//imageIds = new LinkedList<Long>();
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
	
	public LinkedList<User> getContacts() {
		if(contacts == null) {
			contacts = new LinkedList<User>();
		}
		return contacts;
	}
	
	public void addContact(User user) {
		contacts.add(user);
	}
	
	public long getUploads() {
		return uploads;
	}
	
	public void setUploads(long uploads){
		this.uploads = uploads;
	}

	public void addImage(Image image) {
		this.imageIds.add(image);
		if(image.getUploader() != this){
			image.setUploader(this);
		}
		uploads = imageIds.size();
	}
	
	public LinkedList<Image> getImages() {
		return imageIds;
	}
}
