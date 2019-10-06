<%--
  Created by IntelliJ IDEA.
  User: Фобка
  Date: 06.10.2019
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h2>Редактирование выбранной записи</h2>
<form action="meals" method="post" accept-charset="UTF-8">
    <input readonly type="number" name="id" value="${meal.id}">
    <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
    <input type="text" name="description" value="${meal.description}">
    <input type="number" name="calories" value="${meal.calories}">
    <button type="submit">Подтвердить</button>
</form>
</body>
</html>
