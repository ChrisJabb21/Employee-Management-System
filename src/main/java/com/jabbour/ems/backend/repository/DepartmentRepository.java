package com.jabbour.ems.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jabbour.ems.backend.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Long>{

}
