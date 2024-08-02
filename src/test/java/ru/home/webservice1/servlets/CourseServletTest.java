package ru.home.webservice1.servlets;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.home.webservice1.dao.jdbc.CourseDaoJDBC;
import ru.home.webservice1.model.Course;
import ru.home.webservice1.utils.Dbutils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class CourseServletTest {
    private Integer port = 8086;
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    CourseDaoJDBC courseDao;

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
        courseDao = new CourseDaoJDBC(connectionProvider);
        RestAssured.baseURI = "http://localhost:" + port;
    }

    /**
     * Проверим получение данных координаторов через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONGetCoursesServlet
     */
    @Test
    void doGetCourses() {
        courseDao.addCourse(new Course("Math"));
        courseDao.addCourse(new Course("Eng"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/courses?url=" + postgres.getJdbcUrl())
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }

    /**
     * Проверим добавление курса через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONAddCourseServlet
     */
    @Test
    void doAddCourse() {
        given()
                .formParam("name", "Math")
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/addCourse")
                .then()
                .statusCode(200);
    }

    /**
     * Проверим обновление курса через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONUpdateCourseServlet
     */
    @Test
    void doUpdateCourse() {
        courseDao.addCourse(new Course(1, "Math"));
        given()
                .formParam("id", 1)
                .formParam("name", "Math1")
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/updateCourse")
                .then()
                .statusCode(200);
        //  .body("name", is("Alex"));;
    }

    /**
     * Проверим удаление курса через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONDeletCourseServlet
     */
    @Test
    void doDeleteCourse() {
        courseDao.addCourse(new Course(1, "Math"));
        given()
                .formParam("id", 1)
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/deleteCourse")
                .then()
                .statusCode(200);
    }
}
