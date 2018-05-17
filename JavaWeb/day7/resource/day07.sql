use day07;

CREATE TABLE category(
  cid VARCHAR(20) PRIMARY KEY,
  cname VARCHAR(20)
);

INSERT INTO category VALUES('c001', '电器');
INSERT INTO category VALUES('c002', '照饰');
INSERT INTO category VALUES('c003', '化妆品');
INSERT INTO category VALUES('c004', '书籍');