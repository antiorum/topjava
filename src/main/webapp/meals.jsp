<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <c:if test="${meal.excess}">
            <tr style="color: red">
        </c:if>
        <c:if test="${!meal.excess}">
            <tr style="color: green">
        </c:if>
        <td>${meal.dateTime.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
