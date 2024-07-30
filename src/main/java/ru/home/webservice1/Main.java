package ru.home.webservice1;

import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String query = ("SELECT s.id, s.name, c.coordinator_name FROM student AS s " +
                "LEFT JOIN coordinator AS с ON s.coordinator_id=c.coordinator_id");
        //Student student=new Student();
        try (
                Connection connection = Dbutils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("s.id");
                String name = rs.getString("s.name");
                String coordinator_name = rs.getString("c.coordinator_name");
//                int coordinator_id = rs.getInt("coordinator_id");
                System.out.println(id+","+name+","+coordinator_name);
                //courses.add(new Course(id, name));
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
