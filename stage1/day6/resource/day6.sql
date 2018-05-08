//创建商品表
create table products(
    pid int primary key auto_increment,
    pname varchar(20),
    price double,
    pnum int,
    cno int,
    pdate timestamp
);

insert into products values(null, '泰国大榴莲', 98, 12, 1, null);
insert into products values(null, '新疆大枣', 38, 123, 1, null);
insert into products values(null, '新疆切糕', 65, 50, 2, null);
insert into products values(null, '十三香', 10, 200, 3, null);
insert into products values(null, '老干妈', 20, 180, 3, null);
insert into products values(null, '豌豆黄', 20, 120, 2, null);

