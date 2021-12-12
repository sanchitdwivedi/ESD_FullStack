package com.iiitb.esd_project.dao;

import com.iiitb.esd_project.dto.Department;
import com.iiitb.esd_project.dto.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeDAO extends CrudRepository<Employee, Integer> {

    @Query("SELECT u FROM Employee u WHERE u.email=?1")
    public Employee getEmployeeByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.id = ?1 WHERE e.email = ?2")
    public void updateId(Integer id, String email);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.photoPath=?2, e.title=?3, e.lastName=?4, e.firstName=?5, e.email=?6, e.password=?7, e.department=?8 WHERE e.id = ?1")
    public void updateEmployee(Integer id, String photoPath, String title, String lname, String fname,
                                   String email, String password, Department department);
}
