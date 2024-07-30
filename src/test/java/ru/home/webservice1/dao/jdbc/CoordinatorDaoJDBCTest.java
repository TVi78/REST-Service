package ru.home.webservice1.dao.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.home.webservice1.model.Coordinator;
import ru.home.webservice1.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatorDaoJDBCTest {
    private CoordinatorDao coordinatorDao;

    @BeforeEach
    void setUp() throws Exception {
        this.coordinatorDao = new CoordinatorDaoJDBC();
    }

    @Test
    void addCoordinator() {
        Coordinator coordinator = new Coordinator("Petr");
        coordinatorDao.addCoordinator(coordinator);
    }

    @Test
    void getAllCoordinator() {
        List<Coordinator> coordinators = new ArrayList<>();
        coordinators.add(new Coordinator(1,"Aleksei"));
        coordinators.add(new Coordinator(2,"Maria"));
        assertEquals(coordinators, coordinatorDao.getAllCoordinator());
    }

    @Test
    void getByIdCoordinator() {
        Coordinator coordinator = new Coordinator(1, "Aleksei");
        assertEquals(coordinator, coordinatorDao.getByIdCoordinator(1));
    }

    @Test
    void updateCoordinator() {
        Coordinator coordinator = new Coordinator(1, "Aleksei1");
        coordinatorDao.updateCoordinator(coordinator);
    }

    @Test
    void deleteCoordinator() {
        coordinatorDao.deleteCoordinator(4);
    }
}