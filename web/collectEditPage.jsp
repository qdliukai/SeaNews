<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>收藏</title>
    <link rel="stylesheet" type="text/css" href="css/table.css"/>
</head>
<body>
    <table border="1">
        <tr>
            <td style="display: none">编号</td>
            <td>日期</td>
            <td>标题</td>
            <td>链接</td>
            <td>删除</td>
        </tr>
        <c:forEach items="${userCollectList}" var="list">
            <tr>
                <td style="display: none"><c:out value="${list.recommendKey}"/></td>
                <td><c:out value="${list.date}"/></td>
                <td><c:out value="${list.title}"/></td>
                <td><a href='<c:url value="${list.url}"/>' target="_blank"/><c:out value="${list.url}"/></td>
                <td><a href='<c:url value="/updateUserCollect?collectId=${list.recommendKey}&"/>'/>删除</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
