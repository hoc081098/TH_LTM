<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 12/12/2019
  Time: 12:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
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

        <div class="card">
            <div class="card-body">
                <c:choose>
                <c:when test="${todo != null}">
                <form action="update" method="post">
                    </c:when>
                    <c:otherwise>
                    <form action="insert" method="post">
                        </c:otherwise>
                        </c:choose>

                        <caption>
                            <h2>
                                <c:if test="${todo != null}">
                                    Edit Todo
                                </c:if>
                                <c:if test="${todo == null}">
                                    Add New Todo
                                </c:if>
                            </h2>
                        </caption>

                        <c:if test="${todo != null}">
                            <input type="hidden" name="id" value="<c:out value='${todo.id}' />"/>
                        </c:if>

                        <fieldset class="form-group">
                            <label>Todo Title</label>
                            <input type="text"
                                   value="<c:out value='${todo.title}' />"
                                   class="form-control"
                                   name="title" required="required" minlength="5">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Todo Description</label>
                            <input type="text"
                                   value="<c:out value='${todo.description}' />"
                                   class="form-control"
                                   name="description" minlength="5">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Todo Status</label>
                            <select class="form-control"
                                    name="isDone">
                                <option value="false">In Progress</option>
                                <option value="true">Complete</option>
                            </select>
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Todo Target Date</label>
                            <input type="date"
                                   value="<c:out value='${todo.targetDate}' />"
                                   class="form-control"
                                   name="targetDate" required="required">
                        </fieldset>

                        <button type="submit" class="btn btn-success">Save</button>
                    </form>
            </div>
        </div>
    </div>
</div>
</div>
<jsp:include page="./common/footer.jsp"></jsp:include>

</body>
</html>
