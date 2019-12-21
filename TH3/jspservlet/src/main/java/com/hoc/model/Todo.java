package com.hoc.model;

import java.time.LocalDate;
import java.util.Objects;

public class Todo {

  private Long id;
  private String title;
  private String username;
  private String description;
  private LocalDate targetDate;
  private boolean status;

  public Todo() {
  }

  public Todo(Long id, String title, String username, String description, LocalDate targetDate, boolean status) {
    this.id = id;
    this.title = title;
    this.username = username;
    this.description = description;
    this.targetDate = targetDate;
    this.status = status;
  }

  public Todo(String title, String username, String description, LocalDate targetDate, boolean isDone) {
    super();
    this.title = title;
    this.username = username;
    this.description = description;
    this.targetDate = targetDate;
    this.status = isDone;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getTargetDate() {
    return targetDate;
  }

  public void setTargetDate(LocalDate targetDate) {
    this.targetDate = targetDate;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", username='" + username + '\'' +
        ", description='" + description + '\'' +
        ", targetDate=" + targetDate +
        ", status=" + status +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Todo todo = (Todo) o;
    return status == todo.status &&
        Objects.equals(id, todo.id) &&
        Objects.equals(title, todo.title) &&
        Objects.equals(username, todo.username) &&
        Objects.equals(description, todo.description) &&
        Objects.equals(targetDate, todo.targetDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, username, description, targetDate, status);
  }
}
