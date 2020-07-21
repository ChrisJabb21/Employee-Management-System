package com.jabbour.ems.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jabbour.ems.backend.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
