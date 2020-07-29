package com.jabbour.ems.ui;

import com.jabbour.ems.backend.entity.Department;
import com.jabbour.ems.backend.entity.Employee;
import com.jabbour.ems.backend.service.DepartmentService;
import com.jabbour.ems.backend.service.EmployeeService;
import com.jabbour.ems.ui.EmployeeForm.SaveEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@Route("")
@CssImport("./styles/shared-styles.css")
/**
 * Construct a new Vaadin view to populate grid table with data.
 * Build the initial UI state for the user accessing the application.
 */
public class MainView extends VerticalLayout {
	private EmployeeService employeeService;
	private Grid<Employee> grid = new Grid<>(Employee.class);
	private TextField filterText = new TextField();
	private EmployeeForm empForm;

	/**
	 * component for main view
	 * @param employeeService
	 */
	public MainView(EmployeeService employeeService,
			DepartmentService departmentService){
    	this.employeeService = employeeService;
    	addClassName("list-view");   //declare a CSS class name for styling support.
    	setSizeFull(); //Set height and Width to 100%

    	configureGrid();
    	configureFilter();

    	empForm = new EmployeeForm(departmentService.findAll());
    	empForm.addListener(EmployeeForm.SaveEvent.class, this::saveEmp);
    	empForm.addListener(EmployeeForm.DeleteEvent.class, this::deleteEmp);
    	empForm.addListener(EmployeeForm.CloseEvent.class, e -> closeEditor());


    	
    	
    	Div content = new Div(grid, empForm);// place grid and form child components into a div element.
    	content.addClassName("content");
    	content.setSizeFull();
    	
    	add(filterText, content);// add the filtertextfield and  to the main view layout.
    	updateList();
    	closeEditor();
    }
	
	/***
	 * Event Listener method 
	 * for saving an employee to its repository. 
	 * @param event
	 */
	private void saveEmp(EmployeeForm.SaveEvent event){
		employeeService.save(event.getEmployee());
		updateList();
		closeEditor();
	}
	
	/***
	 * Event Listener method 
	 * for deleting an employee to its repository. 
	 * @param event
	 */
	private void deleteEmp(EmployeeForm.DeleteEvent event) {
		employeeService.delete(event.getEmployee());
		updateList();
		closeEditor();
	}
	
	
/*** Method for hiding the employee form editor
	 * */
	private void closeEditor(){
		empForm.setEmployee(null);
		empForm.setVisible(false);
		removeClassName("editing");
	}
	
	/***
	 * Method that hides and shows the form 
	 * and based on the up the employee selected on the grid  
	 * using the addvaluechange listener
	 * @param employee -> the employee to modify or if nothing is selected, null.
	 */
	public void editEmployee(Employee employee)
	{
		if(employee == null) {
			closeEditor();
		} else {
			empForm.setEmployee(employee);
			empForm.setVisible(true);
			addClassName("editing");
		}
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
		grid.asSingleSelect().addValueChangeListener(event -> editEmployee(event.getValue()));
		
	}
    /**
     * method for setting up text filter functionality.
     */
    private void configureFilter() {
    	filterText.setPlaceholder("Filter by name...");
    	filterText.setClearButtonVisible(true);
    	filterText.setValueChangeMode(ValueChangeMode.LAZY);
    	filterText.addValueChangeListener(e -> updateList());
    }
    /**
     * Return all employees from the service after an event is triggered and pass them into the grid.
     * filter text will look for first or last name of employee
     * */
    private void updateList() {
    	grid.setItems(employeeService.findAll(filterText.getValue()));
    }
}
