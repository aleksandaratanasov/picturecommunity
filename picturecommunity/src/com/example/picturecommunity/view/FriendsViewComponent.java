package com.example.picturecommunity.view;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.persistence.EntityTransaction;

import com.example.picturecommunity.controller.Broadcaster;
import com.example.picturecommunity.controller.FriendsController;
import com.example.picturecommunity.model.Image;
import com.example.picturecommunity.model.User;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
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
	Table searchTable = new Table();
	
	
	public FriendsViewComponent() {
		if(friendsController.getCurrentUser() == null)
		System.out.println("NO USER FOUND!!! PANIC");
	VerticalLayout layout = new VerticalLayout();
	
	friendsTable = new Table();
	friendsTable.addContainerProperty("Name", Label.class, null);
	friendsTable.addContainerProperty("Status", Label.class, null);
	friendsTable.setPageLength(friendsTable.size());
	Map<String,String> friendNames = friendsController.getFriendNamesAndStatus();
	
	for(Entry<String, String> entry:friendNames.entrySet()) {
		Object newItemId = friendsTable.addItem();
		Item row = friendsTable.getItem(newItemId);
		row.getItemProperty("Name").setValue(new Label(entry.getKey()));
		row.getItemProperty("Status").setValue(new Label(entry.getValue()));
	}
	Label addFriendLabel = new Label("Type in friends name: ");
	userNotFoundLabel = new Label("Friend could not be added");
	resetUserNotFoundLabel();
	TextField addFriendField = new TextField();
	searchTable = new Table();
	
	searchTable.setSelectable(true);
	searchTable.addContainerProperty("Username",Label.class,null);
	searchTable.addContainerProperty("Add",Button.class,null);
	searchTable.setPageLength(searchTable.size());
	Button addFriendButton = new Button("Search for Friends", new Button.ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
//			resetUserNotFoundLabel();
//			if(!addFriendField.isEmpty()) {
//				if(friendsController.addFriend(addFriendField.getValue())) {
//					Map<String,String> friendNames = friendsController.getFriendNamesAndStatus();
//					Object newItemId = friendsTable.addItem();
//					Item row = friendsTable.getItem(newItemId);
//					row.getItemProperty("Name").setValue(addFriendField.getValue());
//					row.getItemProperty("Status").setValue(friendNames.get(addFriendField.getValue()));
//				}
//				else {
//					
//					userNotFoundLabel.setVisible(true);
//				}
//			}
			searchTable.removeAllItems();
			List<User> searchResult = friendsController.getSearchedUsers((String)addFriendField.getValue());
			List<User> contacts = friendsController.getCurrentUser().getContacts();
			for(User user : searchResult) {
				
				Object newItemId = searchTable.addItem();
				Item row = searchTable.getItem(newItemId);
				row.getItemProperty("Username").setValue(new Label(user.getUserName()));
				Button detailsField = new Button("+");
				if(friendsController.getFriendNames(friendsController.getCurrentUser().getUserName()).contains(user.getUserName())) {
					detailsField.setEnabled(false);
				}
			    detailsField.setData(newItemId);
			    detailsField.addClickListener(new Button.ClickListener() {
			        /**
					 * 
					 */
					private static final long serialVersionUID = 1L;
		
					public void buttonClick(ClickEvent event) {
						
						if(friendsController.addFriend(user.getUserName())) {
							Map<String,String> friendNames = friendsController.getFriendNamesAndStatus();
							Object newItemId = friendsTable.addItem();
							Item row = friendsTable.getItem(newItemId);
							row.getItemProperty("Name").setValue(new Label(user.getUserName()));
							row.getItemProperty("Status").setValue(new Label(friendNames.get(addFriendField.getValue())));
							detailsField.setEnabled(false);
						}
			        } 
			    });
			   // detailsField.addStyleName("link");
				row.getItemProperty("Add").setValue(detailsField);
				
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
	layout.addComponent(searchTable);
	layout.setComponentAlignment(searchTable, Alignment.TOP_LEFT);
	layout.setSizeUndefined();
	layout.setSpacing(true);
	setSizeUndefined();
	setCompositionRoot(layout);
	Broadcaster.register(this);
	}
	@Override
	public void finalize() {
		Broadcaster.unregister(this);
	}
	
	public void resetUserNotFoundLabel() {
		userNotFoundLabel.setVisible(false);
	}

	@Override
	public void receiveBroadcast(String username, String message) {
		try{
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
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
