package com.example.picturecommunity.controller;

import com.example.picturecommunity.model.User;
import com.vaadin.server.VaadinSession;

public class PersonalDashboardController {
	private User current_user;
	
	public PersonalDashboardController() {
		current_user = UserController.findUserbyName((String)VaadinSession.getCurrent().getAttribute("username"));
	}
}
