rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
create or replace function queryEmpIncome(eno in number)
return number
as
    --月薪和奖金
    psal emp.sal%type;
    pcomm emp.comm%type;
begin
    --得到月薪和奖金
    select sal,comm into psal,pcomm from emp where empno=eno;
    --返回年收入
    return psal*12+nvl(pcomm,0);
end;
/
