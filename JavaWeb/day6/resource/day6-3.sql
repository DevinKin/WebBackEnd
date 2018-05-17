-- 用户表
CREATE TABLE user(
  id int auto_increment PRIMARY KEY,
  username VARCHAR(50)
);

-- 订单表
CREATE TABLE orders(
  id INT auto_increment PRIMARY KEY ,
  price DOUBLE,
  user_id int
);

-- 给订单表添加外键约束
ALTER TABLE orders ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES user(id);

-- 向user表中添加数据
INSERT INTO user VALUES (3,'张三');
INSERT INTO user VALUES (4,'李四');
INSERT INTO user VALUES (5,'王五');
INSERT INTO user VALUES (6,'赵六');


-- 向orders表中插入数据
INSERT INTO orders VALUES (1,1314,3);
INSERT INTO orders VALUES (2,1314,3);
INSERT INTO orders VALUES (3,15,4);
INSERT INTO orders VALUES (4,315,5);
INSERT INTO orders VALUES (5,1014,null);
