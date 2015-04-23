package com.example.picturecommunity.controller;

import javax.servlet.annotation.WebServlet;

import com.example.picturecommunity.model.User;
import com.example.picturecommunity.view.AboutView;
import com.example.picturecommunity.view.AdminView;
import com.example.picturecommunity.view.LoginView;
import com.example.picturecommunity.view.PersonalDashboardView;
import com.example.picturecommunity.view.RegisterView;
import com.example.picturecommunity.view.allUsersDashboardView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("picturecommunity")
public class PicturecommunityUI extends UI {

	public Navigator navigator;
	User user;

	// Views
	public static final String ADMINVIEW = "admin view";
	public static final String ALLUSERSDASHBOARDVIEW = "all users dashboard";
	public static final String PERSONALDASHBOARDVIEW = "personal dashboard";
	public static final String ABOUTVIEW = "about";
	public static final String REGISTERVIEW = "create account";

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
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(
				layout);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		user = new User();
		navigator.addView("", new LoginView(this));
		navigator.addView(ALLUSERSDASHBOARDVIEW, new allUsersDashboardView());
		navigator.addView(PERSONALDASHBOARDVIEW,
				new PersonalDashboardView(this));
		navigator.addView(ADMINVIEW, new AdminView());
		navigator.addView(ABOUTVIEW, new AboutView());
		navigator.addView(REGISTERVIEW, new RegisterView());

	}

	public User getUser() {
		return user;
	}
}