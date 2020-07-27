package com.jabbour.ems.ui;

import com.jabbour.ems.backend.entities.Department;
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
import com.vaadin.flow.data.value.ValueChangeMode;
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
	private TextField filterText = new TextField();
	
	
	
    public MainView(EmployeeService employeeService){
    	this.employeeService = employeeService;
    	addClassName("list-view");   //declare a CSS class name for styling support.
    	setSizeFull(); //Set height and Width to 100%
    	configureFilter();
    	configureGrid();
    	add(filterText, grid);// add the grid to the main view layout.
    	updateList();
    }
    /**
     * Method for configuring the grid object and populating the table
     */
    private void configureGrid() {
		grid.addClassName("employee-grid");
		grid.setSizeFull();
		grid.removeColumnByKey("department");
		grid.setColumns("firstName","lastName","email", "status");		
		grid.addColumn(emp -> {
		Department department = emp.getDepartment();
		return department == null ? "-" : department.getName();
			}).setHeader("Department");
		
		grid.getColumns().forEach(col -> col.setAutoWidth(true));
		
	}
    
    /**
     * 
     */
    private void configureFilter() {
    	filterText.setPlaceholder("Filter by name...");
    	filterText.setClearButtonVisible(true);
    	filterText.setValueChangeMode(ValueChangeMode.LAZY);
    	filterText.addValueChangeListener(e -> updateList());
    	
    }
    
    
    
    /**
     * Return all employees from the service and pass them into the grid.
     * filter text will look for first or last name of employee
     * 
     * */
    private void updateList() {
    	grid.setItems(employeeService.findAll(filterText.getValue()));
    }
}
