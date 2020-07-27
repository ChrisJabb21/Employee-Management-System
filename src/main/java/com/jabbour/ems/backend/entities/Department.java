package com.jabbour.ems.backend.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Department extends AbstractEntity {
		private String deptName;
		
		@OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
		private List<Employee> employees = new LinkedList<>();

		public Department(){}
		
		public Department(String deptName) {
			setName(deptName);
		}
		public String getName() {
			return deptName;
		}
		public void setName(String departmentName) {
			this.deptName = departmentName;
		}

}