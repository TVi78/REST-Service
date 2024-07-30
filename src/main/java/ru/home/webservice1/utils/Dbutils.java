package ru.home.webservice1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbutils {
    private static String USER = "admin";
    private static String PASSWORD = "admin";
    private static String URL = "jdbc:postgresql://localhost:1500/db";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Подключение к базе данных выполнено успешно!");
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println("Подключение к базе данных не выполнено");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("Подключение к драйверу не выполнено");
            e.printStackTrace();
        }
        return connection;
    }
}
