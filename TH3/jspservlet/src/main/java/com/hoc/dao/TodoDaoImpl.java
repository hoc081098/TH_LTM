package com.hoc.dao;

import com.hoc.JDBCUtil;
import com.hoc.model.Todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDaoImpl implements TodoDao {

  public TodoDaoImpl() {
  }

  @Override
  public void insertTodo(Todo todo) throws Exception {
    try (
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO todos"
            + "  (title, username, description, target_date,  is_done) VALUES " + " (?, ?, ?, ?, ?);")
    ) {
      preparedStatement.setString(1, todo.getTitle());
      preparedStatement.setString(2, todo.getUsername());
      preparedStatement.setString(3, todo.getDescription());
      preparedStatement.setDate(4, JDBCUtil.sqlDateFromLocalDate(todo.getTargetDate()));
      preparedStatement.setBoolean(5, todo.isStatus());
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public Todo selectTodo(long todoId, String username) {
    try (
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select id, title, username, description, target_date, is_done from todos where id =? and username=?")
    ) {
      preparedStatement.setLong(1, todoId);
      preparedStatement.setString(2, username);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        LocalDate targetDate = rs.getDate("target_date").toLocalDate();
        boolean isDone = rs.getBoolean("is_done");
        return new Todo(id, title, username, description, targetDate, isDone);
      } else {
        return null;
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<Todo> selectAllTodos(String username) {

    List<Todo> todos = new ArrayList<>();

    try (
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from todos where username=?")
    ) {
      preparedStatement.setString(1, username);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        LocalDate targetDate = rs.getDate("target_date").toLocalDate();
        boolean isDone = rs.getBoolean("is_done");
        todos.add(new Todo(id, title, username, description, targetDate, isDone));
      }
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return todos;
  }

  @Override
  public boolean deleteTodo(int id, String username) throws Exception {
    boolean rowDeleted;
    try (Connection connection = JDBCUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement("delete from todos where id = ? and username=?")) {
      statement.setInt(1, id);
      statement.setString(2, username);
      rowDeleted = statement.executeUpdate() > 0;
    }
    return rowDeleted;
  }

  @Override
  public boolean updateTodo(Todo todo, String username) throws Exception {
    boolean rowUpdated;
    try (
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("update todos set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ? and username=?")
    ) {
      statement.setString(1, todo.getTitle());
      statement.setString(2, todo.getUsername());
      statement.setString(3, todo.getDescription());
      statement.setDate(4, JDBCUtil.sqlDateFromLocalDate(todo.getTargetDate()));
      statement.setBoolean(5, todo.isStatus());
      statement.setLong(6, todo.getId());
      statement.setString(7, username);
      rowUpdated = statement.executeUpdate() > 0;
    }
    return rowUpdated;
  }
}