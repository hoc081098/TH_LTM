package com.hoc.controller;

import com.hoc.dao.TodoDao;
import com.hoc.dao.TodoDaoImpl;
import com.hoc.model.Todo;
import com.hoc.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

class UnauthenticatedException extends Exception {
  public UnauthenticatedException(String message) {
    super(message);
  }
}

@WebServlet(value = {"/"})
public class ServletTodoListController extends HttpServlet {
  private TodoDao todoDAO;

  @Override
  public void init() throws ServletException {
    super.init();
    this.todoDAO = new TodoDaoImpl();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    System.out.println("[POST] " + request.getServletPath());
    process(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    System.out.println("[GET] " + request.getServletPath());
    process(request, response);
  }

  private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    final Object currentUser = request.getSession().getAttribute("current_user");
    System.out.println("currentUser=" + currentUser);

    if (currentUser == null) {
      response.sendRedirect(request.getContextPath() + "/login");
      return;
    }

    try {
      String action = request.getServletPath();

      switch (action) {
        case "/new":
          showNewForm(request, response);
          break;
        case "/insert":
          insertTodo(request, response);
          break;
        case "/delete":
          deleteTodo(request, response);
          break;
        case "/edit":
          showEditForm(request, response);
          break;
        case "/update":
          updateTodo(request, response);
          break;
        default:
          listTodo(request, response);
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (e instanceof UnauthenticatedException) {
        response.sendRedirect("/login");
      } else {
        request.setAttribute("NOTIFICATION", "Error: " + e.getMessage());
        request.getRequestDispatcher("/todo-list.jsp").forward(request, response);
      }
    }
  }

  private void listTodo(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException, UnauthenticatedException {
    List<Todo> listTodo = todoDAO.selectAllTodos(getCurrentUserName(request));
    request.setAttribute("listTodo", listTodo);
    RequestDispatcher dispatcher = request.getRequestDispatcher("/todo-list.jsp");
    dispatcher.forward(request, response);
  }

  private void showNewForm(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher("/todo-form.jsp");
    dispatcher.forward(request, response);
  }

  private void showEditForm(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException, UnauthenticatedException {
    int id = Integer.parseInt(request.getParameter("id"));
    Todo existingTodo = todoDAO.selectTodo(id, getCurrentUserName(request));
    RequestDispatcher dispatcher = request.getRequestDispatcher("/todo-form.jsp");
    request.setAttribute("todo", existingTodo);
    dispatcher.forward(request, response);
  }

  private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String title = request.getParameter("title");
    String username = getCurrentUserName(request);
    String description = request.getParameter("description");

    boolean isDone = Boolean.parseBoolean(request.getParameter("isDone"));
    Todo newTodo = new Todo(title, username, description, LocalDate.now(), isDone);
    todoDAO.insertTodo(newTodo);
    response.sendRedirect("list");
  }

  private String getCurrentUserName(HttpServletRequest request) throws UnauthenticatedException {
    final Object currentUser = request.getSession().getAttribute("current_user");
    if (currentUser == null) {
      throw new UnauthenticatedException("Login first!!!");
    }
    return ((User) currentUser).getUsername();
  }

  private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Long id = Long.parseLong(request.getParameter("id"));

    String title = request.getParameter("title");
    String username = getCurrentUserName(request);
    String description = request.getParameter("description");
    LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));

    boolean isDone = Boolean.parseBoolean(request.getParameter("isDone"));
    Todo updateTodo = new Todo(id, title, username, description, targetDate, isDone);

    todoDAO.updateTodo(updateTodo, getCurrentUserName(request));
    response.sendRedirect("list");
  }

  private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int id = Integer.parseInt(request.getParameter("id"));
    todoDAO.deleteTodo(id, getCurrentUserName(request));
    response.sendRedirect("list");
  }
}
