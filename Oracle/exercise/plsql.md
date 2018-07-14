按员工的工种涨工资，总裁1000元，经理涨800，其他人员涨400元
```postgresplsql
declare
    esal emp.esal%type;
    cursor cemp is select * from emp;
begin
    open cemp;
    loop
       if job = 'PRESIDENT' then esal := 1000
        elsif job = 'MANAGER' then esal := 800
        else esal :=400;
       end if;
       update emp set sal=sal+esal where empno = cemp.empno;
    end loop;
    close cemp;
end;
```
