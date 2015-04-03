package com.example.picturecommunity;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.example.picturecommunity.AboutView;
import com.example.picturecommunity.AdminView;
import com.example.picturecommunity.LoginView;
import com.example.picturecommunity.PersonalDashboardView;
import com.example.picturecommunity.allUsersDashboardView;

@SuppressWarnings("serial")
@Theme("picturecommunity")
public class PicturecommunityUI extends UI {

	public Navigator navigator;
	
	// Views
	public static final String ADMINVIEW = "admin view";
	public static final String ALLUSERSDASHBOARDVIEW = "all users dashboard";
	public static final String PERSONALDASHBOARDVIEW = "personal dashboard";
	public static final String ABOUTVIEW = "about";

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PicturecommunityUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		navigator.addView("", new LoginView());
		navigator.addView(ALLUSERSDASHBOARDVIEW, new allUsersDashboardView());
		navigator.addView(PERSONALDASHBOARDVIEW, new PersonalDashboardView());
		navigator.addView(ADMINVIEW, new AdminView());
		navigator.addView(ABOUTVIEW, new AboutView());
	}
}