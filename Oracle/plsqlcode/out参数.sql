PL/SQL Developer Test script 3.0
12
/*
查询某个员工的姓名 月薪 职位
*/
create or replace procedure queryEmpInformation(eno in number,
                                                  pename out varchar2,
                                                  psal out number,
                                                  pjob out varchar2)
as
begin
  select ename,sal,job into pename,psal,pjob from emp where empno=eno;
end;

0
0
