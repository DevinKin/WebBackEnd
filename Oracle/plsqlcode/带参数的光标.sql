rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
-- 查询某个部门的员工姓名
declare 
  cursor cemp(dno number) is select ename from emp where deptno=dno;
  pename emp.ename%type;
begin
  --打开光标
  open cemp(10);
  loop
    fetch cemp into pename;
    exit when cemp%notfound;
    dbms_output.put_line(pename);
  end loop;
  --光比光标
  close cemp;
end;
/
