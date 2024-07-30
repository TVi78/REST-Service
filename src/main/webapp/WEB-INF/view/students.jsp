<%--
  Created by IntelliJ IDEA.
  User: Viki
  Date: 27.07.2024
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Студенты</title>
    <%--<style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style> --%>

</head>
<body>

<h2>Список всех студентов</h2>

<c:forEach var="student" items="${requestScope.students}">
    <ul>
        ID: <c:out value="${student.id}"/> <br>
        Имя: <c:out value="${student.name}"/> <br>
        Координатор: <c:out value="${student.coordinator_id}"/> <br>
    </ul>
    <hr/>
</c:forEach>

<a href="<c:url value="/students"/>">
    <img src="<%=request.getContextPath()%>/images/backToMyTasks.png" alt="Logo" width="120" height="60"></a>
</a>


</body>
</html>
