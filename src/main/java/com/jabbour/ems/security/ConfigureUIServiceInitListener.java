package com.jabbour.ems.security;

import org.springframework.stereotype.Component;

import com.jabbour.ems.ui.view.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
	
	@Override
	/***
	 * method that runs when a Vaadin Service is initialized
	 * 	
	 *  overriden for authenicate method implementation
	 *  
	 *  listen for the initialization of the UI
	 *  (internal root component) and 
	 *  add a listener before each view transition.
	 */
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
	}
	
	// listener 
	/**
	 * Reroute all requests to the login if the user is not logged in
	 * @param event
	 */
	public void authenticateNavigation(BeforeEnterEvent event) {
		if(!LoginView.class.equals(event.getNavigationTarget())
			&& !SecurityUtils.isUserLoggedIn()) {
			event.rerouteTo(LoginView.class);
		}
	}
	
	
	

}
