package com.devinkin.springboot.controller;

import com.devinkin.springboot.dao.EmployeeDao;
import com.devinkin.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    /**
     * 查询所有员工返回页面
     * @return
     */
    @GetMapping("/emps")
    public String list(Model model) {
        Collection<Employee> all = employeeDao.getAll();
        // 放在请求域中进行共享
        model.addAttribute("emps", all);

        // thymeleaf默认会拼字符串
        return "emp/list";
    }

    /**
     * 来到员工添加页面
     * @return
     */
    @GetMapping("/emp")
    public String toAddPage() {
        // 来到添加页面
        return "emp/add";
    }
}
