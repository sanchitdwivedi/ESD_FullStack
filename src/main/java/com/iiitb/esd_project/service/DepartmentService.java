package com.iiitb.esd_project.service;

import com.iiitb.esd_project.dao.DepartmentDAO;
import com.iiitb.esd_project.dto.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired private DepartmentDAO deptDao;

    public List<Department> getDepartments(){
        return (List<Department>)deptDao.findAll();
    }
}
