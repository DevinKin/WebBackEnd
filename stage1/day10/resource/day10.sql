CREATE DATABASE day10;
use day10;

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
