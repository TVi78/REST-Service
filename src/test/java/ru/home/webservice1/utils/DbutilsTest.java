package ru.home.webservice1.utils;

import org.junit.jupiter.api.Test;
import ru.home.webservice1.dao.jdbc.StudentDao;
import ru.home.webservice1.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class DbutilsTest {
private Dbutils db;
private StudentDao dao;

public DbutilsTest() {
    this.db = new Dbutils();
}
    @Test
    void getConnection() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"Vas2",1));
        students.add(new Student(2,"Petrov2",2));
        given(dao.getAllStudent()).willReturn(students);
    }
}