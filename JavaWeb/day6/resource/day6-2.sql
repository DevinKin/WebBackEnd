    CREATE TABLE products(
     id INT PRIMARY KEY auto_increment,
     name VARCHAR(20),
     price DOUBLE
    );

    CREATE TABLE orderitem(
     oid INT,
     pid INT
    );

    ALTER TABLE orderitem ADD FOREIGN KEY(oid) REFERENCES orders(id);
    ALTER TABLE orderitem ADD FOREIGN KEY(pid) REFERENCES products(id);
