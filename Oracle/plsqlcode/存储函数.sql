rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
create or replace function queryEmpIncome(eno in number)
return number
as
    --��н�ͽ���
    psal emp.sal%type;
    pcomm emp.comm%type;
begin
    --�õ���н�ͽ���
    select sal,comm into psal,pcomm from emp where empno=eno;
    --����������
    return psal*12+nvl(pcomm,0);
end;
/
