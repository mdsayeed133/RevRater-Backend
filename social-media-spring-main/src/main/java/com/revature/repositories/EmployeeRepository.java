package com.revature.repositories;

import com.revature.models.Department;
import com.revature.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<List<Employee>> findByDepartment(Department departments);

    Optional<List<Employee>> findByFirstNameContainingIgnoreCase(String firstName);

    Optional<List<Employee>> findByLastNameContainingIgnoreCase(String lastName);

}