package com.hoc.dao;

import com.hoc.model.Todo;

import java.util.List;

public interface TodoDao {
  List<Todo> selectAllTodos(String username);

  Todo selectTodo(long todoId, String username);

  void insertTodo(Todo todo) throws Exception;

  boolean deleteTodo(int id, String username) throws Exception;

  boolean updateTodo(Todo todo, String username) throws Exception;
}