package ru.home.webservice1;

import ru.home.webservice1.dao.jdbc.StudentDaoJDBC;
import ru.home.webservice1.utils.Dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        final Dbutils connectionProvider=new Dbutils();
        String query = ("SELECT st.id, st.name, cd.coordinator_name FROM student AS st left join coordinator AS cd on st.coordinator_id = cd.coordinator_id;");
        //Student student=new Student();
        try (
                Connection connection = connectionProvider.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String coordinator_name = rs.getString("coordinator_name");
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
