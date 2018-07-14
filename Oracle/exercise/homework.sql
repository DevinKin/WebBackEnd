/*
SQL��䣺

��һ�����γ�����ϵ����ƽ����
select distinct c.cname,d.dname,avg(cs.grade) avg
from dep d, student s, sc cs, course c
where  c.cname='��ѧ����' and d.dno = s.dno and s.sno = cs.sno and cs.cno = c.cno
group by d.dname, c.cname;
--�ڶ������ɼ�
select cs.grade
from student s, sc cs, dep d
where s.dno = d.dno and d.dno = (select dno from dep where dname='�����ϵ')
and s.sno = cs.sno
and cs.cno=(select cno from course where cname='��ѧ����');

������
  count1 number := 0; --С��60
  count2 number := 0; --60~85
  count3 number := 0; --����85
�洢�����õı���:
  cname varchar2;
  dname varchar2;
  cavg number;
  grade number;
*/
declare
  count1 number; --С��60
  count2 number; --60~85
  count3 number; --����85
  cname varchar2(20);
  dname varchar2(20);
  cavg number;
  grade number;
  cursor csql1 is select distinct c.cname,d.dname,avg(cs.grade) avg
                  from dep d, student s, sc cs, course c
                  where  c.cname='��ѧ����' and d.dno = s.dno and s.sno = cs.sno and cs.cno = c.cno
                  group by d.dname, c.cname;
  cursor csql2(d_name varchar2) is select cs.grade
                  from student s, sc cs, dep d
                  where s.dno = d.dno and d.dno = (select dno from dep where dname=d_name)
                  and s.sno = cs.sno
                  and cs.cno=(select cno from course where cname='��ѧ����');
begin
  open csql1;
  loop
    fetch csql1 into cname,dname,cavg;
    exit when csql1%notfound;
    open csql2(dname);
    --��������
    count1 := 0;
    count2 := 0;
    count3 := 0;
    loop
      fetch csql2 into grade;
      exit when csql2%notfound;
      if grade < 60 then count1 := count1 + 1;
      elsif grade > 60 and grade < 85 then count2 := count2 + 1;
      else count3 := count3 + 1;
      end if;
    end loop;
    close csql2;
    insert into msg2 values(cname, dname, count1, count2, count3, cavg);
  end loop;
  close csql1;
  dbms_output.put_line('ͳ�Ƹ�ϵ'||cname||'�ɼ������');
end;
