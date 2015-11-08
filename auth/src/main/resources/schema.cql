CREATE KEYSPACE authtest
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE authtest;

CREATE TABLE users (
  username VARCHAR,
  password VARCHAR,
  enabled BOOLEAN,
  roles LIST<VARCHAR>,
  PRIMARY KEY (username)
);

INSERT INTO USERS (username, password, enabled, roles)
VALUES ('a','123',true,['admin']);