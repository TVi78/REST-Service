package ru.home.webservice1.dao.jdbc;

import ru.home.webservice1.model.Coordinator;

import java.util.List;

public interface CoordinatorDao {
    void addCoordinator (Coordinator coordinator);
    List<Coordinator> getAllCoordinator();
    Coordinator getByIdCoordinator(int id);
    void updateCoordinator (Coordinator coordinator);
    void deleteCoordinator (int id);
}
