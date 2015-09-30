package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.ImageController;
import com.sun.corba.se.impl.util.PackagePrefixChecker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class ImageUploadViewComponent extends CustomComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageController receiver = new ImageController();
	
	public ImageUploadViewComponent(){
		VerticalLayout layout = new VerticalLayout();
		

		
		
		//Creating Upload
		Upload imageUpload = new Upload("Upload your Image here", receiver);
		imageUpload.setButtonCaption("Start Upload");
		imageUpload.addSucceededListener(receiver);

		
		//Creating Comment Field
		TextField comment = new TextField("Comment your Picture");
		comment.addValueChangeListener(receiver);

		//Adding Checkbox for Public or Private Picture
		CheckBox viewstatus = new CheckBox("public Picture");
		viewstatus.addValueChangeListener(receiver);

		
		
		//Adding Panel for Checkbox and Comment Field
		HorizontalLayout uploadData = new HorizontalLayout();
		uploadData.addComponent(comment);
		uploadData.addComponent(viewstatus);
		uploadData.setComponentAlignment(viewstatus, Alignment.BOTTOM_RIGHT);
		uploadData.setSpacing(true);
		

		layout.addComponent(imageUpload);
		layout.setComponentAlignment(imageUpload, Alignment.BOTTOM_LEFT );
		layout.addComponent(uploadData);
		layout.setComponentAlignment(uploadData, Alignment.BOTTOM_LEFT);


		setCompositionRoot(layout);
	}
	



}
