package com.example.picturecommunity.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-09-30T19:06:13.879+0200")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, User> contacts;
	public static volatile SingularAttribute<User, Long> uploads;
	public static volatile ListAttribute<User, Image> imageIds;
}