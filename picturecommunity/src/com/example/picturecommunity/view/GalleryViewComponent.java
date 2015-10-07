package com.example.picturecommunity.view;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.example.picturecommunity.controller.GalleryController;
import com.example.picturecommunity.view.GalleryImageViewComponent;

// A container for all GalleryImageViewComponents
// Parent: PersonalDashboardView
public class GalleryViewComponent extends Panel {

	//private Table gallery;
	private VerticalLayout gallery;
	private GalleryController controller;
	
	// A pseudo-grid using a combination of horizontal layouts and a single vertical layout (sadly the Vaadin Grid doesn't support other then ThemeSource and ExternalSource for image data)
	public GalleryViewComponent(boolean isPersonal) {
		//VerticalLayout layout = new VerticalLayout();
		//images = new LinkedList<GalleryImageViewComponent>();
		
		controller = new GalleryController(isPersonal);
		// Long live short-circuit evaluation
		
		
		// Based on the vertical dimension of the grid add a number of horizontal layouts
		// Each horizontal layout represent a single row, which will contain a predefined number of images
		//LinkedList<HorizontalLayout> rows = new LinkedList<>();
		//for(int row = 0; row < controller.getGridCellsVertical(); row++) rows.add(new HorizontalLayout());
		
		gallery = new VerticalLayout();
		LinkedList<GalleryImageViewComponent> images = controller.getImages(isPersonal);
		if(images.size() == 0) return;
		Iterator<GalleryImageViewComponent> it = images.iterator();
		// For each row (horizontal layout)
		for(int row = 0; row < controller.getGridCellsVertical(); row++) {
			// Add x number of components (represent the columns)
			HorizontalLayout rowLayout = new HorizontalLayout();
			for(int col = 0; col < controller.getGridCellsHorizontal(); col++) {
				rowLayout.addComponent((it.hasNext()) ? it.next() : new GalleryImageViewComponent(null, isPersonal));
				//rows.get(row).addComponent((it.hasNext()) ? it.next() : null);
			}
			gallery.addComponent(rowLayout);
			//gallery.addComponent(rows.get(row));
		}

		setContent(gallery);
	}
	
	// For All Users Dashboard
//	public GalleryViewComponent(List<String> users) {
//		//VerticalLayout layout = new VerticalLayout();
//		//images = new LinkedList<GalleryImageViewComponent>();
//		controller = new GalleryController(users);
//		// Long live short-circuit evaluation
//		try {
//			if(controller.getCurrentUser().getImages().size() == 0) return;
//		}
//		catch(NullPointerException e) {
//			return;
//		}
//		
//		// Based on the vertical dimension of the grid add a number of horizontal layouts
//		// Each horizontal layout represent a single row, which will contain a predefined number of images
//		//LinkedList<HorizontalLayout> rows = new LinkedList<>();
//		//for(int row = 0; row < controller.getGridCellsVertical(); row++) rows.add(new HorizontalLayout());
//		
//		gallery = new VerticalLayout();
//		Iterator<GalleryImageViewComponent> it = controller.getImages().iterator();
//		// For each row (horizontal layout)
//		for(int row = 0; row < controller.getGridCellsVertical(); row++) {
//			// Add x number of components (represent the columns)
//			HorizontalLayout rowLayout = new HorizontalLayout();
//			for(int col = 0; col < controller.getGridCellsHorizontal(); col++) {
//				rowLayout.addComponent((it.hasNext()) ? it.next() : new GalleryImageViewComponent(null, true));
//				//rows.get(row).addComponent((it.hasNext()) ? it.next() : null);
//			}
//			gallery.addComponent(rowLayout);
//			//gallery.addComponent(rows.get(row));
//		}
//
//		setContent(gallery);
//	}
}
