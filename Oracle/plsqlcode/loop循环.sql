rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
-- ��ӡ1-10 
declare 
  -- Local variables here
  pnum number :=1;
begin
  dbms_output.put_line('loop ѭ��');
  loop
    --ѭ��
    --�˳�����
    exit when pnum > 10;
    dbms_output.put_line(pnum);
    --��1
    pnum := pnum + 1;
  end loop;
  dbms_output.put_line('while ѭ��');
  while pnum < 20
    loop
      dbms_output.put_line(pnum);
      pnum := pnum + 1;
    end loop;
  dbms_output.put_line('for ѭ��');
  for i in 1..10
    loop
      dbms_output.put_line(i);
    end loop;
end;
/
