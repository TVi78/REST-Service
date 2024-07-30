package ru.home.webservice1.dao.jdbc;

import ru.home.webservice1.model.Course;

import java.util.List;

public interface CourseDao {
    void addCourse(Course cource);

    List<Course> getAllCourse();

    Course getByIdCourse(int id);

    void updateCourse(Course course);

    void deleteCourse(int id);
}
