package ru.home.webservice1.dao.jdbc;

import lombok.extern.slf4j.Slf4j;
import ru.home.webservice1.model.Coordinator;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD операции координатора
 *
 * @see Coordinator
 */
@Slf4j
public class CoordinatorDaoJDBC implements CoordinatorDao {
    private static String INSERT_COORDINATOR = "INSERT INTO coordinator (coordinator_name) VALUES (?);";
    private static String UPDATE_COORDINATOR = "UPDATE coordinator SET coordinator_name = ? WHERE coordinator_id = ?";
    private static String DELETE_COORDINATOR = "DELETE FROM coordinator WHERE coordinator_id = ?";
    private static String queryCoord = ("SELECT * FROM coordinator ORDER BY coordinator_id ASC");
    private static String queryCoordId = ("SELECT * FROM coordinator WHERE coordinator_id = ?");

    private Dbutils connectionProvider;

    /**
     * Конструктор для получения данных из локальной БД
     */
    public CoordinatorDaoJDBC() {
        connectionProvider = new Dbutils();
    }

    /**
     * Конструктор для получения данных из БД Testcontainers
     */
    public CoordinatorDaoJDBC(Dbutils connectionProvider) {
        this.connectionProvider = connectionProvider;
        createCoordinatorTableIfNotExists();
    }

    /**
     * Cоздание таблицы, если ее нет в БД
     */
    private void createCoordinatorTableIfNotExists() {
        try (Connection conn = this.connectionProvider.getConnection()) {
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
    }

    /**
     * Добавляем координатора
     *
     * @param coordinator
     * @see Coordinator
     */
    @Override
    public void addCoordinator(Coordinator coordinator) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COORDINATOR)) {
            preparedStatement.setString(1, coordinator.getCoordinator_name());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Получаем всех координаторов
     *
     * @return список координаторов
     */
    @Override
    public List<Coordinator> getAllCoordinator() {
        List<Coordinator> coordinators = new ArrayList<>();
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryCoord)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("coordinator_id");
                String name = rs.getString("coordinator_name");
                coordinators.add(new Coordinator(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return coordinators;
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param id идентификатор координатора
     */
    @Override
    public Coordinator getByIdCoordinator(int id) {
        Coordinator coordinator = new Coordinator();
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryCoordId)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("coordinator_name");
                coordinator.setCoordinator_id(id);
                coordinator.setCoordinator_name(name);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return coordinator;
    }

    /**
     * Обновляем координатора, полученного по id
     *
     * @param coordinator координатор
     */
    @Override
    public void updateCoordinator(Coordinator coordinator) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COORDINATOR)) {
            preparedStatement.setString(1, coordinator.getCoordinator_name());
            preparedStatement.setInt(2, coordinator.getCoordinator_id());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Удаляет координатора, полученного по id
     *
     * @param id идентификатор координатора
     */
    @Override
    public void deleteCoordinator(int id) {
        try (Connection connection = this.connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COORDINATOR)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
