package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.PicturecommunityMainController;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.shared.ui.label.ContentMode;
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
	
	public MenuViewComponent(boolean disablePersonalDashboard) {
		HorizontalLayout layout = new HorizontalLayout();
		
		Label title = new Label("<h2><b>Picture Community</b></h2>");
		title.setContentMode(ContentMode.HTML);
		layout.addComponent(title);
		layout.setComponentAlignment(title, Alignment.TOP_LEFT);
		
		Label searchInCurrentDashboardLabel = new Label("Search in current dashboard: ");
		layout.addComponent(searchInCurrentDashboardLabel);
		layout.setComponentAlignment(searchInCurrentDashboardLabel, Alignment.TOP_LEFT);
		TextField searchInCurrentDashboard = new TextField();
		layout.addComponent(searchInCurrentDashboard);
		layout.setComponentAlignment(searchInCurrentDashboard, Alignment.TOP_LEFT);
		//searchInCurrentDashboard.setCaption("Search in current dashboard: ");
		
		Button searchInCurrentDashboardButton = new Button("Search", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// Fire up a search query in the DB for all images in the currently presented dashboard
				// TODO
			}
		});
		layout.addComponent(searchInCurrentDashboardButton);
		layout.setComponentAlignment(searchInCurrentDashboardButton, Alignment.TOP_LEFT);
		
		Button allUsersDashboardButton = new Button("All users' dashboard", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(PicturecommunityMainController.ALLUSERSDASHBOARDVIEW);
			}
		});
		allUsersDashboardButton.setStyleName(BaseTheme.BUTTON_LINK);
		layout.addComponent(allUsersDashboardButton);
		layout.setComponentAlignment(allUsersDashboardButton, Alignment.TOP_CENTER);
		
		if(!disablePersonalDashboard) {
			Button personalDashboardButton = new Button("Personal dashboard", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(PicturecommunityMainController.PERSONALDASHBOARDVIEW);
				}
			});
			personalDashboardButton.setStyleName(BaseTheme.BUTTON_LINK);
			layout.addComponent(personalDashboardButton);
			layout.setComponentAlignment(personalDashboardButton, Alignment.TOP_CENTER);
		}
		
		Button aboutButton = new Button("About", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(PicturecommunityMainController.ABOUTVIEW);
			}
		});
		aboutButton.setStyleName(BaseTheme.BUTTON_LINK);
		layout.addComponent(aboutButton);
		layout.setComponentAlignment(aboutButton, Alignment.TOP_RIGHT);
		
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
		layout.addComponent(logoutButton);
		layout.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
		
		layout.setSizeUndefined();
		layout.setSpacing(true);
		setSizeUndefined();
		setCompositionRoot(layout);
	}
}
