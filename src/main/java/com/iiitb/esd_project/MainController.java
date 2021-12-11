package com.iiitb.esd_project;

import com.iiitb.esd_project.dto.Department;
import com.iiitb.esd_project.dto.Employee;
import com.iiitb.esd_project.service.DepartmentService;
import com.iiitb.esd_project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired private EmployeeService empService;
    @Autowired private DepartmentService deptService;

    @GetMapping("")
    public String showHomePage(Model model){
        List<Department> departments = new ArrayList<>();
        for(Department d: deptService.getDepartments()){
            if(d.getRemaining()>0) departments.add(d);
        }
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departments);
        return "index";
    }

    @PostMapping("/Signup")
    public String signup(Employee emp){
//        System.out.println(emp.toString());
        try {
            empService.addEmployee(emp);
        }
        catch (Exception e){
            System.out.println(e.toString());
            return "redirect:/";
        }
        return "signup";
    }

    @PostMapping("/Login")
    public String login(Model model, Employee emp){
        System.out.println("Inside login!");
        Employee employee = empService.getEmployeeByEmail(emp.getEmail());
//        System.out.println(employee.toString());
        if(employee==null || !employee.getPassword().equals(emp.getPassword())) return "redirect:/";
        Employee newEmployee = new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail()
                , employee.getPassword(), employee.getTitle(), employee.getPhotoPath(), employee.getDepartment());
        List<Department> departments = new ArrayList<>();
        for(Department d: deptService.getDepartments()){
            if(d.getRemaining()>0 && d.getId()!=employee.getDepartment().getId()) departments.add(d);
        }

        model.addAttribute("id", employee.getId());
        model.addAttribute("newEmployee", newEmployee);
        model.addAttribute("departments", departments);
        return "home";
    }

    @PostMapping("/Edit")
    public String update(Model model, Employee emp){
        System.out.println("Here!!");
        try {
            Employee old = empService.getEmployeeByID(emp.getId()).get();
//            System.out.println(old);
            if(emp.getId()==null) emp.setId(old.getId());
            if(emp.getEmail()==null) emp.setEmail(old.getEmail());
            if(emp.getFirstName()==null) emp.setEmail(old.getEmail());
            if(emp.getLastName()==null) emp.setLastName(old.getLastName());
            if(emp.getTitle()==null) emp.setTitle(old.getTitle());
            if(emp.getPhotoPath()==null) emp.setPhotoPath(old.getPhotoPath());

            emp.setDepartment(old.getDepartment());
            emp.setPassword(old.getPassword());

//            System.out.println(emp.toString());
            empService.addEmployee(emp);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
