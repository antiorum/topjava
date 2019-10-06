<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table {
            /*width: 100%;*/
            background: orangered; /* Цвет фона таблицы */
            color: black; /* Цвет текста */
            border-spacing: 1px; /* Расстояние между ячейками */
        }
        td, th {
            text-align: center;
            background: #fff3cc; /* Цвет фона ячеек */
            padding: 5px; /* Поля вокруг текста */
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Редактирование</th>
        <th>Удаление</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <c:if test="${meal.excess}">
            <tr style="color: red">
        </c:if>
        <c:if test="${!meal.excess}">
            <tr style="color: green">
        </c:if>
        <td>${meal.id}</td>
        <td>${meal.dateTime.format( DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Редактировать</a></td>
        <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="6">
            <h3>Добавление новой еды:</h3>
            <form action="meals" method="post">
                <label for="dateTime">Дата и время </label><input type="datetime-local" id="dateTime" name="dateTime">
                <label for="description">Описание </label><input type="text" id="description" name="description">
                <label for="calories">Калории </label><input type="number" id="calories" name="calories">
                <button type="submit">Добавить</button>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
