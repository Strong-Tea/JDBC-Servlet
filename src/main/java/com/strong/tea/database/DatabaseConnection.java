package com.strong.tea.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Manages the connection to the database using JDBC. This class follows the Singleton pattern to ensure
 * that only one instance of the database connection is created and reused throughout the application.
 * It reads database connection properties from a "db.properties" file located in the classpath.
 * Upon initialization, it loads the database URL, username, and password from the properties file.
 * The class provides a static method, getInstance(), to obtain the singleton instance of the DatabaseConnection.
 * The getConnection() method returns the active database connection.
 * If no instance exists or the existing connection is closed, a new connection is created.
 * Any SQLExceptions or ClassNotFoundExceptions that occur during connection creation are logged to the standard error output.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private final static String url;
    private final static String username;
    private final static String password;

    static {
        Properties properties = new Properties();
        try(InputStream inputStream = DatabaseConnection.class
                .getClassLoader().getResourceAsStream("db.properties")){
            properties.load(inputStream);
        }catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
        url      = properties.getProperty("db.url");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
    }


    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.err.println("Database Connection Creation Failed : " + e.getMessage());
        }
    }


    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }
}
