package com.example.picturecommunity.model;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-09-30T19:06:13.875+0200")
@StaticMetamodel(Image.class)
public class Image_ {
	public static volatile SingularAttribute<Image, Long> id;
	public static volatile SingularAttribute<Image, String> path;
	public static volatile SingularAttribute<Image, String> name;
	public static volatile SingularAttribute<Image, String> comment;
	public static volatile SingularAttribute<Image, Instant> uploadTime;
	public static volatile SingularAttribute<Image, Boolean> viewStatus;
	public static volatile SingularAttribute<Image, User> uploader;
}