package com.jabbour.ems.backend.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Employee extends AbstractEntity implements Cloneable {
	@NotNull
	@NotEmpty
	private String firstName = "";
	
	@NotNull
	@NotEmpty
	private String lastName = "";
	
	@OneToOne
	@JoinColumn(name = "dept_id")
	private Department department;
	
	@Email
	@NotNull
	@NotEmpty
	private String email = "";
	
	private String position;
	private String phoneNumber;
	
	public Employee() {
	}
	
	public Employee(String firstName, String lastName, Department department) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String nameToString() {
		return firstName + " " + lastName; 
	}
	
	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName
				+ ", departmentName=" + department + "]";
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
