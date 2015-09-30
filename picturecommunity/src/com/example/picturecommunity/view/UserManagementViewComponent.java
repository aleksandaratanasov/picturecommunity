package com.example.picturecommunity.view;

import com.example.picturecommunity.controller.AdminController;
import com.example.picturecommunity.model.User;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.themes.BaseTheme;

public class UserManagementViewComponent extends Panel {
	
	private static final long serialVersionUID = 1L;
	AdminController controller = new AdminController();
	Table table;

	public UserManagementViewComponent() {
		VerticalLayout layout = new VerticalLayout();
		/*CheckBox deleteAllCheckbox = new CheckBox("Delete all");
		deleteAllCheckbox.setImmediate(true);
		deleteAllCheckbox.addBlurListener(new BlurListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void blur(BlurEvent event) {
				deleteAllCheckbox.setValue(controller.toggleAllUsersForDeletion());
			}

		});*/
		
		Button deleteButton = new Button("Delete", new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				controller.deleteUsers();
				//deleteAllCheckbox.setValue(false);
				//controller.getUsers();
				table.refreshRowCache();
			}
		});
		deleteButton.setStyleName(BaseTheme.BUTTON_LINK);
		//deleteButton.setImmediate(true);
		
		if(controller.getUsers().isEmpty()) {
			Label warningSizeUsers = new Label("Warning: users list's size == 0");
			layout.addComponent(warningSizeUsers);
		}
		
		//Table table = new Table();
		table = new Table();
		table.setSelectable(true);
		table.addStyleName("components-inside");
		table.setColumnCollapsingAllowed(true);
		table.addColumnResizeListener(new Table.ColumnResizeListener(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void columnResize(ColumnResizeEvent event) {
		        // Get the new width of the resized column
		        int width = event.getCurrentWidth();
		        
		        // Get the property ID of the resized column
		        String column = (String) event.getPropertyId();

		        // Do something with the information
		        table.setColumnFooter(column, String.valueOf(width) + "px");
		    }
		});
		
		table.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				table.refreshRowCache();
			}
		});
		        
		// Must be immediate to send the resize events immediately
		table.setImmediate(true);
		
		table.addContainerProperty("ID",            Label.class,     null);
		table.addContainerProperty("Username", 		Label.class, 	 null);
		table.addContainerProperty("Delete", 		CheckBox.class,  null);
		//table.addContainerProperty("Comments",      TextField.class, null);
		table.addContainerProperty("Details",       Button.class,    null);
		
		for (User user : controller.getUsers()) {
			Label userIdField = new Label(Long.toString(user.getId()), ContentMode.HTML);
		    Label usernameField = new Label(user.getUserName(), ContentMode.HTML);
		    CheckBox markForDeletionCheckbox = new CheckBox("delete");
		    markForDeletionCheckbox.setImmediate(true);
		    markForDeletionCheckbox.setValue(controller.checkUserStatusForDeletion(user.getId()));
		    markForDeletionCheckbox.addBlurListener(new BlurListener() {
				
				@Override
				public void blur(BlurEvent event) {
					if(markForDeletionCheckbox.getValue()) {
						controller.markUserForDeletion(user.getId());
						Notification.show("Selected user \"" + user.getUserName() + "\" for deletion");
						//markForDeletionCheckbox.setValue(false);
					}
					else {
						controller.unmarkUserForDeletion(user.getId());
						Notification.show("Deselect user \"" + user.getUserName() + "\" for deletion");
						//markForDeletionCheckbox.setValue(true);
					}
				}
			});
		    
		    // Multiline text field. This required modifying the 
		    // height of the table row.
		    //TextField commentsField = new TextField();
		    //commentsField.setColumns(3);
		    
		    // The Table item identifier for the row.
		    Long itemId = user.getId();
		    
		    // Create a button and handle its click. A Button does not
		    // know the item it is contained in, so we have to store the
		    // item ID as user-defined data.
		    Button detailsField = new Button("Show details");
		    detailsField.setData(itemId);
		    detailsField.addClickListener(new Button.ClickListener() {
		        /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
		            // Get the item identifier from the user-defined data.
		            //Long iid = (Long)event.getButton().getData();
		            Notification.show("Uploads: " + Long.toString(user.getUploads()));
		                              //iid.intValue() + " clicked.");
		        } 
		    });
		    detailsField.addStyleName("link");
		    
		    // Create the table row.
		    table.addItem(new Object[] {userIdField, usernameField, markForDeletionCheckbox,
		                                detailsField}, //commentsField
		                                itemId);
		}
		
		// Show just five rows because they are so high.
		table.setPageLength(10);

		layout.addComponent(deleteButton);
		//layout.addComponent(deleteAllCheckbox);
		layout.addComponent(table);
		setContent(layout);
	}
	
	
	
	/*	public UserManagementViewComponent() {		
		VerticalLayout layout = new VerticalLayout();
		CheckBox deleteAllCheckbox = new CheckBox("Delete all");
		
		Button deleteButton = new Button("Delete", new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		deleteButton.setStyleName(BaseTheme.BUTTON_LINK);
		
		List<User> users = controller.getUsers();
		
		if(users.size() == 0) {
			Label warningSizeUsers = new Label("DEBUG_INFO: users.size() == 0");
			layout.addComponent(warningSizeUsers);
		}
		
		Table table = new Table();
		table.setSelectable(true);
		table.addStyleName("components-inside");
		table.setColumnCollapsingAllowed(true);
		table.addColumnResizeListener(new Table.ColumnResizeListener(){
			@Override
		    public void columnResize(ColumnResizeEvent event) {
		        // Get the new width of the resized column
		        int width = event.getCurrentWidth();
		        
		        // Get the property ID of the resized column
		        String column = (String) event.getPropertyId();

		        // Do something with the information
		        table.setColumnFooter(column, String.valueOf(width) + "px");
		    }
		});
		        
		// Must be immediate to send the resize events immediately
		table.setImmediate(true);
		
		table.addContainerProperty("ID",            Label.class,     null);
		table.addContainerProperty("Username", 		Label.class, 	 null);
		table.addContainerProperty("Delete", 		CheckBox.class,  null);
		//table.addContainerProperty("Comments",      TextField.class, null);
		table.addContainerProperty("Details",       Button.class,    null);
		
		for (User user : users) {
			Label userIdField = new Label(Long.toString(user.getId()), ContentMode.HTML);
		    Label usernameField = new Label(user.getUserName(), ContentMode.HTML);
		    CheckBox markForDeletionField = new CheckBox("delete");
		    
		    // Multiline text field. This required modifying the 
		    // height of the table row.
		    //TextField commentsField = new TextField();
		    //commentsField.setColumns(3);
		    
		    // The Table item identifier for the row.
		    Long itemId = user.getId();
		    
		    // Create a button and handle its click. A Button does not
		    // know the item it is contained in, so we have to store the
		    // item ID as user-defined data.
		    Button detailsField = new Button("show details");
		    detailsField.setData(itemId);
		    detailsField.addClickListener(new Button.ClickListener() {
		        public void buttonClick(ClickEvent event) {
		            // Get the item identifier from the user-defined data.
		            //Long iid = (Long)event.getButton().getData();
		            Notification.show("Uploads: " + Long.toString(user.getUploads()));
		                              //iid.intValue() + " clicked.");
		        } 
		    });
		    detailsField.addStyleName("link");
		    
		    // Create the table row.
		    table.addItem(new Object[] {userIdField, usernameField, markForDeletionField,
		                                detailsField}, //commentsField
		                                itemId);
		}
		
		// Show just five rows because they are so high.
		table.setPageLength(10);

		//HorizontalLayout layoutTop = new HorizontalLayout();
		//layoutTop.addComponent(deleteAllCheckbox);
		//layoutTop.addComponent(deleteButton);
		//layout.addComponent(layoutTop);
		layout.addComponent(deleteButton);
		layout.addComponent(deleteAllCheckbox);
		layout.addComponent(table);
		setContent(layout);
 	}*/
}
