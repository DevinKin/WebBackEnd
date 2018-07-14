/*
触发器应用一：禁止在非工作时间插入新员工
非工作时间：
  1. 周末: to_char(sysdate,'day') in ('星期六','星期日')
  2. 上班前 下班后：to_number(to_char(sysdate,'hh24')) not between 9 and 17
*/
create or replace trigger securityemp
before insert
on emp
begin
  if to_char(sysdate,'day') in ('星期六','星期日') or
    to_number(to_char(sysdate,'hh24')) not between 9 and 17 then
    --禁止insert操作
    raise_application_error(-20001,'不能再非工作时间插入数据.');
  end if;
end;
/