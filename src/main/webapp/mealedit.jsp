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
        <label for="dateTime">Date/Time</label>
        <input type="datetime-local" name="dateTime" id="dateTime" required value="${meal.dateTime}">
        <br/>
        <label for="description">Description</label>
        <input type="text" name="description" id="description" required value="${meal.description}">
        <br/>
        <label for="calories">Calories</label>
        <input type="number" name="calories" id="calories" required value="${meal.calories}">
        <br/>
        <div>
            <c:if test="${empty meal.id}">
                <button type="submit">Add</button>
            </c:if>
            <c:if test="${!empty meal.id}">
                <button type="submit">Update</button>
            </c:if>
        </div>
    </form>

</body>
</html>
