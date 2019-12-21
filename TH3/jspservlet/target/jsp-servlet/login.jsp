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
        <h2 class="mt-3">Login</h2>

        <div class="alert alert-success center" role="alert">
            <span><%=notification%></span>
        </div>

        <form action="<%=request.getContextPath()%>/login" method="post">

            <div class="form-group">
                <label for="username">User Name:</label>
                <input type="text"
                       class="form-control" id="username" placeholder="User Name"
                       name="username" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password"
                       class="form-control" id="password" placeholder="Password"
                       name="password" required>
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>

        </form>
    </div>
</div>
<jsp:include page="./common/footer.jsp"></jsp:include>

</body>
</html>
