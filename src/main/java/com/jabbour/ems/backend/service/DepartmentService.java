package com.jabbour.ems.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jabbour.ems.backend.entity.Department;
import com.jabbour.ems.backend.repository.DepartmentRepository;

@Service
public class DepartmentService {
	private DepartmentRepository departmentRepository;
	
	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}
	
	/**
	 * 
	 * @return all instances of type department
	 */
	public List<Department> findAll() {
		return departmentRepository.findAll();
	}
}
