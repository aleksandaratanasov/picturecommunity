package com.example.picturecommunity.view;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.ColumnResizeEvent;

/*
 * TODO
 * 1)insert JFreeChart with selectable number of users with highest upload numbers (for example: top 5, top 10 and top 20) 
 * 2)delete user(s) from a panel
 * 		- delete selected user's image gallery (incl. files)
 * 		- delete contact entry in list of contact of all users who have the deleted user as a contact
 */

@SuppressWarnings("serial")
@PreserveOnRefresh
public class AdminView extends VerticalLayout implements View {
	
	public AdminView() {
		HorizontalLayout layout = new HorizontalLayout();
		
		// TEST DELETE USER
		/*EntityManagerFactory factory = Persistence.createEntityManagerFactory("picturecommunity");
		EntityManager em = factory.createEntityManager();
		// THE RIGHT WAY TO DO IT - use EntityTransaction!!!
		EntityTransaction tx = em.getTransaction();
		tx.begin();		
		try {
			Query q = em.createQuery(
					"DELETE FROM User u WHERE u.id = :id")
					.setParameter("id", 1);
			if(q.executeUpdate() < 0) throw new Exception("Shitty documention on executeUpdate() doesn't give any information on the return codes...Nice!");
			
			layout.addComponent(new Label("DELETE query passed"));
			em.flush();
		}
		catch(Exception ex) {
			layout.addComponent(new Label(ex.getMessage()));
		}
		tx.commit();
		em.close();*/
		
		
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
