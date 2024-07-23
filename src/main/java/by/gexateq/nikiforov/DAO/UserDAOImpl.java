package by.gexateq.nikiforov.DAO;

import by.gexateq.nikiforov.model.User;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@AllArgsConstructor
public class UserDAOImpl implements UserDAO{
    private Connection connection;

    public void save(User user) throws SQLException {
        var insertUserQuery = "INSERT INTO users (username, phone) VALUES (?, ?)";
        try (var preparedStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public Optional<User> findById(Long id) throws SQLException {
        var selectUserByIdQuery = "SELECT id, username, phone FROM users WHERE id = ?";
        try (var preparedStatement = connection.prepareStatement(selectUserByIdQuery)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToUser(resultSet));
            }
        }
        return Optional.empty();
    }

    public void deleteById(Long id) throws SQLException{
        var deleteUserByIdQuery = "DELETE FROM users WHERE id = ?";
        try(var preparedStatement = connection.prepareStatement(deleteUserByIdQuery)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> usersList = new ArrayList<>();
        String selecatAllUsers = "SELECT * FROM users";
        try(var statement = connection.createStatement()){
            var resultSet = statement.executeQuery(selecatAllUsers);
            while(resultSet.next()){
                usersList.add(mapToUser(resultSet));
            }
        }
        return usersList;
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .phone(resultSet.getString("phone"))
                .build();
    }
}
