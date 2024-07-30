CREATE SCHEMA IF NOT EXISTS postgres;
USE postgres;

DROP table IF EXISTS student;
CREATE TABLE student (
   id BIGINT primary key AUTO_INCREMENT,
   name varchar(80),
   surname varchar(100),
   course_name varchar(100)
);

insert into student (name, surname, course_name)
values ('Vasilii', 'Petrov', 'history')
;

