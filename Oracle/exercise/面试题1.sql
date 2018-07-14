select id,name,money, (select money from test1 where id=t.id-1) money1
from test1 t;