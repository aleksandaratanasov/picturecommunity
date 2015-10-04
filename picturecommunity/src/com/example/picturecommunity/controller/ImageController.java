package com.example.picturecommunity.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.io.FilenameUtils;

import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.model.User;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import net.coobird.thumbnailator.Thumbnails;

import com.vaadin.server.VaadinService;

/**
 * Provides tools for managing images
 */
public class ImageController implements Receiver, SucceededListener, ValueChangeListener {
	
	private final int THUMBNAIL_WIDTH = 128;
	private final int THUMBNAIL_HEIGHT = 128;

	private static final long serialVersionUID = 1L;
	private static final String PERSISTENCE_UNIT_NAME = "picturecommunity";
	private static EntityManagerFactory factory;
	
	
	public File file;
	private String username;
	private User user;
	private boolean fileExists;
	private String comment;
	private String name;
	private boolean pictureViewStatus;
	
	public ImageController() {
		this.username = (String) VaadinSession.getCurrent().getAttribute("username");
		System.out.println("BASE DIRECTORY: " + VaadinService.getCurrent().getBaseDirectory().getAbsolutePath());
		//boolean success = (new File("../tmp/uploads/" + username + "/")).mkdirs();
		boolean success = (new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/" + username + "/")).mkdirs();
		if(!success) new Notification("Error Creating Directory");
		fileExists 			= false;
		pictureViewStatus 	= false;
		
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		new Notification("Upload successfull").show(Page.getCurrent());


		if(!fileExists){
			user = UserController.findUserbyName(username);
			createImage(file, comment, name, pictureViewStatus, user);
			UserController.updateUploads(user);
		}else {
			fileExists = false;
		}
		
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		String filepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/" + username + "/" + filename;
		
		//Workaround for file duplicates (User bekommt im Moment nix davon mit)
		if(Files.exists(Paths.get(filepath), LinkOption.NOFOLLOW_LINKS)) fileExists = true;
		
		// Create upload stream
	   FileOutputStream fos = null; // Stream to write to
	   try {
	       // Open the file for writing.
	       file = new File(filepath);
	       fos = new FileOutputStream(file);
	   } catch (final java.io.FileNotFoundException e) {
	       new Notification("Could not open file",
	                        e.getMessage(),
	                        Notification.Type.ERROR_MESSAGE)
	           .show(Page.getCurrent());
	       return null;
	   }
	   
       return fos; // Return the output stream to write to
	}
	
	public void createImage(File file, String comment, String name, boolean viewstatus, User user ){
		if(file.exists()){
			// Create a temporary BufferedImage image to retrieve some data (such as dimensions) from the image file
			// Vaadin Image is a no go here due to the inability to extract dimension from a component on the server side
			final BufferedImage temp_i;
			try {
				temp_i = ImageIO.read(new File(file.getPath()));
			}
			catch(Exception e) {
				System.out.println("Failed to read image file");
				return;
			}
			// Create image model object
			Image i = new Image(
					user,
					file.getPath(),
					"",
					temp_i.getWidth(), temp_i.getHeight(),
					(name != "") ? name : file.getName(),
					viewstatus,
					comment);
			
		    // Create thumbnail
			try {
				// TODO Add adaptive resizing here (first need to change the Image model to actually be able to get the dimensions of the image)
				int maxWidth = THUMBNAIL_WIDTH;
				int maxHeight = THUMBNAIL_HEIGHT;
				//double ratio = maxWidth / i.getWidth();
				Thumbnails.of(file)
				.size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
				.toFile(file.getPath()
						.replace(
								new String("." + FilenameUtils.getExtension(file.getPath())),
								new String("_thumbnail." + FilenameUtils.getExtension(file.getPath()))
								)
						);
			}
			catch(IOException ex) {
				System.out.println("Failed to create thumbnail");
				return;
			}
			
			i.setPathThumbnail(file.getPath()
					.replace(
							new String("." + FilenameUtils.getExtension(file.getPath())),
							new String("_thumbnail." + FilenameUtils.getExtension(file.getPath()))
							)
					);
			
			//System.out.println("ORIGINAL IMAGE: " + i.getPath());
			//System.out.println("THUMBNAIL IMAGE: " + i.getPathThumbnail());
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			try{
				EntityTransaction entr = em.getTransaction();
				entr.begin();
					//Image i = new Image();
					//Image i = new Image(user, file.getPath(), "", (name != "") ? name : file.getName(), viewstatus, comment);
				    
					/*i.setName((name != "") ? name : file.getName());
					i.setPath(file.getPath());
					i.setUploader(user);
					i.setViewStatus(viewstatus);
					i.setComment(comment);*/
					em.persist(i);
				entr.commit();
				
			} finally {
				em.close();
			}
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// FIXME Add support  for adding name of the uploaded picture
		if(event.getProperty().getClass().equals(TextField.class)) {
			//comment = (String)event.getProperty().getValue();
			//name = (String)event.getProperty().getValue();
		}
		else if (event.getProperty().getClass().equals(CheckBox.class)) pictureViewStatus = (Boolean) event.getProperty().getValue();
	}
}
