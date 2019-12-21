package com.hoc.controller;

import com.hoc.dao.UserDao;
import com.hoc.dao.UserDaoImpl;
import com.hoc.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register")
public class ServletRegisterController extends HttpServlet {
  private UserDao userDao;

  @Override
  public void init() throws ServletException {
    super.init();
    userDao = new UserDaoImpl();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    final String firstName = request.getParameter("first_name");
    final String lastName = request.getParameter("last_name");
    final String username = request.getParameter("username");
    final String password = request.getParameter("password");
    final User user = new User(
        firstName,
        lastName,
        username,
        password
    );

    try {
      userDao.register(user);
      request.setAttribute("NOTIFICATION", "Register successfully");
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("NOTIFICATION", "Error when registering: " + e.getMessage());
    }

    request.getRequestDispatcher("/register.jsp").forward(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.sendRedirect(request.getContextPath() + "/register.jsp");
  }
}
