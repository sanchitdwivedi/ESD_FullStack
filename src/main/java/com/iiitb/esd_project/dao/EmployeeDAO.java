package com.iiitb.esd_project.dao;

import com.iiitb.esd_project.dto.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeDAO extends CrudRepository<Employee, Integer> {

    @Query("SELECT u FROM Employee u WHERE u.email=?1")
    public Employee getEmployeeByEmail(String email);
}
