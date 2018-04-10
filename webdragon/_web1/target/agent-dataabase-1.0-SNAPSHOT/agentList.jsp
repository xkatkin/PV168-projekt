<%--
  Created by IntelliJ IDEA.
  User: xkatkin
  Date: 10.4.18
  Time: 8:21
  To change this template use File | Settings | File Templates.
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1">
    <thead>
    <tr>
        <th>Full Name</th>
        <th>Secret Name</th>
        <th>Equipment</th>
    </tr>
    </thead>
    <c:forEach items="${agents}" var="agent">
        <tr>
            <td><c:out value="${agent.fullName}"/></td>
            <td><c:out value="${agent.secretName}"/></td>
            <td><c:out value="${agent.equipment}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/agents/delete?id=${agent.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Delete"></form></td>
        </tr>
    </c:forEach>
</table>

<h2>Insert Agent</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/agents/add" method="post">
    <table>
        <tr>
            <th>Full name:</th>
            <td><input type="text" name="fullName" value="<c:out value='${param.fullName}'/>"/></td>
        </tr>
        <tr>
            <th>Secret name:</th>
            <td><input type="text" name="secretName" value="<c:out value='${param.secretName}'/>"/></td>
        </tr>
        <tr>
            <th>Equipment:</th>
            <td><input type="text" name="equipment" value="<c:out value='${param.equipment}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Add" />
</form>
</body>
</html>