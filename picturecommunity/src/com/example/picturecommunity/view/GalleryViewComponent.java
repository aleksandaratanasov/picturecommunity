package com.example.picturecommunity.view;

import com.vaadin.ui.Panel;

import java.util.LinkedList;

import com.example.picturecommunity.controller.GalleryController;
import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.view.GalleryImageViewComponent;

// A container for all GalleryImageViewComponents
// Parent: PersonalDashboardView
public class GalleryViewComponent extends Panel{

	LinkedList<GalleryImageViewComponent> images;
	
	public GalleryViewComponent() {
		images = new LinkedList<GalleryImageViewComponent>();
	}
}
