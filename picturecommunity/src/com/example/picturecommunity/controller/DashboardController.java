package com.example.picturecommunity.controller;

import com.example.picturecommunity.model.User;
import com.vaadin.server.VaadinSession;

public class DashboardController {
	private User current_user;
	
	public DashboardController() {
		current_user = UserController.findUserbyName((String)VaadinSession.getCurrent().getAttribute("username"));
	}
}
