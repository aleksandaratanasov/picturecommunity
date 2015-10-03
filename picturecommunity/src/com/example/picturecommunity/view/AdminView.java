package com.example.picturecommunity.view;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/*
 * TODO 
 * 1)delete user(s) from a panel
 * 		- delete selected user's image gallery (incl. files)												DONE
 * 		- delete contact entry in list of contact of all users who have the deleted user as a contact 		PENDING
 * 3)fix table and chart refresh - https://vaadin.com/book/vaadin7/-/page/advanced.push.html
 * Right now the  Admin view components wait for the next server request to update their content. Even refreshing the web page
 * doesn't work so if a user changes (number of uploads for example) or is deleted the changes are not displayed immediately!
 */

@SuppressWarnings("serial")
@PreserveOnRefresh
public class AdminView extends VerticalLayout implements View {
	
	public AdminView() {
		HorizontalLayout layout = new HorizontalLayout();
		
		setSizeFull();
		setSpacing(true);
		addComponent(new MenuViewComponent(true));

		layout.setWidth("80%");
		layout.addComponent(new UserManagementViewComponent());
		layout.addComponent(new UploadStatisticsViewComponent());
		addComponent(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("In this view you can manage the users and show some statistics about them");
	}

}
