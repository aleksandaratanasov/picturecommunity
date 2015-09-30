package com.example.picturecommunity.view;

import java.util.List;

import com.example.picturecommunity.controller.FriendsController;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Item;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@PreserveOnRefresh
public class FriendsViewComponent extends CustomComponent{

	FriendsController friendsController = new FriendsController();
	
	public FriendsViewComponent() {
	VerticalLayout layout = new VerticalLayout();
	
	Table friendsTable = new Table();
	friendsTable.addContainerProperty("Name", String.class, null);
	friendsTable.setPageLength(friendsTable.size());
	List<String> friendNames = friendsController.getFriendNames();
	for(String friendName:friendNames) {
		Object newItemId = friendsTable.addItem();
		Item row = friendsTable.getItem(newItemId);
		row.getItemProperty("Name").setValue(friendName);
	}
	Label addFriendLabel = new Label("Type in friends name: ");
	Label userNotFoundLabel = new Label("Friend could not be added");
	userNotFoundLabel.setVisible(false);
	TextField addFriendField = new TextField();
	
	Button addFriendButton = new Button("Add Friend", new Button.ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
			if(!addFriendField.isEmpty()) {
				if(friendsController.addFriend(addFriendField.getValue())) {
					Object newItemId = friendsTable.addItem();
					Item row = friendsTable.getItem(newItemId);
					row.getItemProperty("Name").setValue(addFriendField.getValue());
				}
				else {
					
					userNotFoundLabel.setVisible(true);
				}
			}
		}
	});
	layout.addComponent(friendsTable);
	layout.setComponentAlignment(friendsTable, Alignment.TOP_LEFT);
	layout.addComponent(addFriendLabel);
	layout.setComponentAlignment(addFriendLabel, Alignment.TOP_LEFT);
	layout.addComponent(addFriendField);
	layout.setComponentAlignment(addFriendField, Alignment.TOP_LEFT);
	layout.addComponent(addFriendButton);
	layout.setComponentAlignment(addFriendButton, Alignment.TOP_LEFT);
	layout.addComponent(userNotFoundLabel);
	layout.setSizeUndefined();
	layout.setSpacing(true);
	setSizeUndefined();
	setCompositionRoot(layout);
	}
}
