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
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="mealTo" items="${mealTos}">
        <tr class="${mealTo.isExcess() ? "excess" : "notexcess"}">
            <td>${dateTimeFormater.format(mealTo.getDateTime())}</td>
            <td>${mealTo.getDescription()}</td>
            <td>${mealTo.getCalories()}</td>
        </tr>
    </c:forEach>
</table>
</c:if>
</body>
</html>