<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<!doctype html>
<html>

<head>
    <title>Web Store</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">

    <!-- Resource style -->
    <link rel='stylesheet' href="<c:url value="/resources/css/dataTables.bootstrap.min.css"/>">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<c:url value="/resources/css/app-style.css"/>">

    <script type="text/javascript" href="//code.jquery.com/jquery-1.12.3.js"></script>

    <%@include file="views/_header.jsp" %>

</head>
<body>
    <div>
        <div class="col-lg-2"><%@include file="views/_sidebar.jsp" %></div>

        <div class="col-lg-10">
            <ul class="nav nav-tabs">
                <li role="presentation"><a href="/"><span class="glyphicon glyphicon-th-large"></span> </a></li>
                <li role="presentation"><a href="${pageContext.request.contextPath}?${pageContext.request.queryString}&viewMode=list"><span class="glyphicon glyphicon-list"></span> </a></li>
            </ul>

            <c:choose>
                <c:when test="${param.viewMode eq 'list'}">
                    <%@include file="views/_table.jsp" %>
                </c:when>
                <c:otherwise>
                    <%@include file="views/_container.jsp" %>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>
</html>