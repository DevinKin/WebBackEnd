#用户订单表
    CREATE TABLE user(
     id INT PRIMARY KEY AUTO_INCREMENT,
     username VARCHAR(20)
    );

    CREATE TABLE orders(
     id INT PRIMARY KEY AUTO_INCREMENT,
     totalprice DOUBLE,
     user_id INT
    );