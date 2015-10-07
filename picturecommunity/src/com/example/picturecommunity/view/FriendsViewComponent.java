package com.example.picturecommunity.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.picturecommunity.controller.Broadcaster;
import com.example.picturecommunity.controller.FriendsController;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinSession;
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
public class FriendsViewComponent extends CustomComponent implements Broadcaster.BroadcastListener{

	FriendsController friendsController = new FriendsController();
	Label userNotFoundLabel;
	Table friendsTable;
	public FriendsViewComponent() {
	VerticalLayout layout = new VerticalLayout();
	
	friendsTable = new Table();
	friendsTable.addContainerProperty("Name", String.class, null);
	friendsTable.addContainerProperty("Status", String.class, null);
	friendsTable.setPageLength(friendsTable.size());
	Map<String,String> friendNames = friendsController.getFriendNamesAndStatus();
	
	for(Entry<String, String> entry:friendNames.entrySet()) {
		Object newItemId = friendsTable.addItem();
		Item row = friendsTable.getItem(newItemId);
		row.getItemProperty("Name").setValue(entry.getKey());
		row.getItemProperty("Status").setValue(entry.getValue());
	}
	Label addFriendLabel = new Label("Type in friends name: ");
	userNotFoundLabel = new Label("Friend could not be added");
	resetUserNotFoundLabel();
	TextField addFriendField = new TextField();
	
	Button addFriendButton = new Button("Add Friend", new Button.ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
			resetUserNotFoundLabel();
			if(!addFriendField.isEmpty()) {
				if(friendsController.addFriend(addFriendField.getValue())) {
					Map<String,String> friendNames = friendsController.getFriendNamesAndStatus();
					Object newItemId = friendsTable.addItem();
					Item row = friendsTable.getItem(newItemId);
					row.getItemProperty("Name").setValue(addFriendField.getValue());
					row.getItemProperty("Status").setValue(friendNames.get(addFriendField.getValue()));
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
	Broadcaster.register(this);
	}
	
	public void resetUserNotFoundLabel() {
		userNotFoundLabel.setVisible(false);
	}

	@Override
	public void receiveBroadcast(String username, String message) {
		
		getUI().access(new Runnable() {
            @Override
            public void run() {
                // Show it somehow
            	if(friendsTable != null) {
            		Collection<?> itemids = friendsTable.getItemIds();
            		for(Object itemid : itemids) {
            			Item row = friendsTable.getItem(itemid);
            			if(row.getItemProperty("Name").getValue().equals(username)) {
            				row.getItemProperty("Status").setValue(message);
            				getUI().markAsDirty();
            				break;
            			}
            		}
            	}
                
            }
        });
	}
}
