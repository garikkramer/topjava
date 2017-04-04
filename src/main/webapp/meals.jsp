<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <hr>
    <form id="filter" class="form-horizontal" action="meals?action=filtering" method="post">
            <label for="startDate">От даты:</label>
            <input type="date" value="${fn:formatDateTime(startDate)}" name="startDate" id="startDate">

            <label for="startTime">От времени:</label>
            <input type="date" value="${fn:formatDateTime(startTime)}" name="startTime" id="startTime">

            <label for="endDate">До даты:</label>
            <input type="time" value="${fn:formatDateTime(endDate)}" name="endDate" id="endDate">

            <label for="endTime">До времени:</label>
            <input type="time" value="${fn:formatDateTime(endTime)}" name="endTime" id="endTime">
            <input type="submit" value="Фильтровать">
    </form>
    <hr>
    <form method="post" action="meals?action=sorted">
        <select name="sorter" required>
            <option ${sorter.equals("DATE_TIME")? 'selected': ''} value="DATE_TIME">По дате и времени</option>
            <option ${sorter.equals("DATE")? 'selected': ''} value="DATE">По дате</option>
            <option ${sorter.equals("TIME")? 'selected': ''} value="TIME">По времени</option>
        </select>
        <input type="submit" value="Сортировать">
    </form>
    <hr>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>