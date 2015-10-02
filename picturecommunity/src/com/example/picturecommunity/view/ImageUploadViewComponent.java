package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.ImageController;
import com.sun.corba.se.impl.util.PackagePrefixChecker;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class ImageUploadViewComponent extends CustomComponent {

	private static final long serialVersionUID = 1L;
	ImageController receiver = new ImageController();
	
	public ImageUploadViewComponent(){
		// FIXME Alignment looks like shit
		VerticalLayout layout = new VerticalLayout();
		
		//Creating Upload
		Upload imageUpload = new Upload("Upload your Image here", receiver);
		imageUpload.setButtonCaption("Start Upload");
		imageUpload.addSucceededListener(receiver);

		//Creating Comment Field
		TextField comment = new TextField("Comment");
		//comment.addValueChangeListener(receiver);
		comment.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				String comment = (String)event.getProperty().getValue();
				receiver.setComment(comment);
				
			}
		});
		TextField name = new TextField("Name");
		name.addValueChangeListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				String name = (String)event.getProperty().getValue();
				receiver.setName(name);
				
			}
		});
		//name.addValueChangeListener(receiver);

		//Adding Checkbox for Public or Private Picture
		CheckBox viewstatus = new CheckBox("public Picture");
		viewstatus.addValueChangeListener(receiver);

		//Comment and name text fields are aligned horizontally
		// TODO Sadly the Upload component takes too much space horizontally. If the ImageUpload component is to be vertical we need a custom Upload component
		HorizontalLayout uploadTextSettings = new HorizontalLayout();
		uploadTextSettings.addComponent(name);
		uploadTextSettings.addComponent(comment);
		VerticalLayout uploadSettings = new VerticalLayout();
		uploadSettings.addComponent(uploadTextSettings);
		uploadSettings.addComponent(viewstatus);
		//uploadData.setComponentAlignment(viewstatus, Alignment.BOTTOM_RIGHT);
		uploadSettings.setSpacing(true);

		layout.addComponent(imageUpload);
		//layout.setComponentAlignment(imageUpload, Alignment.BOTTOM_LEFT );
		layout.addComponent(uploadSettings);
		//layout.setComponentAlignment(uploadData, Alignment.BOTTOM_LEFT);

		setCompositionRoot(layout);
	}
}
