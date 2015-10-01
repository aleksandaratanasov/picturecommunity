package com.example.picturecommunity.view;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@PreserveOnRefresh
public class AboutView extends VerticalLayout implements View {
	
	private Label someText() {
		Label label = new Label("<center><h1>About this project</h1><br/>A community website for managing and sharing pictures.</center>");
		label.setContentMode(ContentMode.HTML);
		return label;
	}
	
	public AboutView() {
		setSizeFull();
		setSpacing(true);
		addComponent(new MenuViewComponent(false));
		addComponent(new Label("Hello AboutView!"));//+ VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username")));
		addComponent(someText());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Learn something about this project");
	}
}
