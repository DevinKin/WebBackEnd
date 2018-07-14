rem PL/SQL Developer Test Script

set feedback off
set autoprint off

rem Execute PL/SQL Block
/*
SQL���
1. ���ţ�`select deptno from dept;`
2. �鲿���е�Ա��нˮ��`select sal from emp wher deptno=??`

����
1. ÿ���ε�����
    1. count1 number;
    2. count2 number;
    3. count3 number;
2. ���ŵĹ����ܶsalTotal number := 0;
    1. `select sum(sal) into salTotal from emp where deptno=??`
    2. �ۼ�
*/
declare 
  -- ��ѭ�����
  cursor cdeptno is select deptno from dept;
  -- ��ѭ�����
  cursor csal(dno number) is select sal from emp where deptno=dno;
  --ÿ���ε�����
  count1 number := 0;
  count2 number := 0;
  count3 number := 0;
  --���Ź����ܶ�
  salTotal number := 0;
  --�洢���ȡ����ֵ
  pdeptno dept.deptno%type;
  psal emp.sal%type;
begin
  -- ������
  open cdeptno;
  loop
    --���ͳ������
    count1 := 0;
    count2 := 0;
    count3 := 0;
    --��ղ��Ź����ܶ�
    salTotal := 0;
    --������ȡ�����ź�
    fetch cdeptno into pdeptno;
    exit when cdeptno%notfound;
    --���ڹ��
    open csal(pdeptno);
    loop
      fetch csal into psal;
      exit when csal%notfound;
      if psal < 3000 then count1 := count1 + 1;
         elsif psal < 6000 then count2 := count2 + 1;
         else count3 := count3 + 1;
      end if;
      salTotal := salTotal + psal; 
    end loop;
    close csal;
    dbms_output.put_line('outer');
    insert into msg1 values(pdeptno,count1,count2,count3,salTotal);
  end loop;
  close cdeptno;
  commit;
  dbms_output.put_line('���');
end;
/
