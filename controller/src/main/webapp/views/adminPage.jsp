<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>


<head>
    <title>Admin CMS</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">

    <!-- Resource style -->
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<c:url value="/resources/css/app-style.css"/>">
    <script src="<c:url value="../resources/js/jquery.min.js"/>"></script>

    <%@include file="_header.jsp" %>

</head>

<body>
    <div>
        <div class="col-lg-1"></div>
        <div class="col-lg-10">
            <ul class="nav nav-pills">
                <li role="presentation"><a href="/admin?page=categories">
                    <span class="glyphicon glyphicon-th-list"></span> Categories</a></li>
                <li role="presentation"><a href="/admin?page=cities">
                    <span class="glyphicon glyphicon-tower"></span> Cities</a></li>
                <li role="presentation"><a href="/admin?page=users">
                    <span class="glyphicon glyphicon-user"></span> Users</a></li>
                <li role="presentation"><a href="/admin?page=lot">
                    <span class="glyphicon glyphicon-plus"></span> New Lot</a></li>
            </ul>

            <c:choose>
                <c:when test="${param.page eq 'lot'}">
                    <%@include file="admin/_lots.jsp" %>
                </c:when>
                <c:when test="${param.page eq 'users'}">
                    <%@include file="admin/_users.jsp" %>
                </c:when>
                <c:when test="${param.page eq 'cities'}">
                    <%@include file="admin/_cities.jsp" %>
                </c:when>
                <c:when test="${param.page eq 'categories'}">
                    <%@include file="admin/_categories.jsp" %>
                </c:when>
            </c:choose>

        </div>
        <div class="col-lg-1"></div>
    </div>

    <script src="<c:url value="../resources/js/navs.js"/>"></script>

    <script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>