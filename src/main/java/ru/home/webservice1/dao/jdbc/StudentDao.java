package ru.home.webservice1.dao.jdbc;

import ru.home.webservice1.model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StudentDao {
    void addStudent(Student student);

    List<Student> getAllStudent();

    Student getByIdStudent(int id);

    void updateStudent(Student student);

    void deleteStudent(int id);
}
