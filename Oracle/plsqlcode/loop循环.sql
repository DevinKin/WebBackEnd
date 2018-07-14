rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
-- 打印1-10 
declare 
  -- Local variables here
  pnum number :=1;
begin
  dbms_output.put_line('loop 循环');
  loop
    --循环
    --退出条件
    exit when pnum > 10;
    dbms_output.put_line(pnum);
    --加1
    pnum := pnum + 1;
  end loop;
  dbms_output.put_line('while 循环');
  while pnum < 20
    loop
      dbms_output.put_line(pnum);
      pnum := pnum + 1;
    end loop;
  dbms_output.put_line('for 循环');
  for i in 1..10
    loop
      dbms_output.put_line(i);
    end loop;
end;
/
