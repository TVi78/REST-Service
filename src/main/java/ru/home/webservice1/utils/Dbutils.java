package ru.home.webservice1.utils;

import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@NoArgsConstructor
public class Dbutils {
//    private static String USER = "admin";
//    private static String PASSWORD = "admin";
//    private static String URL = "jdbc:postgresql://localhost:1500/db";

    private String URL;
    private String USER ;
    private String PASSWORD;

    public Dbutils (String url, String username, String password) {
        this.URL = url;
        this.USER = username;
        this.PASSWORD = password;
    }

    public Connection getConnection() {
        /**
         * При запуске не через testcontainers
         */
        if (URL==null && USER==null && PASSWORD==null) {
            Properties properties = new Properties();
            FileInputStream fis;
            try {
                /**
                 * При запуске не сервлета считываем проперти, закомментировать при запуске сервлета
                 */
//                fis=new FileInputStream("src/main/webapp/WEB-INF/classes/config.properties");
//                properties.load(fis);
                /**
                 * При запуске сервлета считываем проперти, закомментировать при локальном запуске
                 */
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

                URL = properties.getProperty("dbURL");
                PASSWORD = properties.getProperty("dbPASS");
                USER = properties.getProperty("dbUSER");
            } catch (FileNotFoundException e) {
            //    e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
