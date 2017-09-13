
package com.coderslab.controller;

import com.coderslab.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentController {
    
    @Autowired
    private ProductDao productDao;
    
    @RequestMapping("/")
    public String indexPage(ModelMap modelMap){
        modelMap.addAttribute("students", productDao.getAllStudent());
        return "index";
    }
    
    
}
