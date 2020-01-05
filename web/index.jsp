<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>海洋资讯推荐系统</title>
    <link rel="stylesheet" type="text/css" href="css/base.css">
    <link rel="stylesheet" type="text/css" href="css/thems.css">
    <link rel="stylesheet" type="text/css" href="css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="css/jqcloud.css"/>
    <link rel="stylesheet" type="text/css" href="css/responsive.css">
    <link rel="stylesheet" type="text/css" href="css/my.css"/>
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="js/js_z.js"></script>
    <script type="text/javascript" src="js/my.js"></script>
    <script type="text/javascript" src="js/layer/layer.js"></script>
    <script type="text/javascript" src="js/jqcloud-1.0.4.js"></script>
    <script>
        //
        <!--这个cg就是span的id，初始化Date时间并转化为字符string类型,每1000毫秒，setInterval() 就会调用函数，直到被关闭。-->
        setInterval("cg.innerHTML=new Date().toLocaleString()", 1000);
    </script>
</head>
<body>
<div id="container">
    <div id="header">
        <div class="navigation-bar" style="width: 20%">
            <a class="first-a" href="/index">www.seanews.com</a>
        </div>
        <div class="navigation-bar" style="width: 40%"></div>
        <div class="navigation-bar" style="width: 25%">
            <li class="clearfix" style="margin: 0 auto;">
                <div class="navigation-bar" style="margin: 0 auto; text-align: center;">
                    <input type="text" class="search" placeholder="请输入关键词搜索"/>
                    <input type="button" name="" class="btn"/>
                </div>
            </li>
        </div>
        <div class="navigation-bar" style="width: 15%">
            <span id="cg" class="time-span">2020/01/01 上午12:00:00</span>
        </div>
    </div>
    <div id="main" class="news clearfix">
        <div id="left">
            <div class="scd_mr">
                <div class="box_h">
                    <span>用户中心</span>
                </div>
                <div class="box_m">
                    <li class="clearfix">
                        <div class="li_r" style="margin: 0 auto;">
                            <%--<c:out value="${sessionScope.user.userName}"/>--%>
                            <%--当用户登录时，session中存储用户信息--%>
                            <c:if test="${sessionScope.user != null}">
                                <span id="userSpan" class="title" style="font-size:18px;">
                                    当前用户:&nbsp;&nbsp;<c:out value="${sessionScope.user.userName}"/>
                                    <input type="submit" class="button" onclick="Logout()" value="退出">
                                </span>
                            </c:if>
                            <c:if test="${sessionScope.user == null}">
                                <input id="loginButton" type="button" class="button" onclick="ShowLoginText()"
                                       value="登录">
                            </c:if>
                            <!--<input name="" type="text">-->
                            <div id="loginBox">
                                <div class="login-item"><input type="text" id="InputUsername" placeholder="请输入用户名"/>
                                </div>
                                <div class="login-item"><input type="password" id="InputUserPwd" placeholder="请输入密码"/>
                                </div>
                                <div class="login-item">
                                    <a href="javascript:;" onclick="Login()">登录</a>
                                </div>
                            </div>
                        </div>
                    </li>
                    <div class="space_hx">&nbsp;</div>
                    <input id = "offlineBtn" type="button" class="button" onclick="showOffline()" value="离线">
                    <input id = "onlineBtn" type="button" class="button" onclick="showOnline()" style="background: #EC9D19" value="实时">
                    <li class="clearfix" style="display: block" id="online">
                        <div class="li_r">
                            <span class="title" style="font-size:18px; ">推荐时间间隔：</span>
                            <select class="select" id="onlineSelect" onchange="startOnlineRecommend()">
                                <option value="24">最近24小时</option>
                                <option value="12">最近12小时</option>
                                <option value="6">最近6小时</option>
                            </select>
                        </div>
                    </li>
                    <li class="clearfix" style="display: none" id="offline">
                        <div class="li_r">
                            开始时间：<input id="beginTime" type="date" style="width: auto"/>
                            <br>
                            结束时间：<input id="endTime" type="date" style="width: auto;"/>
                            <br>
                            <input type="button" class="button" onclick="startOfflineRecommend()" value="确定">
                        </div>
                    </li>
                    <div class="space_hx">&nbsp;</div>
                    <li class="clearfix">
                        <div class="li_r">
                            <span class="title" style="font-size:18px; ">用户收藏：</span>
                            <!--<input name="" type="text">-->
                            &nbsp;&nbsp;
                            <input type="hidden" id="userFlag" value="${sessionScope.user.userName}" />
                            <input type="button" class="button" onclick="userCollectEdit()" value="编辑">
                        </div>
                    </li>
                    <div class="space_hx">&nbsp;</div>
                    <li class="clearfix">
                        <div class="li_r">
                            <span class="title" style="font-size:18px; ">兴趣标签：</span>
                            <!--<input name="" type="text">-->
                            &nbsp;&nbsp;
                            <input type="button" class="button" onclick="userLabelEdit()" value="编辑">
                        </div>
                    </li>
                    <div class="space_hx">&nbsp;</div>
                    <div id="example" style="width: 300px; height: 300px;"></div>
                </div>
            </div>
        </div>
        <div id="center">
            <div class="my_top">
                <span>海洋资讯推荐</span>
            </div>
            <script type="text/javascript" language="javascript">
                function goPage(page) {
                    location.href = "/index?currentPage=" + page;
                }
            </script>
            <div class="new_m">
                <c:forEach items="${paging.dataList}" var="recommendList">
                    <div class="n_m">
                        <div class="title">
                            <input type="hidden" id="collectKey" value="${recommendList.recommendKey}" />
                            <span onclick="userCollect('${recommendList.recommendKey}')">${recommendList.date}</span>
                            <p class="name">
                                <a href="${recommendList.url}" target="_blank">${recommendList.title}</a>
                            </p>
                        </div>
                        <div class="des" style="height: auto;">
                                ${recommendList.summary}
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="pages">
                <div class="venderfen">
                    <c:if test="${paging.totalPage > 0}">
                        当前第 ${paging.currentPage } 页/共  ${paging.totalPage} 页
                    </c:if>&nbsp;&nbsp;
                    <c:if test="${paging.totalPage > 1}">
                        <c:choose>
                            <c:when test="${paging.currentPage==2 && paging.totalPage==2}">
                                <a onclick="goPage(1)">首页</a>
                                <a onclick="goPage(${paging.currentPage-1})">上一页</a>
                            </c:when>
                            <c:when test="${paging.currentPage==1}">
                                <a onclick="goPage(${paging.currentPage+1})">下一页</a>
                                <a onclick="goPage(${paging.totalPage})">末页</a>
                            </c:when>
                            <c:when test="${paging.currentPage==paging.totalPage}">
                                <a onclick="goPage(1)">首页</a>
                                <a onclick="goPage(${paging.currentPage-1})">上一页</a>
                            </c:when>
                            <c:otherwise>
                                <a onclick="goPage(1)">首页</a>
                                <a onclick="goPage(${paging.currentPage-1})">上一页</a>
                                <a onclick="goPage(${paging.currentPage+1})">下一页</a>
                                <a onclick="goPage(${paging.totalPage})">末页</a>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    共 ${paging.total} 条
                </div>
            </div>
        </div>
        <div id="right">
            <div class="scd_mr">
                <div class="box_h">
                    <span>最新海洋经济资讯</span>
                    <a href="">更多</a>
                </div>
                <div class="box_m">
                    <ul>
                        <c:forEach items="${weekNews}" var="wNews">
                            <li><a href="${wNews.key}">${wNews.value}</a></li>
                        </c:forEach>
                        <%--<li><a href="">大力发展海洋经济 举力建设依海强市</a></li>--%>
                    </ul>
                </div>
                <div class="space_hx">&nbsp;</div>
                <div class="space_hx">&nbsp;</div>
                <div class="space_hx">&nbsp;</div>
                <div class="box_h">
                    <span>最新全部海洋资讯</span>
                    <a href="">更多</a>
                </div>
                <div class="box_m">
                    <ul>
                        <c:forEach items="${latestNews}" var="lNews">
                            <li><a href="${lNews.key}">${lNews.value}</a></li>
                        </c:forEach>
                        <%--<li><a href="">22名专家智力支持辽宁省大连“海上天路”建设</a></li>--%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div id="footer">
        <div class="gcs-footer">
            <div class="footer-top">
                <a href="http://www.hellosea.net/">海洋网</a> |
                <a href="http://ocean.china.com.cn/">中国海洋网</a> |
                <a href="http://www.nmdis.org.cn/">中国海洋信息网</a> |
                <a href="https://www.chinaoceannews.cn/">中国海洋新闻网</a> |
                <a href="http://www.oceanol.com/">中国海洋在线</a> |
                <a href="http://www.ouc.edu.cn/">中国海洋大学</a> |
            </div>
            <p>
                ©Copyright 2019 中国海洋大学信息科学与工程学院XLab | 青岛ICP备21170231号-333
            </p>
        </div>
    </div>
</div>
</body>
</html>