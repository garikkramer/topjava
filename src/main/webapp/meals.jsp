<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals List</title>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <table border=1>
        <thead>
        <tr>
            <th>ID</th>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
            <!--<th colspan=2>Action</th>-->
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${meals}" var="meal">
            <tr bgcolor="${meal.exceed eq true ? "red": "green"}">
                <td><c:out value="${meal.id}" /></td>
                <td><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td><c:out value="${meal.description}" /></td>
                <td><c:out value="${meal.calories}" /></td>
                <td><a href="meals?action=edit&Id=<c:out value="${meal.id}"/>">Update</a></td>
                <td><a href="meals?action=delete&Id=<c:out value="${meal.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p><a href="meals?action=insert">Add User</a></p>
</body>
</html>
