
DROP table IF EXISTS course;
CREATE TABLE course (
                        id serial primary key,
                        course_name varchar(50)
);

DROP table IF EXISTS coordinator;
CREATE TABLE coordinator (
                             coordinator_id serial primary key,
                             coordinator_name varchar(80)
);


DROP table IF EXISTS student;
CREATE TABLE IF NOT EXISTS student (
                                       id serial primary key ,
                                       name varchar(80),
    coordinator_id integer REFERENCES coordinator (coordinator_id)
    );

DROP table IF EXISTS student_course;
CREATE TABLE IF NOT EXISTS student_course (
                                              student_id integer REFERENCES student (id),
    course_id integer REFERENCES course(id)
    );


insert into student (name, coordinator_id)
values ('Vasilii', 1),
       ('Petrov', 2)
;

insert into coordinator (coordinator_name)
values ('Aleksei'),
       ('Maria')
;

insert into course (course_name)
values ('Math'),
       ('Eng')
;

insert into student_course (student_id, course_id)
values (2, 1),
       (1, 2),
       (1, 1)
;

INSERT INTO student(name, coordinator_id) VALUES('Popov',2);

SELECT st.id, st.name, cd.coordinator_name FROM student AS st left join coordinator AS cd on st.coordinator_id = cd.coordinator_id;

SELECT sr.id, sr.name, cw.coordinator_name FROM student AS sr LEFT JOIN coordinator AS cw ON sr.coordinator_id=cw.coordinator_id;