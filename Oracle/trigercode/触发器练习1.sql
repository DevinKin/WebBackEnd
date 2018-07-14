/*
��������ϰ1������ÿ������ֻ��Ƹ6��ְ���������ƻ��򱨳�������Ϣ
1. SQL��䣺
  1. ��ѯְ�����У�ͨ��ÿ�����ŵ���������select count(*) from emp where deptno=dno;
2. ����:
  1. ������num number := 0;
*/
create or replace trigger limitEmpCount
before insert
on emp
for each row
declare
  cursor cemp(dno number) is select count(*) from emp where deptno=dno;
  num number :=0;
begin
  --�򿪲��ű���
  open cemp(:new.deptno);
  fetch cemp into num;
  if num >= 6 then raise_application_error(-20003,'����: '||:new.deptno||',Ա������6��');
  end if;
  close cemp;
end;
/