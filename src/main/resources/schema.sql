DROP TABLE IF EXISTS operation;
DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS account (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  balance float
);

CREATE TABLE IF NOT EXISTS operation (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  operation_type varchar(100),
  operation_date TIMESTAMP,
  amount float,
  account_id BIGINT,
  balance float,
  foreign key (account_id) references account(id) ON DELETE CASCADE
);
