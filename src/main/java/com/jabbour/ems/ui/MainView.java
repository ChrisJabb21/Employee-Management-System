package com.jabbour.ems.ui;

import com.jabbour.ems.backend.entities.Employee;
import com.jabbour.ems.backend.service.EmployeeService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */

@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Route("")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view to populate grid table with data.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     */
	
	private EmployeeService employeeService;
	private Grid<Employee> grid = new Grid<>(Employee.class);
	
    public MainView(EmployeeService employeeService){
    	this.employeeService = employeeService;
    	addClassName("list-view");
    	setSizeFull(); //Set height and Width to 100%
    	configureGrid();
    	add(grid);
    	updateList();
    }
    private void configureGrid() {
		grid.addClassName("employee-grid");
		grid.setSizeFull();
		grid.setColumns("firstName","lastName","email", "department", "status");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));
	}
    
    private void updateList() {
    	grid.setItems(employeeService.findAll());
    }
}
