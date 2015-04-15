package com.example.picturecommunity.model;

import java.io.Serializable;

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
	public Long id;

	public String username;
	public String password;
	

	public User(){
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
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

}
