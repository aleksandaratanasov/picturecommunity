package com.example.picturecommunity.view;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.VerticalLayout;

import org.jfree.data.contour.ContourDataset;

import com.example.picturecommunity.controller.GalleryController;
import com.example.picturecommunity.model.Image;
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
		if(controller.getCurrentUser().getImages().size() == 0) return;
		//images = new IndexedContainer(controller.getImages());
		//gallery = new Grid(images);
		//gallery.addColumn(images, GalleryImageViewComponent.class);
		gallery = new Table("Gallery");
		gallery.setImmediate(true);
		//gallery.setSizeFull();
		//gallery.setHeight("100%");
		//gallery.setSizeUndefined();
		gallery.setPageLength(controller.getCurrentUser().getImages().size());
		gallery.setRowHeaderMode(RowHeaderMode.HIDDEN);
		gallery.addContainerProperty("Image", GalleryImageViewComponent.class, null);
		
		for (GalleryImageViewComponent item : controller.getImages()) {
			Object itemId = gallery.addItem();
			System.out.println("Adding image \"" + item.getImage().getPath() + "\"");
			gallery.getItem(itemId).getItemProperty("Image").setValue(item);
		}

		layout.addComponent(gallery);
		setContent(layout);
	}
}
