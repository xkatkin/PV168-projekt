
<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1">
    <thead>
    <tr>
        <th>Target</th>
        <th>Equipment</th>
        <th>Deadline</th>
    </tr>
    </thead>
    <c:forEach items="${missions}" var="mission">
        <tr>
            <td><c:out value="${mission.target}"/></td>
            <td><c:out value="${mission.necessaryEquipment}"/></td>
            <td><c:out value="${mission.deadline}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/missions/delete?id=${mission.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Delete"></form></td>
        </tr>
    </c:forEach>
</table>

<h2>Enter new mission</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/missions/add" method="post">
    <table>
        <tr>
            <th>target:</th>
            <td><input type="text" name="target" value="<c:out value='${param.target}'/>"/></td>
        </tr>
        <tr>
            <th>equipment:</th>
            <td><input type="text" name="necessaryEquipment" value="<c:out value='${param.necessaryEquipment}'/>"/></td>
        </tr>
        <tr>
            <th>day:</th>
            <td><input type="text" name="day" value="<c:out value='${param.day}'/>"/></td>
        </tr>
        <tr>
            <th>month:</th>
            <td><input type="text" name="month" value="<c:out value='${param.month}'/>"/></td>
        </tr>
        <tr>
            <th>year:</th>
            <td><input type="text" name="year" value="<c:out value='${param.year}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Add" />
</form>
</body>
</html>