rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
/*
1. 为员工涨工资。从 最低工资调起每个人涨10%，但工资总额不能超过5万元，请计算涨工资人数和涨工资后的工资总额，并输出涨工资人数以及工资总数
SQL语句
select empno, sal from emp order by sal;
--->光标--->循环--->退出条件:1. 工资总额>5W 2.notfound


1. 涨工资的人数：countEmp number := 0;
2. 涨后的工资总额：salTotal number;
    1. select sum(sal) into salTotal from emp;
    2. 涨后=涨前+sal*0.1
*/
declare
  cursor cemp is select empno, sal from emp order by sal;
  pempno emp.empno%type;
  psal emp.sal%type;
  -- 涨工资的人数
  countEmp number := 0;
  -- 涨工资总额;
  salTotal number;   
begin
  -- 得到初始化的工资总额
  select sum(sal) into salTotal from emp;
  open cemp;
  loop
    --1、总额>5W
    exit when salTotal > 80000;
    --取一个员工涨工资
    fetch cemp into pempno, psal;
    --下一个员工工资涨了之后超出预算，退出
    exit when (salTotal + psal*0.1) > 80000;
    --2、notfound
    exit when cemp%notfound;
    --涨工资
    update emp set sal=sal*1.1 where empno=pempno;
    --人数+1
    countEmp := countEmp + 1;
    --2.涨后=涨前+sal*0.1
    salTotal := salTotal + psal*0.1;
  end loop;
  close cemp;
  
  commit;
  dbms_output.put_line('涨后的工资总额：'||salTotal||'涨工资的人数：'||countEmp);
end;
/
