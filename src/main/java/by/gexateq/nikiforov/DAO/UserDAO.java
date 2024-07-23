package by.gexateq.nikiforov.DAO;

import by.gexateq.nikiforov.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface UserDAO {

    void save(User user) throws SQLException;

    Optional<User> findById(Long id) throws SQLException;

    void deleteById(Long id) throws SQLException;

    List<User> findAll() throws SQLException;
}
