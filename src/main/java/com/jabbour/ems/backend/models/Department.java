package com.jabbour.ems.backend.models;

public class Department extends AbstractEntity {
		
		private int deptId;
		private String deptName;

		public Department() {
		}
		public Department(int departmentId, String departmentName) {
			this.deptId = departmentId;
			this.deptName = departmentName;
		}
	
		public void setDepartmentId(int departmentId) {
			this.deptId = departmentId;
		}
		public String getName() {
			return deptName;
		}
		public void setDepartmentName(String departmentName) {
			this.deptName = departmentName;
		}
		
		@Override
		public String toString() {
			return "Department [departmentId=" + deptId + ", departmentName=" + deptName + "]";
		}
	
	
	
}
