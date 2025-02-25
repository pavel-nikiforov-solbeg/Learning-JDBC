package by.gexateq.nikiforov;

import by.gexateq.nikiforov.DAO.UserDAO;
import by.gexateq.nikiforov.DAO.UserDAOImpl;
import by.gexateq.nikiforov.model.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class AppWithDAO {

    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "username VARCHAR(255), " +
                    "phone VARCHAR(255))";

    public static void main( String[] args ) throws SQLException
    {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (var statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE_USERS);
            } catch (SQLException e){
                throw new SQLException("Statement not created");
            }

            UserDAO userDAO = new UserDAOImpl(connection);

            User user = User.builder().username("Paul").phone("375291234567").build();
            userDAO.save(user);

            User foundUser = userDAO.findById(user.getId()).get();
            log.info("User name {}", foundUser.getUsername());
            log.info("Phone number {}", foundUser.getPhone());

            userDAO.deleteById(user.getId());

            User user1 = User.builder().username("Joanne").phone("375299876543").build();
            userDAO.save(user1);

            log.info(userDAO.findAll().toString());

        }catch (SQLException e){
            log.error("SQL connection failed");
            e.printStackTrace();
        }
    }
}
