package com.hoc.dao;

import com.hoc.model.User;

public interface UserDao {
  void register(User user) throws Exception;

  User login(String username, String password) throws Exception;
}
