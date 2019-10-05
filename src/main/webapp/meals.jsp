<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr><th>Время</th><th>Описание</th><th>Калории</th></tr>
    <c:forEach var="meal" items="${meals}">
        <c:if test="${meal.excess}">
            <tr style="color: red"><td>"${meal.date} ${meal.time}"</td><td>"${meal.description}"</td><td>"${meal.calories}"</td></tr>
        </c:if>
        <c:if test="${!meal.excess}">
            <tr style="color: green"><td>"${meal.date} ${meal.time}"</td><td>"${meal.description}"</td><td>"${meal.calories}"</td></tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
