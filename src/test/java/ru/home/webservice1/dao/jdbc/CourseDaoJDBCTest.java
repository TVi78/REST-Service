package ru.home.webservice1.dao.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.home.webservice1.model.Coordinator;
import ru.home.webservice1.model.Course;
import ru.home.webservice1.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDaoJDBCTest {
    private CourseDao courseDao;

    @BeforeEach
    void setUp() throws Exception {
        this.courseDao = new CourseDaoJDBC();
    }

    @Test
    void addCourse() {
        Course course = new Course(3, "Math");
        courseDao.addCourse(course);
    }

    @Test
    void getAllCourse() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Math1"));
        courses.add(new Course(2, "Eng"));
        assertEquals(courses, courseDao.getAllCourse());
    }

    @org.junit.jupiter.api.Test
    void notGetAllCourse() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Math1"));
        courses.add(new Course(2, "Eng"));
        assertNotEquals(courses, courseDao.getAllCourse());
    }

    @Test
    void getByIdCourse() {
        Course course = new Course(1, "Math1");
        assertEquals(course, courseDao.getByIdCourse(1));
    }

    @Test
    void updateCourse() {
        Course course = new Course(1, "Math1");
        courseDao.updateCourse(course);
    }

    @Test
    void deleteCourse() {
        courseDao.deleteCourse(3);
    }
}