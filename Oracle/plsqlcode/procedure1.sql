rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
create or replace procedure raiseSalary(eno in number)
as
  --定义变量保存涨前的薪水
  psal emp.sal%type;
begin
  --得到涨前的薪水
  select sal into psal from emp where empno=eno;
  
  --涨100
  update emp set sal=sal+100 where empno=eno;
  
  dbms_output.put_line('涨前：'||psal||'涨后：'||(psal+100));
end;
/
