package com.madhav.repository;

import com.madhav.model.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByEmpcity(String empcity);
	List<Employee> findByEmpageGreaterThan(Integer empage);
}
