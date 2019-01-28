package com.devinkin.springboot06datamybatis.mapper;


import com.devinkin.springboot06datamybatis.bean.Employee;

public interface EmployeeMapper {

    Employee getEmployeeById(Integer id);

    void insertEmp(Employee employee);
}
