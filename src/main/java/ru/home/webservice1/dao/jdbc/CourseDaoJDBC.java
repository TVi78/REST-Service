package ru.home.webservice1.dao.jdbc;

import ru.home.webservice1.model.Course;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoJDBC implements CourseDao{
    private static String INSERT_COURSE = "INSERT INTO course (course_name) VALUES (?);";
    private static String UPDATE_COURSE = "UPDATE course SET course_name = ? WHERE id = ?";
    private static String DELETE_COURSE = "DELETE FROM course WHERE id = ?";
    private static String queryCourse = ("SELECT * FROM course ORDER BY id ASC");
    private static String queryCourseId = ("SELECT * FROM course WHERE id = ?");


    @Override
    public void addCourse(Course cource) {
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COURSE)) {
            preparedStatement.setString(1, cource.getCourse_name());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Course> getAllCourse() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryCourse)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("course_name");
                courses.add(new Course(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courses;
    }

    @Override
    public Course getByIdCourse(int id) {
        Course course = new Course();
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryCourseId)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("course_name");
                course.setId(id);
                course.setCourse_name(name);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return course;
    }

    @Override
    public void updateCourse(Course course) {
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE)) {
            preparedStatement.setString(1,course.getCourse_name());
            preparedStatement.setInt(2, course.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteCourse(int id) {
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
