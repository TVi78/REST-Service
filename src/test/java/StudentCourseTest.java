import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;
import ru.home.webservice1.model.Course;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class StudentCourseTest {
    private Dbutils connectionProvider;
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
    void setUp() {
        /**
         * Задаем параметры соединения
         */
        connectionProvider = new Dbutils(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        /**
         * Создаем таблицы
         */
        try (Connection conn = connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "create table if not exists course ( id serial primary key, course_name varchar(50))"
            );
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

        try (Connection conn = connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "    CREATE TABLE IF NOT EXISTS studentCourse (\n" +
                            "    student_id integer REFERENCES student (id),\n" +
                            "    course_id integer REFERENCES course(id))");
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /**
         * Заполняем таблицы
         */
        try (Connection conn = connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into course (course_name)\n" +
                            "values ('Math'),\n" +
                            "       ('Eng')\n"
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

        try (Connection conn = connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into student (name, coordinator_id)\n" +
                            "values ('Vasilii', 1),\n" +
                            "       ('Petrov', 2)"
            );
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into studentCourse (student_id, course_id)\n" +
                            "values (2, 1),\n" +
                            "       (1, 2),\n" +
                            "       (1, 1)"
            );
            pstmt.execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Проверяем взаимосвязь всех таблиц
     */
    @Test
    void select() {
        String query = ("SELECT st.id, st.name, cours.course_name, cd.coordinator_name FROM student AS st\n" +
                "left join coordinator AS cd on st.coordinator_id = cd.coordinator_id\n" +
                "left join studentCourse AS stcourse ON stcourse.student_id = st.id\n" +
                "left join course AS cours ON cours.id=stcourse.course_id;");
        //Student student=new Student();
        try (
                Connection connection = connectionProvider.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String course_name = rs.getString("course_name");
                String coordinator_name = rs.getString("coordinator_name");
//                int coordinator_id = rs.getInt("coordinator_id");
                System.out.println(id + "," + name + "," + course_name + "," + coordinator_name);
                //courses.add(new Course(id, name));
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}