<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title> Insert or Edit meal</title>
</head>
<body>

<form method="POST" action='meals' name="frmAddMeal">
    ID : <input type="number" readonly="readonly" name="id"
                     value="<c:out value="${meal.id}" />" /> <br />
    Дата : <input type="datetime" name="date"
        value="<javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" />" /> <br />
    Описание : <input
    type="text" name="desc"
    value="<c:out value="${meal.description}" />" /> <br />
    Калории : <input type="number" name="cal"
                   value="<c:out value="${meal.calories}" />" /> <br />
    <input
        type="submit" value="Отправить" />
</form>
</body>
</html>