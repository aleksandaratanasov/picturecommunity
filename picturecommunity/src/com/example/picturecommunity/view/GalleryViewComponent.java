package com.example.picturecommunity.view;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.example.picturecommunity.controller.GalleryController;
import com.example.picturecommunity.model.User;
import com.example.picturecommunity.view.GalleryImageViewComponent;

// A container for all GalleryImageViewComponents
// Parent: PersonalDashboardView
public class GalleryViewComponent extends Panel {

	//private LinkedList<GalleryImageViewComponent> images;
	//private Grid gallery;
	private Table gallery;
	//private IndexedContainer images;
	private GalleryController controller;
	
	public GalleryViewComponent(String user) {
		VerticalLayout layout = new VerticalLayout();
		//images = new LinkedList<GalleryImageViewComponent>();
		controller = new GalleryController(user);
		//images = new IndexedContainer(controller.getImages());
		//gallery = new Grid(images);
		//gallery.addColumn(images, GalleryImageViewComponent.class);
		gallery = new Table("Gallery");
		gallery.setImmediate(true);
		gallery.setSizeFull();
		gallery.addContainerProperty("Image", GalleryImageViewComponent.class, null);
		
		for (GalleryImageViewComponent item : controller.getImages()) {
			gallery.addItem(item);
		}
		
		layout.addComponent(gallery);
	}
}
