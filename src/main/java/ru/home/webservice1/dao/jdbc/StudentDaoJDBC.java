package ru.home.webservice1.dao.jdbc;

import ru.home.webservice1.model.Course;
import ru.home.webservice1.model.Student;
import ru.home.webservice1.utils.Dbutils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoJDBC implements StudentDao {

    private static String INSERT_STUDENT = "INSERT INTO student (name, coordinator_id) VALUES (?,?);";
    private static String UPDATE_STUDENT = "UPDATE student SET name = ?, coordinator_id=? WHERE id = ?";
    private static String DELETE_STUDENT = "DELETE FROM student WHERE id = ?";
    private static String query = ("SELECT * FROM student ORDER BY id ASC");
    private static String queryId = ("SELECT * FROM student WHERE id = ?");

    @Override
    public void addStudent(Student student) {
        try (Connection connection = Dbutils.getConnection();
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

    @Override
    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                students.add(parseStudentsFromResultSet(rs));
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                int coordinator_id = rs.getInt("coordinator_id");
//
//                students.add(new Student(id, name, coordinator_id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }

    @Override
    public Student getByIdStudent(int id) {
        Student student = new Student();
        try (Connection connection = Dbutils.getConnection();
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

    @Override
    public void updateStudent(Student student) {
        try (Connection connection = Dbutils.getConnection();
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

    @Override
    public void deleteStudent(int student_id) {
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT)) {

            preparedStatement.setInt(1, student_id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Student parseStudentsFromResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setCoordinator_id(rs.getInt("coordinator_id"));
        return student;
    }

    public void qw() {
        query = ("SELECT s.id, s.name, c.coordinator_name FROM student AS s " +
                "LEFT JOIN coordinator AS с ON s.coordinator_id=c.coordinator_id");
        //Student student=new Student();
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("s.id");
                String name = rs.getString("s.name");
                String coordinator_name = rs.getString("c.coordinator_name");
//                int coordinator_id = rs.getInt("coordinator_id");
                System.out.println(id+","+name+","+coordinator_name);
                //courses.add(new Course(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
