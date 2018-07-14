查询工资大于12000的员工姓名和工资
```oracle
select last_name, salary
from employees
where salary > 12000;
```

查询员工工号为176的员工的姓名和部门号
```oracle
select last_name,department_id
from employees
where employee_id=176;
```

选择工资不在5000到12000的员工的姓名和工资
```oracle
select last_name,salary
from employees
where salary not between 5000 and 12000;
```

选择雇用时间在1998-02-01到1998-05-01之间的员工姓名，job_id和雇用时间
```oracle
select last_name,job_id,hire_date
from employees
where hire_date between '1-2月-1998' and '1-5月-1998';
```

选择在20或50号部门工作的员工姓名和部门号
```oracle
select last_name, department_id
from employees
where department_id in (20,50);
```

选择在1994年雇用的员工的姓名和雇用时间
```oracle
select last_name,hire_date
from employees
--where to_char(hire_date,'yyyy') = '1994';
where hire_date like '%-94';
```

选择公司中没有管理者的员工姓名及job_id
```oracle
select last_name,job_id
from employees
where manager_id is null;
```

选择公司中有奖金的员工姓名，工资和奖金级别
```oracle
select last_name,salary,commission_pct
from employees
where commission_pct is not null;
```

选择员工姓名的第三个字母是ａ的员工姓名
```oracle
select last_name
from employees
where last_name like '__a%';
```

选择姓名中有字母a和e的员工姓名
```oracle
select last_name
from employees
where last_name like '%a%' or last_name like '%e%';
```

显示系统时间
```oracle
select to_char(sysdate,'yyyy-mm-dd hh:mi:ss') from dual;
```

查询员工号，姓名，工资，以及工资提高百分比之20%后的结果(new salary)
```oracle
select employee_id,last_name,salary, salary*1.2 new_salary
from employees;
```

将员工的姓名按首字母排序，并写出姓名的长度(length)
```oracle
select last_name, length(last_name) length
from employees
order by last_name;
```

查询各员工的姓名，并显示各员工在公司工作的月份数(worked_month)
```oracle
select last_name, months_between(sysdate,hire_date) worked_month
from employees;
```


查询员工的姓名，以及在公司工作的月份数(worked_month)，并按月份降序排列
```oracle
select last_name, months_between(sysdate,hire_date) worked_month
from employees
order by worked_month desc;
```

做一个查询，产生下面的结果：`<last_name> earns <salary> monthly but wants <salary*3>`
```oracle
select last_name || ' earns ' || salary || ' monthly but wants ' || salary*3
from employees;
```

使用decode函数，按照下面的条件，产生下面的结果
```oracle
select last_name,job_id,
decode (job_id, 'AD_PRES', 'A',
                'ST_MAN', 'B',
                'IT_PROG', 'C',
                'SA_REP', 'D',
                'ST_CLERK', 'E',
                'Others', 'F') grade
from employees;
```

将上一题的查询用case函数再写一遍
```oracle
select last_name,job_id,
    case job_id when 'AD_PRES' then 'A'
                when 'ST_MAN' then 'B'
                when 'IT_PROG' then 'C'
                when 'SA_REP' then 'D'
                when 'ST_CLERK' then 'E'
                else 'F'
    end grade
from employees;
```

查询公司员工工资的最大值，最小值，平均值，总和
```oracle
select max(salary), min(salary), avg(salary), sum(salary)
from employees;
```

查询job_id的员工工资的最大值，最小值，平均值，总和
```oracle
select job_id,max(salary),min(salary),avg(salary),sum(salary)
from employees
group by job_id;
```

选择具有各个job_id的员工人数
```oracle
select job_id, count(employee_id)
from employees
group by job_id;
```

查询员工最高工资和最低工资的差距(DIFFERENCE)
```oracle
select max(salary) - min(salary) difference
from employees
```

查询各个管理者手下员工的最低工资，其中最低工资不能低于6000，没有管理者的员工不计算在内
```oracle
select manager_id,min(salary)
from employees
where manager_id is not null;
group by manager_id
having min(salary) > 6000;
```

查询所有部门的名字，location_id，员工数量和工资平均值
```oracle
select d.department_name, d.location_id, count(e.employee_id), avg(e.salary)
from employees e, departments d
where e.department_id(+) = d.department_id
group by d.department_name, d.location_id;
```
