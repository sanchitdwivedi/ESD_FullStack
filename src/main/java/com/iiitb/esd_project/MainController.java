package com.iiitb.esd_project;

import com.iiitb.esd_project.dto.Department;
import com.iiitb.esd_project.dto.Employee;
import com.iiitb.esd_project.service.DepartmentService;
import com.iiitb.esd_project.service.EmployeeService;
import com.iiitb.esd_project.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String signup(Employee emp, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes){
//        System.out.println(emp.toString());
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            emp.setPhotoPath(fileName);
            Department dept = deptService.getDepartmentById(emp.getDepartment().getId()).get();
            dept.setRemaining(dept.getRemaining()-1);
            deptService.addDepartment(dept);
            Employee savedEmployee = empService.addEmployee(emp);

            String uploadDir = "user-photos/" + savedEmployee.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            redirectAttributes.addFlashAttribute("success", "Successfully registered!");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("failed", "Failed to register");
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/Login")
    public String login(Model model, Employee emp){
//        System.out.println("Inside login!");
        Employee employee = empService.getEmployeeByEmail(emp.getEmail());
//        System.out.println(employee.toString());
        if(employee==null || !employee.getPassword().equals(emp.getPassword())){

            return "redirect:/";
        }
        Employee newEmployee = new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail()
                , employee.getPassword(), employee.getTitle(), employee.getPhotoPath(), employee.getDepartment());
        List<Department> departments = new ArrayList<>();
        for(Department d: deptService.getDepartments()){
            if(d.getRemaining()>0 && d.getId()!=employee.getDepartment().getId()) departments.add(d);
        }
//        System.out.println(newEmployee);
        model.addAttribute("id", employee.getId());
        model.addAttribute("newEmployee", newEmployee);
        model.addAttribute("departments", departments);
        return "home";
    }

    @PostMapping("/Edit")
    public String update(Model model, Employee emp, @RequestParam("image") MultipartFile multipartFile, @RequestParam("oldId") Integer oldId){
//        System.out.println("Here!!");
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            Employee old = empService.getEmployeeByID(oldId).get();
            if(emp.getId()==null) emp.setId(old.getId());
            if(!fileName.equals("")){
                emp.setPhotoPath(fileName);
                String uploadDir = "user-photos/" + emp.getId();
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
            else if(old.getPhotoPath()!=null){
                fileName = old.getPhotoPath();
                emp.setPhotoPath(fileName);
                FileUploadUtil.copyFile("user-photos/" + old.getId() + "/" + fileName, "user-photos/" + emp.getId() + "/" + fileName);
            }
            if(emp.getEmail()==null) emp.setEmail(old.getEmail());
            if(emp.getFirstName()==null) emp.setFirstName(old.getFirstName());
            if(emp.getLastName()==null) emp.setLastName(old.getLastName());
            if(emp.getTitle()==null) emp.setTitle(old.getTitle());

            emp.setPassword(old.getPassword());

//            System.out.println(emp.toString());
            empService.updateId(emp.getId(), old.getEmail());
            empService.updateEmployee(emp.getId(), emp);

            Department dept = deptService.getDepartmentById(old.getDepartment().getId()).get();
            dept.setRemaining(dept.getRemaining()+1);
            deptService.addDepartment(dept);

            dept = deptService.getDepartmentById(emp.getDepartment().getId()).get();
            dept.setRemaining(dept.getRemaining()-1);
            deptService.addDepartment(dept);

        }
        catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }

        List<Department> departments = new ArrayList<>();
        for(Department d: deptService.getDepartments()){
            if(d.getRemaining()>0 && d.getId()!=emp.getDepartment().getId()) departments.add(d);
        }
        model.addAttribute("id", emp.getId());
        model.addAttribute("newEmployee", emp);
        model.addAttribute("departments", departments);
        return "home";
    }
}
