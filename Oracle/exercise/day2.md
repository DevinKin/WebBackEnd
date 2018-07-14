查询和Zlotkey相同部门的员工姓名和雇用日期
```oracle
select last_name, hire_date
from employees
where department_id =
(select department_id
from employees 
where last_name='Zlotkey');
```

查询工资比公司平均工资高的员工的员工号，姓名和工资
```oracle
select employee_id, last_name, salary
from employees
where salary >
(select avg(salary)
from employees);
```

查询部门中工资比本部门平均工资高的员工的员工号，姓名和工资
```oracle
select employee_id, last_name, salary
from employees e
where salary >
(select avg(salary)
from employees
where department_id = e.department_id);
```

查询和姓名中包含字母u的员工在相同部门的员工的员工号和姓名
```oracle
select employee_id, last_name
from employees
where department_id in
(select department_id
from employees
where last_name like '%u%');
```

查询在部门的location_id为1700的部门工作的员工的员工号
```oracle
select employee_id
from employees
where department_id in (
    select department_id
    from departments
    where location_id = 1700
);
```

查询管理者是King的员工姓名和工资
```oracle
select last_name, salary
from employees
where manager_id in (
    select employee_id 
    from employees
    where last_name='King'
);
```
