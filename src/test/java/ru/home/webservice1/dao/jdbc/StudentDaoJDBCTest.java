package ru.home.webservice1.dao.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.home.webservice1.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoJDBCTest {
    private StudentDao studentDao;


    @BeforeEach
    void setUp() throws Exception {
        this.studentDao = new StudentDaoJDBC();
    }

    @org.junit.jupiter.api.Test
    void addStudent() {
        Student student = new Student("Mar1", 1);
        studentDao.addStudent(student);

    }

    @org.junit.jupiter.api.Test
    void getAllStudent() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"Vasilii",1));
        students.add(new Student(2,"Petrov",2));
        assertEquals(students, studentDao.getAllStudent());
    }

    @org.junit.jupiter.api.Test
    void notGetAllStudent() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"Vas",1));
        students.add(new Student(2,"Petrov",2));
        assertNotEquals(students, studentDao.getAllStudent());
    }

    @org.junit.jupiter.api.Test
    void getByIdStudent() {
        Student student = new Student(2,"Petrov",2);
        assertEquals(student, studentDao.getByIdStudent(2));

    }

    @org.junit.jupiter.api.Test
    void updateStudent() {
        Student student = new Student(2, "Petrov2", 1);
        studentDao.updateStudent(student);
    }

    @org.junit.jupiter.api.Test
    void deleteStudent() {
      studentDao.deleteStudent(23);
    }

}