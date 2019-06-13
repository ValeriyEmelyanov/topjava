<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add meal</title>
    <link href="<c:url value="/resources/mealsedit.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add meal</h2>

    <c:if test="${!empty addResult}">
        <div id="result">
            Added: ${addResult}
        </div>
    </c:if>

    <form method="post">
        <c:if test="${empty meal.id}">
            <input type="hidden" name="action" value='add'>
        </c:if>
        <c:if test="${!empty meal.id}">
            <input type="hidden" name="action" value='update'>
            <input type="hidden" name="id" value="${meal.id}">
        </c:if>
        <dl>
            <dt><label for="dateTime">Date/Time</label></dt>
            <dd><input type="datetime-local" name="dateTime" id="dateTime" required value="${meal.dateTime}"></dd>
        </dl>
        <dl>
            <dt><label for="description">Description</label></dt>
            <dd><input type="text" name="description" id="description" required value="${meal.description}"></dd>
        </dl>
        <dl>
            <dt><label for="calories">Calories</label></dt>
            <dd><input type="number" name="calories" id="calories" required value="${meal.calories}"></dd>
        </dl>
        <div>
            <c:if test="${empty meal.id}">
                <button type="submit">Add</button>
            </c:if>
            <c:if test="${!empty meal.id}">
                <button type="submit">Update</button>
            </c:if>
            <button onclick="window.history.back()" type="button">Cancel</button>
        </div>
    </form>

</body>
</html>
