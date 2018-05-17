CREATE DATABASE day09;
use day09;

CREATE TABLE user(
  id INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  password VARCHAR(20),
  email VARCHAR(20),
  name VARCHAR(20),
  sex VARCHAR(10),
  birthday DATE,
  hobby VARCHAR(50)
);
INSERT INTO user VALUES (NULL, 'tom', '123', 'tom@126.com', 'tom', '1', '1988-01-01', NULL);