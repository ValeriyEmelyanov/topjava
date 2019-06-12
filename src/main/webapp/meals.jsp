<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link href="<c:url value="/resources/meals.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<c:if test="${mealTos.isEmpty()}">
    <p>No meals<p>
</c:if>
<c:if test="${!mealTos.isEmpty()}">
<table>
    <tr id="title">
        <th>Id</th>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    <c:forEach var="mealTo" items="${mealTos}">
        <tr class="${mealTo.isExcess() ? "excess" : "notexcess"}">
            <td>${mealTo.getId()}</td>
            <td>${dateTimeFormater.format(mealTo.getDateTime())}</td>
            <td>${mealTo.getDescription()}</td>
            <td>${mealTo.getCalories()}</td>
            <td>
                <form method="post">
                    <input type='hidden' name='action' value='forwardToEdit'>
                    <input type='hidden' name='id' value='${mealTo.id}'>
                    <button type='submit'>Edit</button>
                </form>
            </td>
            <td>
                <form method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${mealTo.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</c:if>
<div>
    <form method="post">
        <input type='hidden' name='action' value='forward'>
        <input type='hidden' name='page' value='mealedit.jsp'>
        <button type='submit'>Add meal</button>
    </form>
</div>
</body>
</html>