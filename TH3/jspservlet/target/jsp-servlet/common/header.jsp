<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div>
            <a class="navbar-brand" href="#">Todo App</a>
        </div>

        <ul class="navbar-nav navbar-collapse justify-content-end">
            <c:choose>
                <c:when test='<%= request.getSession().getAttribute("current_user") != null %>'>
                    <li>
                        <form action="<c:url value="/logout"/>" method="post" class="form-inline">
                            <button type="submit" class="btn btn-info">Logout</button>
                        </form>
                    </li>
                </c:when>
                <c:otherwise>
                    <li><a href="<%= request.getContextPath() %>/login" class="nav-link">Login</a></li>
                    <li><a href="<%= request.getContextPath() %>/register" class="nav-link">Register</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>