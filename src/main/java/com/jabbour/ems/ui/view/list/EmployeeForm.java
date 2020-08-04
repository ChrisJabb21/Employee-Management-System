package com.jabbour.ems.ui.view.list;

import java.util.List;

import com.jabbour.ems.backend.entity.Department;
import com.jabbour.ems.backend.entity.Employee;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

/**
 * Employee form component that extends FormLayout Vaadin for form display
 * 
 * @author chris
 *
 */
public class EmployeeForm extends FormLayout {

	private Employee employee;

	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	EmailField email = new EmailField("Email");
	ComboBox<Employee.Status> status = new ComboBox<>("Status");
	ComboBox<Department> department = new ComboBox<>("Department");

	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button close = new Button("Cancel");

	Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

	// Constructor
	public EmployeeForm(List<Department> departments) {
		addClassName("employee-form");
		binder.bindInstanceFields(this);
		department.setItems(departments);
		department.setItemLabelGenerator(Department::getName);
		status.setItems(Employee.Status.values());
		add(firstName, lastName, email, department, status, createButtonsLayout());
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		binder.readBean(employee);
	}

	private Component createButtonsLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		save.addClickShortcut(Key.ENTER);
		close.addClickShortcut(Key.ESCAPE);
		
		save.addClickListener(event -> validateAndSave());
		delete.addClickListener(event -> fireEvent(new DeleteEvent(this, employee)));
		close.addClickListener(event -> fireEvent(new CloseEvent(this)));
		
		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
		return new HorizontalLayout(save, delete, close);
	}
	
	private void validateAndSave() {
		try {
			binder.writeBean(employee);
			fireEvent(new SaveEvent(this, employee));
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}

	// Events
	/**
	 * Superclass for all events and handles the employee object that is edited or
	 * deleted.
	 * 
	 * @author chris
	 */
	public static abstract class EmployeeFormEvent extends ComponentEvent<EmployeeForm> {
		private Employee employee;

		protected EmployeeFormEvent(EmployeeForm source, Employee employee) {
			super(source, false);
			this.employee = employee;
		}

		public Employee getEmployee() {
			return employee;
		}
	}

	public static class SaveEvent extends EmployeeFormEvent {
		SaveEvent(EmployeeForm source, Employee employee) {
			super(source, employee);
		}
	}

	public static class DeleteEvent extends EmployeeFormEvent {
		DeleteEvent(EmployeeForm source, Employee employee) {
			super(source, employee);
		}
	}

	public static class CloseEvent extends EmployeeFormEvent {
		CloseEvent(EmployeeForm source) {
			super(source, null);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
}