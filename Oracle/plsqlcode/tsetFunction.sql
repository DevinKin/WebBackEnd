rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
begin
    -- Call the function
    :result := queryempincome(eno => 7839);
    dbms_output.put_line(:result);
end;
/
