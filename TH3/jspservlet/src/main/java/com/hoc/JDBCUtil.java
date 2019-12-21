package com.hoc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class JDBCUtil {
  private static final String DB_URL = "jdbc:mysql://localhost:3306/todo_ltm?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "";

  private JDBCUtil() {
  }

  public static Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
  }

  public static java.sql.Date sqlDateFromLocalDate(LocalDate localDate) {
    return java.sql.Date.valueOf(localDate);
  }

  public static LocalDate localDateFromSql(Date date) {
    return date.toLocalDate();
  }
}
