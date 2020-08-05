package com.jabbour.ems.ui;

import com.jabbour.ems.ui.view.list.ListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;

@SuppressWarnings("serial")
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
	public MainLayout() {
		createHeader();
		createDrawer();
	}
	
	
	private void createHeader() {
		
		 H1 logo = new H1("Slick EMS");
		 logo.addClassName("logo");
		 
		 Anchor logout = new Anchor("logout", "Log out");
			Button toggleButton = new Button("Toggle ☀️/🌙", click -> {
				ThemeList themeList = UI.getCurrent().getElement().getThemeList(); //
				if (themeList.contains(Lumo.DARK)) { //
					themeList.remove(Lumo.DARK);
				} else {
					themeList.add(Lumo.DARK);
				}
			});
		 
		 HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo,toggleButton, logout);
		 header.expand(logo);
		 header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		 header.setWidth("100%");
		 header.addClassName("header");
		 addToNavbar(header);
		
	}
	
	private void createDrawer() {
		RouterLink listLink = new RouterLink("List", ListView.class);
		listLink.setHighlightCondition(HighlightConditions.sameLocation());
		addToDrawer(new VerticalLayout(listLink));
	}
	

}
