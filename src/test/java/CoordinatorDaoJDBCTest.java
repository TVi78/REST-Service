import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.home.webservice1.dao.jdbc.CoordinatorDaoJDBC;
import ru.home.webservice1.model.Coordinator;
import ru.home.webservice1.utils.Dbutils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatorDaoJDBCTest {
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
    void setUp(){
        Dbutils connectionProvider = new Dbutils(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        coordinatorDao = new CoordinatorDaoJDBC(connectionProvider);
    }

    @Test
    void shouldGetAllCourses() {
        coordinatorDao.addCoordinator(new Coordinator("Petr"));
        List<Coordinator> coordinators = coordinatorDao.getAllCoordinator();
        assertEquals(1, coordinators.size());
    }

    @Test
    void shouldAddCourse() {
        List<Coordinator> coordinators = new ArrayList<>();
        Coordinator coordinator = new Coordinator(1,"Mari");
        coordinators.add(coordinator);
        coordinatorDao.addCoordinator(coordinator);
        assertEquals(coordinators, coordinatorDao.getAllCoordinator());
    }

    @Test
    void shouldGetByIdCourse() {
        Coordinator coordinator = new Coordinator(1,"Mari");
        coordinatorDao.addCoordinator(coordinator);
        assertEquals(coordinator, coordinatorDao.getByIdCoordinator(1));
    }

    @Test
    void updateCourse() {
        Coordinator coordinator = new Coordinator(1,"Mari1");
        coordinatorDao.addCoordinator(new Coordinator(1,"Mari"));
        coordinatorDao.updateCoordinator(new Coordinator(1,"Mari1"));
        assertEquals(coordinator, coordinatorDao.getByIdCoordinator(1));
    }

    @Test
    void deleteCourse() {
        coordinatorDao.addCoordinator(new Coordinator(1,"Mari"));;
        coordinatorDao.deleteCoordinator(1);
    }
}
