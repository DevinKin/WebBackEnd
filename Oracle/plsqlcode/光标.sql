rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
-- ��ѯ����ӡԱ����������нˮ
/*
�������ԣ�
%isopen
%rowcount(Ӱ�������)
%found
%nofound
*/
declare 
  -- ������
  cursor cemp is select ename,sal from emp;
  pename emp.ename%type;
  psal emp.sal%type;
begin
  -- �򿪹��
  open  cemp;
  loop
    --ȡ��ǰ��¼
    fetch cemp into pename, psal;
    --û��ȡ����¼���˳�
    exit when cemp%notfound;
  
    dbms_output.put_line(pename||'��нˮ��'||psal);
  end loop;
  --�رչ��
  close cemp;
  
end;
/
