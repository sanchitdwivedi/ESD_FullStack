package com.iiitb.esd_project.dao;

import com.iiitb.esd_project.dto.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentDAO extends CrudRepository<Department, Integer> {
}
