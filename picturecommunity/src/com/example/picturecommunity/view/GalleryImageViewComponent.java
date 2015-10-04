package com.example.picturecommunity.view;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import java.io.File;

import com.example.picturecommunity.controller.GalleryController;
import com.example.picturecommunity.controller.GalleryImageController;
import com.example.picturecommunity.model.Image;

// A container for a single image allowing user interaction with the underlying
// image collection of the specific user
// Parent: GalleryViewCOmponent
public class GalleryImageViewComponent extends CustomComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GalleryImageController controller;

	public GalleryImageViewComponent(Image img) {
		
		// Vaadin doesn't allow adding null as a component (for good reason) so we have
		// to create a representation of an empty cell
		if(img == null) return;
		
		// Add the metadata of the image (comment, name, uploader)
		controller = new GalleryImageController(img);
		// Load the image file and create a Vaadin Image
		FileResource resource = new FileResource(new File(controller.getImage().getPathThumbnail()));
		com.vaadin.ui.Image embeddedImage = new com.vaadin.ui.Image(
				controller.getImage().getName() + "\t(" + (int)controller.getImage().getWidth() + "x" + (int)controller.getImage().getHeight() + ")",
				resource);

		// Create additional components and add all to the layout
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(embeddedImage);
		layout.addComponent(new Label(controller.getImage().getComment()));
		HorizontalLayout uploadStatsAndViewStatus = new HorizontalLayout();
		//CheckBox viewStatus = new CheckBox("Private");
		CheckBox viewStatus = new CheckBox("Public", controller.getImage().getViewStatus());
		viewStatus.addBlurListener(new BlurListener() {

			private static final long serialVersionUID = 1814712743184783174L;

			@Override
			public void blur(BlurEvent event) {
				if(viewStatus.getValue()) {
					Notification.show("Image is private");
				}
				else {
					Notification.show("Image is public");
				}
				
				controller.changeViewStatus(viewStatus.getValue());
			}
		});
		Label uploader = new Label("Uploader: " + controller.getImage().getUploader().getUserName());
		uploadStatsAndViewStatus.addComponent(uploader);
		uploadStatsAndViewStatus.setComponentAlignment(uploader, Alignment.BOTTOM_LEFT);
		uploadStatsAndViewStatus.addComponent(new Label(" "));
		uploadStatsAndViewStatus.addComponent(viewStatus);
		uploadStatsAndViewStatus.setComponentAlignment(viewStatus, Alignment.BOTTOM_RIGHT);
		//uploadStatsAndViewStatus.addComponent(new Label(img.getUploadTimeAsString()));
		//System.out.println("UPLOAD TIME STRING: " + img.getUploadTimeAsString());
		layout.addComponent(uploadStatsAndViewStatus);
		layout.setSizeUndefined();
		layout.setSpacing(true);
		setSizeUndefined();
		setCompositionRoot(layout);
	}
	
	public Image getImage() {
		return controller.getImage();
	}
}
