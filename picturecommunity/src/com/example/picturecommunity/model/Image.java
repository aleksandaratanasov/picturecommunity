package com.example.picturecommunity.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

@Entity
@Table(name = "my_image")
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9120607274421816301L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String path;
	private String name;
	private String comment;
	private Instant uploadTime;
	private boolean viewStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn
	private User uploader;
	
	public Image() {
		
	}
	
	public Image(String path, String name, boolean viewStatus) {
		this(path, name, viewStatus, "");
	}
	
	public Image(String path, String name, boolean viewStatus, String comment) {
		this.path = path;
		this.name = name;
		this.viewStatus = viewStatus;
		this.comment = comment;
		uploadTime = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getViewStatus() {
		return viewStatus;
	}
	
	public void setViewStatus(boolean viewStatus) {
		this.viewStatus = viewStatus;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUploadTimeAsString() {
		return uploadTime.toString();
	}

	public User getUploader() {
		return uploader;
	}

	public void setUploader(User uploader) {
		this.uploader = uploader;
		if(!uploader.getImages().contains(this)){
			uploader.getImages().add(this);
			uploader.setUploads(uploader.getImages().size());
		}
	}
	
	

}
