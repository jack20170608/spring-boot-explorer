CREATE SCHEMA IF NOT EXISTS JACK;

CREATE USER IF NOT EXISTS jack PASSWORD '';

SET SCHEMA JACK;

--CREATE TABLE

create table student(
    id int not null primary key auto_increment,
   name varchar(255) not null,
   passport_number varchar(255) not null
);


--init the testing data
insert into student(name, passport_number)
values ('Ranga','E1234567');

insert into student(name, passport_number)
values ('Ravi','A123234');
