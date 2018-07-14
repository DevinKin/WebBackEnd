rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
-- 查询并打印员工的姓名和薪水
/*
光标的属性：
%isopen
%rowcount(影响的行数)
%found
%nofound
*/
declare 
  -- 定义光标
  cursor cemp is select ename,sal from emp;
  pename emp.ename%type;
  psal emp.sal%type;
begin
  -- 打开光标
  open  cemp;
  loop
    --取当前记录
    fetch cemp into pename, psal;
    --没有取到记录，退出
    exit when cemp%notfound;
  
    dbms_output.put_line(pename||'的薪水是'||psal);
  end loop;
  --关闭光标
  close cemp;
  
end;
/
