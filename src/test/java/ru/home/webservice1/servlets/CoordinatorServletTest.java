package ru.home.webservice1.servlets;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.home.webservice1.dao.jdbc.CoordinatorDaoJDBC;
import ru.home.webservice1.model.Coordinator;
import ru.home.webservice1.utils.Dbutils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class CoordinatorServletTest {
    private Integer port = 8086;
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    CoordinatorDaoJDBC coordinatorDao;

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
        coordinatorDao = new CoordinatorDaoJDBC(connectionProvider);
        RestAssured.baseURI = "http://localhost:" + port;
    }

    /**
     * Проверим получение данных координаторов через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONGetCoordinatorsServlet
     */
    @Test
    void doGetCoordinators() {
        coordinatorDao.addCoordinator(new Coordinator("Masha"));
        coordinatorDao.addCoordinator(new Coordinator("Misha"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/coordinators?url=" + postgres.getJdbcUrl())
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }

    /**
     * Проверим добавление координатора через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONAddCoordinatorServlet
     */
    @Test
    void doAddCoordinator() {
        given()
                .formParam("name", "Alex")
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/addCoordinator")
                .then()
                .statusCode(200);
    }

    /**
     * Проверим обновление координатора через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONUpdateCoordinatorServlet
     */
    @Test
    void doUpdateCoordinator() {
        coordinatorDao.addCoordinator(new Coordinator(1,"Masha"));
        given()
                .formParam("id", 1)
                .formParam("name", "Math1")
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/updateCoordinator")
                .then()
                .statusCode(200);
    }

    /**
     * Проверим удаление координатора через сервлет
     *
     * @see ru.home.webservice1.servlets.json.JSONDeletCoordinatorServlet
     */
    @Test
    void doDeleteCoordinator() {
        coordinatorDao.addCoordinator(new Coordinator(1,"Masha"));
        given()
                .formParam("id", 1)
                .queryParam("url", postgres.getJdbcUrl())
                .when()
                .post("/api/deleteCoordinator")
                .then()
                .statusCode(200);
    }
}
