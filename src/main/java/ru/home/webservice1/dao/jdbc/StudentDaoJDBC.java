package ru.home.webservice1.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.home.webservice1.model.Student;
import ru.home.webservice1.utils.Dbutils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD операции студента
 *
 * @see Student
 */
@Slf4j
public class StudentDaoJDBC implements StudentDao {

    private static String INSERT_STUDENT = "INSERT INTO student (name, coordinator_id) VALUES (?,?);";
    private static String UPDATE_STUDENT = "UPDATE student SET name = ?, coordinator_id=? WHERE id = ?";
    private static String DELETE_STUDENT = "DELETE FROM student WHERE id = ?";
    private static String query = ("SELECT * FROM student ORDER BY id ASC");
    private static String queryId = ("SELECT * FROM student WHERE id = ?");

    private final Dbutils connectionProvider;

    /**
     * Конструктор для получения данных из локальной БД
     */
    public StudentDaoJDBC() {
        connectionProvider = new Dbutils();
        createStudentTableIfNotExists();
    }

    /**
     * Конструктор для получения данных из БД Testcontainers
     */
    public StudentDaoJDBC(Dbutils connectionProvider) {
        this.connectionProvider = connectionProvider;
        createStudentTableIfNotExists();
    }

    /**
     * Cоздание таблицы, если ее нет в БД
     */
    private void createStudentTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS student (\n" +
                            "  id serial primary key ,\n" +
                            "  name varchar(80),\n" +
                            "  coordinator_id integer REFERENCES coordinator (coordinator_id)\n" +
                            ")"
            );
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавление студента
     *
     * @param student студент
     */
    @Override
    public void addStudent(Student student) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getCoordinator_id());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Получить всех студентов
     *
     * @return список студентов
     */
    @Override
    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                students.add(parseStudentsFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }

    /**
     * Получить студента по id
     *
     * @param id идентификатор студента
     * @return студента по указанному id
     */
    @Override
    public Student getByIdStudent(int id) {
        Student student = new Student();
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryId)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int coordinator_id = rs.getInt("coordinator_id");
                student.setId(id);
                student.setName(name);
                student.setCoordinator_id(coordinator_id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return student;
    }

    /**
     * Обновить данные студента, взятого по id
     *
     * @param student студент
     */
    @Override
    public void updateStudent(Student student) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getCoordinator_id());
            preparedStatement.setInt(3, student.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Удаляет студента, взятого по id
     *
     * @param student_id идентификатор студента
     */
    @Override
    public void deleteStudent(int student_id) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT)) {

            preparedStatement.setInt(1, student_id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Считывание результатов, полученных из БД
     *
     * @param rs результаты, полученные из БД
     * @return студента
     */
    private Student parseStudentsFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setCoordinator_id(rs.getInt("coordinator_id"));
        return student;
    }
}
