package com.strong.tea.controller;

import com.strong.tea.database.DatabaseConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class DatabaseInitListener implements ServletContextListener {

    /**
     * Initializes database tables upon application startup by executing an SQL script.
     * This method reads the script from the classpath, establishes a database connection, and executes the script using JDBC.
     * Any SQLExceptions are logged with details to the standard error output. Additionally, it handles potential
     * NullPointerExceptions and IOExceptions that may occur during the process.
     *
     * @param servletContextEvent The ServletContextEvent object representing the event when the ServletContext is initialized.
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sql/create_tables.sql");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            StringBuilder sqlBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
            String sqlScript = sqlBuilder.toString();
            statement.executeUpdate(sqlScript);

        } catch (SQLException e) {
            System.err.println("Error create SQL table: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }
}