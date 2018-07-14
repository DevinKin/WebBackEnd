rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
-- 涨工资 
declare 
  -- 定义光标
  cursor cemp is select empno,job from emp;
  pempno emp.empno%type;
  pjob emp.job%type;

begin
  rollback;
  -- 打开光标
  open cemp;
  loop
    --取一个员工
    fetch cemp into pempno,pjob;
    
    exit when cemp%notfound;
    
    --判断职位
    if pjob = 'PRESIDENT' then update emp set sal=sal+1000 where empno=pempno;
       elsif pjob = 'MANAGER' then update emp set sal=sal+800 where empno=pempno;
       else update emp set sal=sal+400 where empno=pempno;
    end if;
  end loop;
  
  -- 关闭光标
  close cemp;
  -- 提交
  commit;
  dbms_output.put_line('完成'); 
end;
/
