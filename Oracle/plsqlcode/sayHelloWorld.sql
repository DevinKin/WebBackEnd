rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
/*
��һ���洢���̣���ӡHelloWorld

���ô洢���̣�
1. exec sayHelloWorld();
2. begin
     sayHelloWorld();
     sayHelloWorld();
   end;
   /
*/
create or replace procedure sayHelloWorld is
begin
  dbms_output.put_line('Hello World!');
end sayHelloWorld;
/
