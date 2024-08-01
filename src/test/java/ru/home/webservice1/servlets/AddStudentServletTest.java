package ru.home.webservice1.servlets;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentServletTest {
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

    @Test
    void doGet() {
    }

    @Test
    void doPost() {
    }
}