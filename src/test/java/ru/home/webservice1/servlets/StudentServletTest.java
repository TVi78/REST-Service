package ru.home.webservice1.servlets;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;
import ru.home.webservice1.model.Student;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

class StudentServletTest {
    private Integer port = 8086;
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

        RestAssured.baseURI = "http://localhost:" + port;
        //customerRepository.deleteAll();
    }

    /**
     * Проверим получение данных студента по id через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONGetStudentIDServlet
     */
    @Test
    void doGetStudent() {
        studentDao.addStudent(new Student("Petr", 1));
        studentDao.addStudent(new Student("Misha", 2));


        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/student?id=1&url=" + postgres.getJdbcUrl())
                .then()
                .statusCode(200)
                .body("name", is("Petr"));
    }


    /**
     * Проверим получение данных студентов через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONGetStudentsServlet
     */
    @Test
    void doGetStudents() {
        studentDao.addStudent(new Student("Petr", 1));
        studentDao.addStudent(new Student("Misha", 2));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/students?url=" + postgres.getJdbcUrl())
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }



    /**
     * Проверим добавление студента через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONAddStudentServlet
     */
    @Test
    void doAddStudent() {
        given()
                //     .contentType(ContentType.JSON)
                .formParam("name", "Alex")
                .formParam("coord_id", "1")
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/addStudent")
                .then()
                .statusCode(200);
    }

    /**
     * Проверим обновление студента через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONUpdateStudentServlet
     */
    @Test
    void doUpdateStudent() {
        studentDao.addStudent(new Student(1,"Petr", 1));
        given()
                .formParam("id", 1)
                .formParam("name", "Alex")
                .formParam("coord_id", "1")
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/updateStudent")
                .then()
                .statusCode(200);
              //  .body("name", is("Alex"));;
    }

    /**
     * Проверим удаление студента через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONDeletStudentServlet
     */
    @Test
    void doDeleteStudent() {
        studentDao.addStudent(new Student(1,"Petr", 1));
        given()
                .formParam("id", 1)
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/deleteStudent")
                .then()
                .statusCode(200);
    }
}
