CREATE SCHEMA IF NOT EXISTS JACK;

CREATE USER IF NOT EXISTS jack PASSWORD '';

SET SCHEMA JACK;

--CREATE TABLE
-- User table have a inner auto increment key generate strategy
CREATE TABLE IF NOT EXISTS USERS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_name varchar(256) NOT NULL,
  sex varchar(10),
  date_of_birth DATE NOT NULL,
  reputation INT NOT NULL,
  enabled BOOLEAN NOT NULL,
  last_login_ts timestamp
);

-- Comment table's key generation strategy outside of db (UUID)
CREATE TABLE IF NOT EXISTS COMMENTS (
  id varchar2(100) PRIMARY KEY,
  user_id INT,
  contents varchar(1000),
  created_time TIMESTAMP NOT NULL,
  favourite_count INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES USERS(id)
);

--Compount key
CREATE TABLE IF NOT EXISTS BOARDING_PASS (
  flight_no varchar(8) NOT NULL,
  seq_no INT NOT NULL,
  passenger VARCHAR(1000),
  seat CHAR(3),
  PRIMARY KEY (flight_no, seq_no)
);

--init the testing data
insert into USERS(user_name, sex, date_of_birth, reputation, enabled, last_login_ts)
values('jack', 'MAN', '1988-08-08', 100, true, '2018-09-18 18:47:46.90');

insert into USERS(user_name, sex, date_of_birth, reputation, enabled, last_login_ts)
values('peter', 'WOMAN', '1999-08-08', 100, true, '2018-09-29 00:11:45.67');
