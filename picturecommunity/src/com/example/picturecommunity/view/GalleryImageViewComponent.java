package com.example.picturecommunity.view;

import com.vaadin.event.ShortcutListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.UI;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.io.File;

import com.example.picturecommunity.controller.GalleryController;
import com.example.picturecommunity.controller.GalleryImageController;
import com.example.picturecommunity.controller.PicturecommunityMainController;
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
	VerticalLayout layout;
	VerticalLayout uploadStatsAndViewStatus;
	HorizontalLayout uploaderAndStatus;

	public GalleryImageViewComponent(Image img) {
		
		// Vaadin doesn't allow adding null as a component (for good reason) so we have
		// to create a representation of an empty cell
		if(img == null) return;
		
		layout = new VerticalLayout();
		// Add the metadata of the image (comment, name, uploader)
		controller = new GalleryImageController(img);
		// Load the image file and create a Vaadin Image
		FileResource resource = new FileResource(new File(controller.getImage().getPathThumbnail()));
		com.vaadin.ui.Image embeddedImage = new com.vaadin.ui.Image(
				controller.getImage().getName() + "\t(" + (int)controller.getImage().getWidth() + "x" + (int)controller.getImage().getHeight() + ")",
				resource);
		embeddedImage.addClickListener(new ClickListener() {
			
			@Override
			public void click(ClickEvent event) {
				Window imageZoomedView = new Window();
				imageZoomedView.center();
				VerticalLayout content = new VerticalLayout();
				com.vaadin.ui.Image fullView = new com.vaadin.ui.Image(
						"",
						new FileResource(new File(controller.getImage().getPath()))
						);
				content.addComponent(fullView);
				content.setComponentAlignment(fullView, Alignment.MIDDLE_CENTER);
				content.setMargin(true);
				// Note: -1 for size means "adapt to the content you store" so here both the layout and the window are adapted to the size of the image they show
				content.setWidth(-1, Unit.PIXELS); //controller.getImage().getWidth(), Unit.PIXELS
				content.setHeight(-1, Unit.PIXELS); //controller.getImage().getHeight() + controller.getImage().getHeight()/5, Unit.PIXELS
				
				imageZoomedView.setContent(content);
				imageZoomedView.setCaption(controller.getImage().getName());
				imageZoomedView.setWindowMode(WindowMode.NORMAL);
				imageZoomedView.setWidth(-1, Unit.PIXELS);
				imageZoomedView.setHeight(-1, Unit.PIXELS);
				imageZoomedView.center();
				imageZoomedView.addClickListener(new ClickListener() {
					
					@Override
					public void click(ClickEvent event) {
						imageZoomedView.close();	// Does not apply to the window's border obviously :)
					}
				});
				imageZoomedView.setModal(true); // Prevent interaction with other UI elements while window is on the screen
				imageZoomedView.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE);

				UI.getCurrent().addWindow(imageZoomedView);
				Notification.show(
						"Click on the image, the X in\n" +
						"the top right corner or press\n"+
						"to exit the zoomed view. Move\n"+
						"the mouse cursor to hide this notificaion");
				imageZoomedView.focus();
			}
		});

		// Create additional components and add all to the layout
		layout.addComponent(embeddedImage);
		layout.setComponentAlignment(embeddedImage, Alignment.TOP_CENTER);
		Label comment = new Label(controller.getImage().getComment());
		comment.setStyleName("textStyles");	// TODO Remove this once the the GalleryImageViewComponent are spread out properly inside the GalleryViewComponent
		layout.addComponent(comment);
		layout.setComponentAlignment(comment, Alignment.MIDDLE_CENTER);
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
		
		uploadStatsAndViewStatus = new VerticalLayout();
		uploaderAndStatus = new HorizontalLayout();
		Label uploader = new Label("Uploader: " + controller.getImage().getUploader().getUserName());
		uploaderAndStatus.addComponent(uploader);
		uploaderAndStatus.setComponentAlignment(uploader, Alignment.MIDDLE_LEFT);
		uploaderAndStatus.addComponent(viewStatus);
		uploaderAndStatus.setComponentAlignment(viewStatus, Alignment.MIDDLE_RIGHT);
		uploadStatsAndViewStatus.addComponent(uploaderAndStatus);
		uploadStatsAndViewStatus.addComponent(new Label("Upload date: " + img.getUploadTimeAsString()));
		this.addStyleName("image-component");
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
