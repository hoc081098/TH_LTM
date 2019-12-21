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

@WebServlet(value = "/login")
public class ServletLoginController extends HttpServlet {
  private UserDao userDao;

  @Override
  public void init() throws ServletException {
    super.init();
    userDao = new UserDaoImpl();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    final String username = request.getParameter("username");
    final String password = request.getParameter("password");

    try {
      final User user = userDao.login(username, password);
      request.getSession().setAttribute("current_user", user);
      response.sendRedirect(request.getContextPath() + "/");
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("NOTIFICATION", "Error when logging in: " + e.getMessage());
      request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.sendRedirect(request.getContextPath() + "/login.jsp");
  }
}
