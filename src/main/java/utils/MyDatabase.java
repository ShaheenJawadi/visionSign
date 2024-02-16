package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {


    private final String URL = "jdbc:mysql://localhost:3306/3a9";
    private final String USER = "root";
    private final String PASS = "";
    private Connection connection;
    private static MyDatabase instance;

    private MyDatabase(){
        try {
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyDatabase getInstance() {
        if(instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
