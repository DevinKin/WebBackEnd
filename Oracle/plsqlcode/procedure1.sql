rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
create or replace procedure raiseSalary(eno in number)
as
  --�������������ǰ��нˮ
  psal emp.sal%type;
begin
  --�õ���ǰ��нˮ
  select sal into psal from emp where empno=eno;
  
  --��100
  update emp set sal=sal+100 where empno=eno;
  
  dbms_output.put_line('��ǰ��'||psal||'�Ǻ�'||(psal+100));
end;
/
