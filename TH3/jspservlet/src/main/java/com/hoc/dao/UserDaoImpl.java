package com.hoc.dao;

import com.hoc.JDBCUtil;
import com.hoc.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserNameExistsException extends Exception {
  public UserNameExistsException(String message) {
    super(message);
  }
}

class UsernameNotFoundException extends Exception {
  public UsernameNotFoundException(String message) {
    super(message);
  }
}

class WrongPasswordException extends Exception {
  public WrongPasswordException(String message) {
    super(message);
  }
}

public class UserDaoImpl implements UserDao {
  @Override
  public void register(User user) throws Exception {
    try (final Connection connection = JDBCUtil.getConnection()) {
      checkUserNameIsExists(user, connection);
      insert(user, connection);
    }
  }

  @Override
  public User login(String username, String password) throws Exception {
    try (final Connection connection = JDBCUtil.getConnection()) {
      final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username=?");
      preparedStatement.setString(1, username);
      final ResultSet resultSet = preparedStatement.executeQuery();
      if (!resultSet.next()) {
        throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
      }

      final String hashedPassword = resultSet.getString("password");
      if (!BCrypt.checkpw(password, hashedPassword)) {
        throw new WrongPasswordException("Wrong password");
      }

      return new User(
          resultSet.getString("first_name"),
          resultSet.getString("last_name"),
          resultSet.getString("username"),
          resultSet.getString("password")
      );
    }
  }

  private void insert(User user, Connection connection) throws SQLException {
    final PreparedStatement insertStmt = connection
        .prepareStatement("INSERT INTO users(first_name, last_name, username, password) VALUES (?, ?, ?, ?)");
    insertStmt.setString(1, user.getFirstName());
    insertStmt.setString(2, user.getLastName());
    insertStmt.setString(3, user.getUsername());
    final String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
    insertStmt.setString(4, hashedPassword);
    insertStmt.executeUpdate();
  }

  private void checkUserNameIsExists(User user, Connection connection) throws SQLException, UserNameExistsException {
    final PreparedStatement checkExistsStmt = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
    checkExistsStmt.setString(1, user.getUsername());
    final ResultSet resultSet = checkExistsStmt.executeQuery();
    if (resultSet.next()) {
      final int count = resultSet.getInt(1);
      if (count > 0) {
        throw new UserNameExistsException(
            String.format(
                "User with username='%s' already exists",
                user.getUsername()
            )
        );
      }
    } else {
      throw new UserNameExistsException(
          String.format(
              "User with username='%s' already exists",
              user.getUsername()
          )
      );
    }
  }
}
