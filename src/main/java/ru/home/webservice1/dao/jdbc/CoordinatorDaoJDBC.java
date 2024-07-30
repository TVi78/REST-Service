package ru.home.webservice1.dao.jdbc;

import ru.home.webservice1.model.Coordinator;
import ru.home.webservice1.model.Student;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoordinatorDaoJDBC implements CoordinatorDao {
    private static String INSERT_COORDINATOR = "INSERT INTO coordinator (coordinator_name) VALUES (?);";
    private static String UPDATE_COORDINATOR = "UPDATE coordinator SET coordinator_name = ? WHERE coordinator_id = ?";
    private static String DELETE_COORDINATOR = "DELETE FROM coordinator WHERE coordinator_id = ?";
    private static String queryCoord = ("SELECT * FROM coordinator ORDER BY coordinator_id ASC");
    private static String queryCoordId = ("SELECT * FROM coordinator WHERE coordinator_id = ?");

    @Override
    public void addCoordinator(Coordinator coordinator) {
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COORDINATOR)) {
            preparedStatement.setString(1, coordinator.getCoordinator_name());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Coordinator> getAllCoordinator() {
        List<Coordinator> coordinators = new ArrayList<>();
        try (Connection connection = Dbutils.getConnection();
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

    @Override
    public Coordinator getByIdCoordinator(int id) {
        Coordinator coordinator = new Coordinator();
        try (Connection connection = Dbutils.getConnection();
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

    @Override
    public void updateCoordinator(Coordinator coordinator) {
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COORDINATOR)) {
            preparedStatement.setString(1,coordinator.getCoordinator_name());
            preparedStatement.setInt(2, coordinator.getCoordinator_id());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteCoordinator(int id) {
        try (Connection connection = Dbutils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COORDINATOR)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
