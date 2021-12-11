package com.iiitb.esd_project.service;

import com.iiitb.esd_project.dao.EmployeeDAO;
import com.iiitb.esd_project.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired private EmployeeDAO empDao;

    public void addEmployee(Employee emp){
        empDao.save(emp);
    }

    public boolean validateEmployeeByID(Integer id){
        return empDao.existsById(id);
    }

    public Employee getEmployeeByEmail(String email){
        return empDao.getEmployeeByEmail(email);
    }

    public Optional<Employee> getEmployeeByID(Integer id){
        return empDao.findById(id);
    }
}
