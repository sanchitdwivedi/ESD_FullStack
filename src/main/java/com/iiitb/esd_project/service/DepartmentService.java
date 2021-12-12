package com.iiitb.esd_project.service;

import com.iiitb.esd_project.dao.DepartmentDAO;
import com.iiitb.esd_project.dto.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired private DepartmentDAO deptDao;

    public List<Department> getDepartments(){
        return (List<Department>)deptDao.findAll();
    }

    public Optional<Department> getDepartmentById(Integer id){
        return deptDao.findById(id);
    }

    public Department addDepartment(Department dept){
        return deptDao.save(dept);
    }
}
