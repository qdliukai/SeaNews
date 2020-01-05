<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户标签</title>
    <link href="css/editTable.css" type="text/css" rel="stylesheet"/>
    <script language="javascript" type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script language="javascript" type="text/javascript" src="js/editTable.js"></script>
</head>
<body>
<input type="hidden" id="userFlag" value="${sessionScope.user.userName}" />
<table class="edtitable">
    <thead>
    <tr>
        <th colspan="3">用户便签</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th>标签</th>
        <th>权重</th>
        <th class="del-col">操作</th>
    </tr>
    <c:forEach items="${userLabelMap}" var="map">
        <tr>
            <td><c:out value="${map.key}"/></td>
            <td><c:out value="${map.value}"/></td>
            <td class="del-col">
                <a href="javascript:void(0);" class="delBtn">删除</a>
            </td>
        </tr>
    </c:forEach>
    <tr class="append-row">
        <td colspan="3" align="right">
            <input type="button" id="addBtn" value="添 加"/>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
