CREATE DATABASE day12;
USE day12;
CREATE TABLE product (
  id int PRIMARY KEY AUTO_INCREMENT,
  pname VARCHAR(20),
  price DOUBLE,
  pdsc VARCHAR(20)
);

INSERT INTO product VALUES (NULL,'电视机', 3200, '液晶曲面大电视');
INSERT INTO product VALUES (NULL,'韭菜盒子', 3, '味重请小心食用');
INSERT INTO product VALUES (NULL,'益达', 10, '韭菜伴侣');
INSERT INTO product VALUES (NULL,'十三香', 12, '收益拍');
