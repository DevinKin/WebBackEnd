rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
/*
1. ΪԱ���ǹ��ʡ��� ��͹��ʵ���ÿ������10%���������ܶ�ܳ���5��Ԫ��������ǹ����������ǹ��ʺ�Ĺ����ܶ������ǹ��������Լ���������
SQL���
select empno, sal from emp order by sal;
--->���--->ѭ��--->�˳�����:1. �����ܶ�>5W 2.notfound


1. �ǹ��ʵ�������countEmp number := 0;
2. �Ǻ�Ĺ����ܶsalTotal number;
    1. select sum(sal) into salTotal from emp;
    2. �Ǻ�=��ǰ+sal*0.1
*/
declare
  cursor cemp is select empno, sal from emp order by sal;
  pempno emp.empno%type;
  psal emp.sal%type;
  -- �ǹ��ʵ�����
  countEmp number := 0;
  -- �ǹ����ܶ�;
  salTotal number;   
begin
  -- �õ���ʼ���Ĺ����ܶ�
  select sum(sal) into salTotal from emp;
  open cemp;
  loop
    --1���ܶ�>5W
    exit when salTotal > 80000;
    --ȡһ��Ա���ǹ���
    fetch cemp into pempno, psal;
    --��һ��Ա����������֮�󳬳�Ԥ�㣬�˳�
    exit when (salTotal + psal*0.1) > 80000;
    --2��notfound
    exit when cemp%notfound;
    --�ǹ���
    update emp set sal=sal*1.1 where empno=pempno;
    --����+1
    countEmp := countEmp + 1;
    --2.�Ǻ�=��ǰ+sal*0.1
    salTotal := salTotal + psal*0.1;
  end loop;
  close cemp;
  
  commit;
  dbms_output.put_line('�Ǻ�Ĺ����ܶ'||salTotal||'�ǹ��ʵ�������'||countEmp);
end;
/
