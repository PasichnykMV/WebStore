<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<%@include file="_defineLocale.jsp"%>

<head>
    <title>Check</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">

    <!-- Resource style -->
    <link rel='stylesheet' href="<c:url value="../resources/css/dataTables.bootstrap.min.css"/>">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<c:url value="../resources/css/app-style.css"/>">

    <script type="text/javascript" href="//code.jquery.com/jquery-1.12.3.js"></script>
    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>


    <%@include file="_header.jsp" %>

</head>
<body>
<div>
    <div class="col-lg-4"></div>
    <div class="col-lg-4"><fmt:message key="Payed"/>!
        <table class="table" id="mytable">
            <c:forEach var="lot" items="${lots}" varStatus="Status">
                <c:url var="lotUrl" value="/lot?id=${lot.id}" />
                <c:url var="deleteLotUrl" value="/cart">
                    <c:param name="lotId" value="${lot.id}"/>
                    <c:param name="action" value="delete"/>
                </c:url>
                <tr>
                    <td>
                        <input type="hidden" value="<c:out value="${lot.id}"/>" id="lot-id">
                    </td>
                    <td>
                        <a href="${lotUrl}">
                            <img src="http://podushki.kz/images/stories/1002100_34908873.jpg" alt="..."
                                 width="50px;" height="50px;">
                        </a>
                    </td>
                    <td width="100%" align="center">
                        <c:out value="${lot.title}"/>
                    </td>
                    <td width="100px" align="left" class="price-td">
                            ${lot.price}
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form action="/check" method="post">
            <button type="submit"><fmt:message key="DownloadCheck"/></button>
        </form>
    </div>
    <div class="col-lg-4"></div>
</div>


<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>