package com.jabbour.ems.ui.view.list;

import com.jabbour.ems.backend.entity.Department;
import com.jabbour.ems.backend.entity.Employee;
import com.jabbour.ems.backend.service.DepartmentService;
import com.jabbour.ems.backend.service.EmployeeService;
import com.jabbour.ems.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="", layout = MainLayout.class)
@PageTitle("Employees | Slick EMS")
/**
 * Construct a Vaadin view to populate grid table with data. Build the
 * initial UI state for the user accessing the application.
 */
public class ListView extends VerticalLayout {
	private EmployeeService employeeService;
	private Grid<Employee> grid = new Grid<>(Employee.class);
	private TextField filterText = new TextField();
	private EmployeeForm empForm;

	/**
	 * component for main view
	 * 
	 * @param employeeService
	 * @param departmentService
	 */
	public ListView(EmployeeService employeeService, DepartmentService departmentService) {
		this.employeeService = employeeService;
		addClassName("list-view"); // declare a CSS class name for styling support.
		setSizeFull(); // Set height and Width to 100%

		configureGrid();
		getToolbar();

		empForm = new EmployeeForm(departmentService.findAll());
		empForm.addListener(EmployeeForm.SaveEvent.class, this::saveEmp);
		empForm.addListener(EmployeeForm.DeleteEvent.class, this::deleteEmp);
		empForm.addListener(EmployeeForm.CloseEvent.class, e -> closeEditor());

		//DivContent
		Div content = new Div( grid, empForm);// place grid and form child components into a div element.
		content.addClassName("content");
		content.setSizeFull();
		add(getToolbar(), content);// add the filtertextfield and to the main view layout.
		updateList();
		closeEditor();
	}

	/***
	 * Event Listener method for saving an employee to its repository.
	 * 
	 * @param event
	 */
	private void saveEmp(EmployeeForm.SaveEvent event) {
		employeeService.save(event.getEmployee());
		updateList();
		closeEditor();
	}

	/***
	 * Event Listener method for deleting an employee to its repository.
	 * 
	 * @param event
	 */
	private void deleteEmp(EmployeeForm.DeleteEvent event) {
		employeeService.delete(event.getEmployee());
		updateList();
		closeEditor();
	}

	/***
	 * Method for hiding the employee form editor
	 */
	private void closeEditor() {
		empForm.setEmployee(null);
		empForm.setVisible(false);
		removeClassName("editing");
	}

	/***
	 * Method that hides and shows the form and based on the up the employee
	 * selected on the grid using the addvaluechange listener
	 * 
	 * @param employee -> the employee to modify or if nothing is selected, null.
	 */
	public void editEmployee(Employee employee) {
		if (employee == null) {
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
		grid.setColumns("firstName", "lastName", "email", "status");
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
	private HorizontalLayout getToolbar() {
		filterText.setPlaceholder("Filter by name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateList());
		Button addEmployeeButton = new Button("Add Employee");
		addEmployeeButton.addClickListener(click -> addEmployee());
		HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmployeeButton);
		toolbar.addClassName("toolbar");
		return toolbar;

		// TODO add a button/grid to add a department
	}

	/***
	 * Method to add a new employee
	 */
	void addEmployee() {
		grid.asSingleSelect().clear();
		editEmployee(new Employee());
	}

	/**
	 * Return all employees from the service after an event is triggered and pass
	 * them into the grid. filter text will look for first or last name of employee
	 */
	private void updateList() {
		grid.setItems(employeeService.findAll(filterText.getValue()));
	}
}
