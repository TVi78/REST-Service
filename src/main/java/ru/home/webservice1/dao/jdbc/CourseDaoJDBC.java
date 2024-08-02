package ru.home.webservice1.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.home.webservice1.model.Course;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD операции курса
 *
 * @see Course
 */
@Slf4j
public class CourseDaoJDBC implements CourseDao {
    private static String INSERT_COURSE = "INSERT INTO course (course_name) VALUES (?);";
    private static String UPDATE_COURSE = "UPDATE course SET course_name = ? WHERE id = ?";
    private static String DELETE_COURSE = "DELETE FROM course WHERE id = ?";
    private static String queryCourse = ("SELECT * FROM course ORDER BY id ASC");
    private static String queryCourseId = ("SELECT * FROM course WHERE id = ?");

    private final Dbutils connectionProvider;

    /**
     * Конструктор для получения данных из локальной БД
     */
    public CourseDaoJDBC() {
        connectionProvider = new Dbutils();
        createCoursesTableIfNotExists();
    }

    /**
     * Конструктор для получения данных из БД Testcontainers
     */
    public CourseDaoJDBC(Dbutils connectionProvider) {
        this.connectionProvider = connectionProvider;
        createCoursesTableIfNotExists();
    }

    /**
     * Cоздание таблицы, если ее нет в БД
     */
    private void createCoursesTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "create table if not exists course ( id serial primary key, course_name varchar(50))"
            );
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавляем курс
     *
     * @param course новый курс
     */
    @Override
    public void addCourse(Course course) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COURSE)) {
            preparedStatement.setString(1, course.getCourse_name());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Получаем все курсы
     *
     * @return список курсов
     */
    @Override
    public List<Course> getAllCourse() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = this.connectionProvider.getConnection();
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

    /**
     * Получаем курс по его id
     *
     * @param id идентификатор
     * @return курс
     */
    @Override
    public Course getByIdCourse(int id) {
        Course course = new Course();
        try (Connection connection = this.connectionProvider.getConnection();
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

    /**
     * Обновляем курс, полученный по id
     *
     * @param course курс
     */
    @Override
    public void updateCourse(Course course) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE)) {
            preparedStatement.setString(1, course.getCourse_name());
            preparedStatement.setInt(2, course.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Удаляет курс, взятый по id
     *
     * @param id идентификатор курса
     */
    @Override
    public void deleteCourse(int id) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
