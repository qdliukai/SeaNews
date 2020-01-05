<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>用户列表</title>
    <link rel="stylesheet" type="text/css" href="../css/test.css">
</head>
<body>
    <table>
        <tr>
            <td>id</td>
            <td>name</td>
            <td>password</td>
            <td>collect</td>
            <td>Model</td>
            <td>delete</td>
            <td>update</td>
        </tr>
        <c:forEach items="${allUsers}" var="u">
            <tr>
                <td><c:out value="${u.userId}"/></td>
                <td><c:out value="${u.userName}"/></td>
                <td><c:out value="${u.userPassword}"/></td>
                <td><c:out value="${u.userCollect}"/></td>
                <td><c:out value="${u.userModel}"/></td>
                <td><a href='<c:url value="/user/deleteUser?uid=${u.userId}"/>'/>delete</td>
                <td><a href='<c:url value="/user/edituser?uid=${u.userId}"/>'/>update</td>
            </tr>
        </c:forEach>
        <%--<tr>--%>
            <%--<td colspan="5" style="text-align: right">--%>
                <%--<c:forEach begin="1" end="${pages}" step="1" var="i">--%>
                    <%--<c:if test="${i == param.pn}">--%>
                            <%--[<c:out value="${i}" />]&nbsp;&nbsp;--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${i != param.pn}">--%>
                        <%--<a href='<c:url value="/user/findPage?pn=${i}" /> '>--%>
                            <%--<c:out value="${i}" />--%>
                        <%--</a>&nbsp;&nbsp;--%>
                    <%--</c:if>--%>
                <%--</c:forEach>--%>
            <%--</td>--%>
        <%--</tr>--%>
    </table>
</body>
</html>
