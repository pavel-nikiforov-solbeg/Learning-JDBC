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
    private static final String INSERT_DATA = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
    private static  final String SHOW_ALL_USERS = "SELECT username, phone FROM users";

    public static void main( String[] args ) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (var statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE_USERS);
            }

            try (var statement2 = connection.createStatement()) {
                statement2.executeUpdate(INSERT_DATA);
            }

            try (var statement3 = connection.createStatement()) {
                var resultSet = statement3.executeQuery(SHOW_ALL_USERS);
                while (resultSet.next()) {
                    log.info("Username: {}", resultSet.getString("username"));
                    log.info("Phone: {}", resultSet.getString("phone"));
                }
            }
        }
    }
}
