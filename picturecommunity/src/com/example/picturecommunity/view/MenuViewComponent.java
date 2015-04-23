package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.PicturecommunityUI;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@PreserveOnRefresh
public class MenuViewComponent extends CustomComponent {
	
	public MenuViewComponent() {
		HorizontalLayout layout = new HorizontalLayout();
		
		Label searchInCurrentDashboardLabel = new Label("Search in current dashboard: ");
		TextField searchInCurrentDashboard = new TextField();
		
		Button searchInCurrentDashboardButton = new Button("Search", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// Fire up a search query in the DB for all images in the currently presented dashboard
				// TODO
			}
		});
		
		Button allUsersDashboardButton = new Button("All users' dashboard", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(PicturecommunityUI.ALLUSERSDASHBOARDVIEW);
			}
		});
		allUsersDashboardButton.setStyleName(BaseTheme.BUTTON_LINK);
		
		Button personalDashboardButton = new Button("Personal dashboard", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(PicturecommunityUI.PERSONALDASHBOARDVIEW);
			}
		});
		personalDashboardButton.setStyleName(BaseTheme.BUTTON_LINK);
		
		Button aboutButton = new Button("About", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(PicturecommunityUI.ABOUTVIEW);
			}
		});
		aboutButton.setStyleName(BaseTheme.BUTTON_LINK);
		
		Button logoutButton = new Button("Logout", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// logout user from DB
				// Logout the user
				getSession().setAttribute("username", null);
				// Quit UI session
				getUI().getSession().close();
				
				// Return to login view
				getUI().getPage().setLocation(getUI().getPage().getLocation().getPath());
			}
		});
		logoutButton.setStyleName(BaseTheme.BUTTON_LINK);
		
		layout.addComponent(searchInCurrentDashboardLabel);
		layout.setComponentAlignment(searchInCurrentDashboardLabel, Alignment.TOP_LEFT);
		layout.addComponent(searchInCurrentDashboard);
		layout.setComponentAlignment(searchInCurrentDashboard, Alignment.TOP_LEFT);
		layout.addComponent(searchInCurrentDashboardButton);
		layout.setComponentAlignment(searchInCurrentDashboardButton, Alignment.TOP_LEFT);
		layout.addComponent(allUsersDashboardButton);
		layout.setComponentAlignment(allUsersDashboardButton, Alignment.TOP_RIGHT);
		layout.addComponent(personalDashboardButton);
		layout.setComponentAlignment(personalDashboardButton, Alignment.TOP_RIGHT);
		layout.addComponent(aboutButton);
		layout.setComponentAlignment(aboutButton, Alignment.TOP_RIGHT);
		layout.addComponent(logoutButton);
		layout.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
		layout.setSizeUndefined();
		layout.setSpacing(true);
		setSizeUndefined();
		setCompositionRoot(layout);
	}
}
