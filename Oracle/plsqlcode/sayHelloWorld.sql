rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
/*
第一个存储过程：打印HelloWorld

调用存储过程：
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
