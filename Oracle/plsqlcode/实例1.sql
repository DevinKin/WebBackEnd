rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
-- Created on 2018/7/10 by KING 
/*
SQL语句

SELECT to_char(hiredate,'YYYY') from emp;
--->光标--->循环--->退出条件:notfound;

变量：1、初始值 2、最终得到
1. count80 number := 0;
2. count81 number := 0;
3. count82 number := 0;
4. count87 number := 0;
*/
declare 
  -- 入职年份
  cursor cemp is select to_char(hiredate,'yyyy') from emp;
  phiredate varchar2(4);
  
  --每年入职的人数
  count80 number := 0;
  count81 number := 0;
  count82 number := 0;
  count87 number := 0;
begin
  open cemp;
  loop
    -- 取一个员工的入职年份
    fetch cemp into phiredate;
    exit when cemp%notfound;
    -- 判断年份
    if phiredate = '1980' then count80 := count80 + 1;
       elsif phiredate = '1981' then count81 := count81 + 1;
       elsif phiredate = '1981' then count81 := count82 + 1;
       else count87 := count87 + 1;
    end if;
  end loop;
  
  close cemp;
  
  --输出结果
  dbms_output.put_line('Total:'||(count80+count81+count82+count87));
  dbms_output.put_line('1980:'||count80);
  dbms_output.put_line('1981:'||count81);
  dbms_output.put_line('1982:'||count82);
  dbms_output.put_line('1987:'||count87);
end;
/
