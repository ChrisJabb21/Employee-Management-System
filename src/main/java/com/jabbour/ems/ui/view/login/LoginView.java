package com.jabbour.ems.ui.view.login;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@SuppressWarnings("serial")
@Route("login")
@PageTitle("Login | Slick EMS")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	
	private LoginForm login = new LoginForm();

	
	public LoginView() {
		
		

		addClassName("login-view");
		NativeButton button = new NativeButton("Click here!");
		button.addClassName("lumo-button");
		button.getElement().setAttribute("aria-label", "Click me");
	
		Paragraph footer = new Paragraph("Created by Chris Jabbour🧿");
		
		Notification notification = new Notification(
        "username is user and password is password for the Demo showcase", 3000);

	
		button.addClickListener(event -> notification.open());
		
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		
		login.setAction("login");
		add(new H1("Slick EMS"), login, button);
		add(footer);
	}
	
	
	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		
		if(beforeEnterEvent.getLocation()
		.getQueryParameters()
		.getParameters()
		.containsKey("error")) {
			login.setError(true);
		}
	}
}
