package com.jabbour.ems.backend.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Employee extends AbstractEntity implements Cloneable {
	
	public enum Status {
		FullTime, OnLeave, Contract
	}
	
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
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Employee.Status status;

	public Employee.Status getStatus() {
		return status;
	}
	public void setStatus(Employee.Status status) {
		this.status = status;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	 public String nameToString() { return firstName + " " + lastName; }
	 
}
