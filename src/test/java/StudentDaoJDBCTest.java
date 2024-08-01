import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;
import ru.home.webservice1.model.Student;
import ru.home.webservice1.utils.Dbutils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDaoJDBCTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    StudentDaoJDBC studentDao;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp(){
        Dbutils connectionProvider = new Dbutils(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        try (Connection conn = connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS coordinator (\n" +
                            "   coordinator_id serial primary key,\n" +
                            "   coordinator_name varchar(80)\n" +
                            ");"
            );
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into coordinator (coordinator_name)\n" +
                            "values ('Aleksei'),\n" +
                            "('Maria')\n"
            );
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        studentDao = new StudentDaoJDBC(connectionProvider);
    }

    @Test
    void shouldGetAllCourses() {
        studentDao.addStudent(new Student("Mari", 1));
        List<Student> students = studentDao.getAllStudent();
        assertEquals(1, students.size());
    }

    @Test
    void shouldAddCourse() {
        List<Student> students  = new ArrayList<>();
        Student student = new Student(1,"Mari", 1);
        students.add( student);
        studentDao.addStudent(student);
        assertEquals(students, studentDao.getAllStudent());
    }

    @Test
    void shouldGetByIdCourse() {
        Student student = new Student(1,"Mari", 1);
        studentDao.addStudent(student);
        assertEquals(student, studentDao.getByIdStudent(1));
    }

    @Test
    void updateCourse() {
        Student student = new Student(1,"Mari", 1);
        studentDao.addStudent(student);;
        studentDao.updateStudent(new Student(1,"Mari1", 2));
        assertEquals(new Student(1, "Mari1", 2), studentDao.getByIdStudent(1));
    }

    @Test
    void deleteCourse() {
        studentDao.addStudent(new Student("Mari", 1));
        studentDao.deleteStudent(1);
    }
}
