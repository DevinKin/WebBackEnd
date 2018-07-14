/*
触发器练习1：限制每个部门只招聘6名职工，超过计划则报出错误信息
1. SQL语句：
  1. 查询职工表中，通过每个部门的总人数：select count(*) from emp where deptno=dno;
2. 变量:
  1. 人数：num number := 0;
*/
create or replace trigger limitEmpCount
before insert
on emp
for each row
declare
  cursor cemp(dno number) is select count(*) from emp where deptno=dno;
  num number :=0;
begin
  --打开部门表光标
  open cemp(:new.deptno);
  fetch cemp into num;
  if num >= 6 then raise_application_error(-20003,'部门: '||:new.deptno||',员工已有6人');
  end if;
  close cemp;
end;
/