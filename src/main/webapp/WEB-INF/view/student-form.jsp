<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>REST Service</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js">
    </script>
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: cadetblue">
        <div>
            <a href="https://www.javaguides.net" class="navbar-brand"> REST Service </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list"
                   class="nav-link">Students</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
<div class="card">
<div class="card-body">
<c:if test="${student != null}">
    <form id="student-form" action="editStudent" method="post">
</c:if>
<c:if test="${student == null}">
    <form action="newStudent" method="post">
</c:if>

<caption>
    <h2>
        <c:if test="${student != null}">
            Edit Student
        </c:if>
        <c:if test="${student == null}">
            Add New Student
        </c:if>
    </h2>
</caption>

<c:if test="${student != null}">
    <input type="hidden" name="id" value="<c:out value='${student.id}' />"/>
</c:if>

    <fieldset class="form-group">
    <label>Student Name</label> <input type="text"
    value="<c:out value='${student.name}'/>" class="form-control"
    name="name" required="required">
    </fieldset>

    <fieldset class="form-group">
    <label>Coordinatir ID</label> <input type="text"
    value="<c:out value='${student.coordinator_id}'/>" class="form-control"
    name="coord_id" required="required">
    </fieldset>
    <button type="submit" class="btn btn-success" id="button" >Save</button>
    </form>


    </div>
    </div>
    </div>

<%--<script>--%>

<%--    $("#student-form").submit(function(event){--%>
<%--        event.preventDefault();--%>
<%--        var $form = $(this);--%>
<%--        var userId = $form.find('input[name="id"]').val();--%>
<%--        var url = 'http://localhost:8086/editStudent';  //+userId--%>
<%--        var newname = $form.find('input[name="name"]').val();--%>
<%--        var newcoord_id = $form.find('input[name="coord_id"]').val();--%>

<%--        $.ajax({--%>
<%--            type : 'PUT',--%>
<%--            url : url,--%>
<%--            contentType: 'application/json',--%>
<%--            data : JSON.stringify({name: newname, coord_id: newcoord_id}),--%>
<%--            success : function(data, status, xhr){--%>
<%--                window.location.replace("http://localhost:8086/editStudent"); //+userId--%>
<%--            },--%>
<%--            error: function(xhr, status, error){--%>
<%--                $('#msg').html('<span style=\'color:red;\'>'+error+'</span>')--%>
<%--            }--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
    </body>
    </html>
