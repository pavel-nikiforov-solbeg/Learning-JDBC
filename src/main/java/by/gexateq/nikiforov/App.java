package by.gexateq.nikiforov;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class App 
{
    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
    private static final String INSERT_DATA = "INSERT INTO users (username, phone) VALUES (?,?)";
    private static final String SHOW_ALL_USERS = "SELECT username, phone FROM users";
    private static final String DELETE_MARIA = "DELETE FROM users WHERE username = ?";

    public static void main( String[] args ) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (var statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE_USERS);
            }

            try (var statement2 = connection.prepareStatement(INSERT_DATA)) {
                statement2.setString(1,"Tommy");
                statement2.setString(2,"375293456789");
                statement2.executeUpdate();

                statement2.setString(1,"Maria");
                statement2.setString(2,"375251234567");
                statement2.executeUpdate();
            }

            try (var statement3 = connection.createStatement()) {
                var resultSet = statement3.executeQuery(SHOW_ALL_USERS);
                while (resultSet.next()) {
                    log.info("Username: {}", resultSet.getString("username"));
                    log.info("Phone: {}", resultSet.getString("phone"));
                }
            }

            try(var statement4 = connection.prepareStatement(DELETE_MARIA)){
                log.info("Delete Maria from DB");
                statement4.setString(1, "Maria");
                statement4.executeUpdate();
            }

            try (var statement5 = connection.createStatement()) {
                var resultSet = statement5.executeQuery(SHOW_ALL_USERS);
                while (resultSet.next()) {
                    log.info("Username: {}", resultSet.getString("username"));
                    log.info("Phone: {}", resultSet.getString("phone"));
                }
            }
        }
    }
}
