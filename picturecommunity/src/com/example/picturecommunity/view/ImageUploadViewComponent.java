package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.ImageController;
import com.sun.corba.se.impl.util.PackagePrefixChecker;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class ImageUploadViewComponent extends CustomComponent {

	private static final long serialVersionUID = 1L;
	ImageController receiver = new ImageController();
	
	public ImageUploadViewComponent(){
		// FIXME Alignment looks like shit
		HorizontalLayout layout = new HorizontalLayout();
		
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

		// Place comment, name and view status in a single layout
		HorizontalLayout uploadSettings = new HorizontalLayout();
		uploadSettings.addComponent(new Label(" ")); // Creates some blank space between the components
		uploadSettings.addComponent(name);
		uploadSettings.addComponent(new Label(" ")); // Creates some blank space between the components
		uploadSettings.addComponent(comment);
		uploadSettings.addComponent(new Label(" ")); // Creates some blank space between the components
		uploadSettings.addComponent(viewstatus);
		//uploadData.setComponentAlignment(viewstatus, Alignment.BOTTOM_RIGHT);
		uploadSettings.setSpacing(true);

		layout.addComponent(imageUpload);
		layout.addComponent(new Label(" ")); // Creates some blank space between the components
		layout.addComponent(uploadSettings);

		setCompositionRoot(layout);
	}
}
