<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.hoc.model.Todo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Todo-list</title>
<%--    <link rel="stylesheet" href="./vendor/bootstrap-4.4.1-dist/css/bootstrap.css">--%>
<%--    <link rel="stylesheet" href="./vendor/bootstrap-4.4.1-dist/css/bootstrap-grid.css">--%>
<%--    <link rel="stylesheet" href="./vendor/bootstrap-4.4.1-dist/css/bootstrap-reboot.css">--%>
<%--    <script src="./vendor/bootstrap-4.4.1-dist/js/bootstrap.js"></script>--%>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <style>
        body {
            padding: 80px 0;
        }
    </style>
</head>
<body>

<jsp:include page="./common/header.jsp"></jsp:include>
<%
    String notification = (String) request.getAttribute("NOTIFICATION");
    if (notification == null) notification = "";
%>


<div class="container-fluid">

    <div class="col-md-6 col-md-offset-3 mx-auto">
        <h2 class="mt-3">Todo-list</h2>

        <div class="alert alert-success center" role="alert">
            <span><%=notification%></span>
        </div>

        <div class="container">
            <h3 class="text-center">List of Todos</h3>
            <hr>
            <div class="container text-left">

                <a href="<%=request.getContextPath()%>/new"
                   class="btn btn-success">Add Todo</a>
            </div>
            <br>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Target Date</th>
                    <th>Todo Status</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>

                <!--   for (Todo todo: todos) {  -->
                <c:forEach var="todo" items="${listTodo}">

                    <tr>
                        <td>${todo.title}</td>
                        <td>${todo.targetDate}</td>
                        <td>${todo.status ? "Completed" : "In progress"}</td>
                        <td><a href="edit?id=<c:out value='${todo.id}' />">Edit</a></td>
                        <td>
                            <form action="delete" method="post" class="form-inline">
                                <a href="javascript:;" onclick="parentNode.submit();">Delete</a>
                                <input type="hidden" name="id" value="${todo.id}"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <!-- } -->

                </tbody>

            </table>
        </div>
    </div>
</div>
<jsp:include page="./common/footer.jsp"></jsp:include>

</body>
</html>
