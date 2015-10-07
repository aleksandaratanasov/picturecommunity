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
	private String status;

	// contacts stores all users that the current user has added to his friends list allowing them to view his gallery
	
	// TODO use getAllUsers()
	private LinkedList<User> contacts = new LinkedList<User>();
	// addedBy stores all users that have the current user in their contacts list
	// The main purpose of this is to make the deletion process of an user easier
	// Normally we will have to traverse each user's contacts list looking for the current user's ID upon deletion
	// By using addedBy we create a bidirectional relation between the users - we still traverse all users in the
	// DB but we access the contacts of only those, who are stored in this list
	//private LinkedList<User> addedBy = new LinkedList<User>();
	private long uploads;
	
	@OneToMany(mappedBy="uploader")
	// TODO imageIds should be a set
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
		if(contacts == null) contacts = new LinkedList<User>();
		return contacts;
	}
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	/*public LinkedList<User> getAddedBy() {
		if(addedBy == null) addedBy = new LinkedList<User>();
		return addedBy;
	}*/
	
	public void addContact(User user) {
		contacts.add(user);	// Add the selected user as a contact of the current user
		//user.addedBy.add(this); // Add the current user to the "added by" list of the selected user
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
