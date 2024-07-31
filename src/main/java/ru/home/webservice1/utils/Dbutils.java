package ru.home.webservice1.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Dbutils {
//    private static String USER = "admin";
//    private static String PASSWORD = "admin";
//    private static String URL = "jdbc:postgresql://localhost:1500/db";

    private final String URL;
    private String USER ;
    private String PASSWORD;

    public Dbutils (String url, String username, String password) {
        this.URL = url;
        this.USER = username;
        this.PASSWORD = password;
    }

    public Dbutils (){
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            URL = properties.getProperty("dbURL");
            PASSWORD = properties.getProperty("dbPASS");
            USER = properties.getProperty("dbUSER");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        //FileInputStream fis;
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
