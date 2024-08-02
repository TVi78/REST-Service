package ru.home.webservice1.dao.jdbc;

import ru.home.webservice1.model.Student;

import java.util.List;


public interface StudentDao {
    void addStudent(Student student);

    List<Student> getAllStudent();

    Student getByIdStudent(int id);

    void updateStudent(Student student);

    void deleteStudent(int id);
}
