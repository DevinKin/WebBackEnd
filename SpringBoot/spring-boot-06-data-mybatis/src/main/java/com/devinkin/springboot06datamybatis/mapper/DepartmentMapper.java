package com.devinkin.springboot06datamybatis.mapper;

import com.devinkin.springboot06datamybatis.bean.Department;
import org.apache.ibatis.annotations.*;


// 指定这是一个操作数据库的mapper
public interface DepartmentMapper {
    @Select("SELECT * FROM department WHERE id=#{id}")
    Department getDeptById(Integer id);

    @Delete("DELETE FROM department WHERE id=#{id}")
    int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO department(department_name) values(#{departmentName})")
    int insertDept(Department department);

    @Update("UPDATE department SET department_name=#{departmentName} WHERE id=#{id}")
    int updateDept(Department department);
}
