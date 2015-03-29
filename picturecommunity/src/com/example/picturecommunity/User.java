package com.example.picturecommunity;


import javax.persistence.*;

@Entity
@Table(name="my_user")
public class User{


	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;
	
	public Long getId(){
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
