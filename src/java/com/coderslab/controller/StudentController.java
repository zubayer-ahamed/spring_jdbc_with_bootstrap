package com.coderslab.controller;

import com.coderslab.dao.studentDao;
import com.coderslab.model.Student;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StudentController {

    @Autowired
    private studentDao studentDao;

    @RequestMapping("/")
    public String indexPage(ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("students", studentDao.getAllStudent());
        modelMap.addAttribute("sm", request.getParameter("sm"));
        modelMap.addAttribute("em", request.getParameter("em"));
        return "index";
    }

    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String saveStudent(HttpServletRequest request, ModelMap modelMap) {
        Student student = new Student();
        student.setName(request.getParameter("name"));
        student.setAge(Integer.parseInt(request.getParameter("age")));
        boolean status = studentDao.createStudent(student);
        if (status) {
            modelMap.addAttribute("sm", "Student Info Saved Successfully");
        } else {
            modelMap.addAttribute("em", "Student Info Not Saved");
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/editStudent/{id}", method = RequestMethod.GET)
    public String editStudent(@PathVariable("id") String id, HttpServletRequest request, ModelMap modelMap) {
        Student student = studentDao.getStudent(Integer.parseInt(id));
        modelMap.addAttribute("student", student);
        modelMap.addAttribute("students", studentDao.getAllStudent());
        return "index";
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public String updateStudent(HttpServletRequest request, ModelMap modelMap) {
        Student student = new Student();

        student.setId(Integer.parseInt(request.getParameter("id")));
        student.setName(request.getParameter("name"));
        student.setAge(Integer.parseInt(request.getParameter("age")));

        boolean status = studentDao.updateStudent(student);
        if (status) {
            modelMap.addAttribute("sm", "Student Info Update Successfully");
        } else {
            modelMap.addAttribute("em", "Student Info Not Update");
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/deleteStudent/{id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable("id") String id, HttpServletRequest request, ModelMap modelMap) {
        boolean status = studentDao.deleteStudent(Integer.parseInt(id));
        if (status) {
            modelMap.addAttribute("sm", "Student Info deleted Successfully");
        } else {
            modelMap.addAttribute("em", "Student Info Not deleted");
        }
        return "redirect:/";
    }

}
