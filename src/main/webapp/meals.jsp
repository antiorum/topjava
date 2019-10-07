<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<%--<jsp:useBean id="formatter" class="java.time.format.DateTimeFormatter" beanName="formatter"></jsp:useBean>--%>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Редактирование</th>
        <th>Удаление</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr style="color: ${meal.excess?'red':'green'}">
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=${meal.id}">Редактировать</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="6">
            <a href="meals?action=add"><h3>Добавление новой еды</h3></a>
        </td>
    </tr>
</table>
</body>
</html>
