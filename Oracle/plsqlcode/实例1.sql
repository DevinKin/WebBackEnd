rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
-- Created on 2018/7/10 by KING 
/*
SQL���

SELECT to_char(hiredate,'YYYY') from emp;
--->���--->ѭ��--->�˳�����:notfound;

������1����ʼֵ 2�����յõ�
1. count80 number := 0;
2. count81 number := 0;
3. count82 number := 0;
4. count87 number := 0;
*/
declare 
  -- ��ְ���
  cursor cemp is select to_char(hiredate,'yyyy') from emp;
  phiredate varchar2(4);
  
  --ÿ����ְ������
  count80 number := 0;
  count81 number := 0;
  count82 number := 0;
  count87 number := 0;
begin
  open cemp;
  loop
    -- ȡһ��Ա������ְ���
    fetch cemp into phiredate;
    exit when cemp%notfound;
    -- �ж����
    if phiredate = '1980' then count80 := count80 + 1;
       elsif phiredate = '1981' then count81 := count81 + 1;
       elsif phiredate = '1981' then count81 := count82 + 1;
       else count87 := count87 + 1;
    end if;
  end loop;
  
  close cemp;
  
  --������
  dbms_output.put_line('Total:'||(count80+count81+count82+count87));
  dbms_output.put_line('1980:'||count80);
  dbms_output.put_line('1981:'||count81);
  dbms_output.put_line('1982:'||count82);
  dbms_output.put_line('1987:'||count87);
end;
/
