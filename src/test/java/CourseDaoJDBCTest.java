import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.home.webservice1.dao.jdbc.CourseDaoJDBC;
import ru.home.webservice1.model.Course;
import ru.home.webservice1.utils.Dbutils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseDaoJDBCTest {
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
    void setUp(){
        Dbutils connectionProvider = new Dbutils(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        courseDao = new CourseDaoJDBC(connectionProvider);
    }

    @Test
    void shouldGetAllCourses() {
        courseDao.addCourse(new Course("Rus"));
        List<Course> courses = courseDao.getAllCourse();
        assertEquals(1, courses.size());
    }

    @Test
    void shouldAddCourse() {
        List<Course> courses = new ArrayList<>();
        Course course = new Course(1,"Rus");
        courses.add( course);
        courseDao.addCourse(course);
        assertEquals(courses, courseDao.getAllCourse());
    }

    @Test
    void shouldGetByIdCourse() {
        Course course = new Course(1, "Math");
        courseDao.addCourse(course);
        assertEquals(course, courseDao.getByIdCourse(1));
    }

    @Test
    void updateCourse() {
        Course course = new Course(1, "Math1");
        courseDao.addCourse(new Course(1, "Math"));
        courseDao.updateCourse(new Course(1, "Math1"));
        assertEquals(course, courseDao.getByIdCourse(1));
    }

    @Test
    void deleteCourse() {
        courseDao.addCourse(new Course(1, "Math"));
        courseDao.deleteCourse(1);
    }
}
