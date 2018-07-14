rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
/*
SQL语句
1. 部门：`select deptno from dept;`
2. 查部门中的员工薪水：`select sal from emp wher deptno=??`

变量
1. 每个段的人数
    1. count1 number;
    2. count2 number;
    3. count3 number;
2. 部门的工资总额：salTotal number := 0;
    1. `select sum(sal) into salTotal from emp where deptno=??`
    2. 累加
*/
declare 
  -- 外循环光标
  cursor cdeptno is select deptno from dept;
  -- 内循环光标
  cursor csal(dno number) is select sal from emp where deptno=dno;
  --每个段的人数
  count1 number := 0;
  count2 number := 0;
  count3 number := 0;
  --部门工资总额
  salTotal number := 0;
  --存储光标取出的值
  pdeptno dept.deptno%type;
  psal emp.sal%type;
begin
  -- 打开外光标
  open cdeptno;
  loop
    --清空统计数量
    count1 := 0;
    count2 := 0;
    count3 := 0;
    --清空部门工资总额
    salTotal := 0;
    --从外光标取出部门号
    fetch cdeptno into pdeptno;
    exit when cdeptno%notfound;
    --打开内光标
    open csal(pdeptno);
    loop
      fetch csal into psal;
      exit when csal%notfound;
      if psal < 3000 then count1 := count1 + 1;
         elsif psal < 6000 then count2 := count2 + 1;
         else count3 := count3 + 1;
      end if;
      salTotal := salTotal + psal; 
    end loop;
    close csal;
    dbms_output.put_line('outer');
    insert into msg1 values(pdeptno,count1,count2,count3,salTotal);
  end loop;
  close cdeptno;
  commit;
  dbms_output.put_line('完成');
end;
/
