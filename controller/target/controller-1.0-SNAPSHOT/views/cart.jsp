<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<%@include file="_defineLocale.jsp"%>

<!doctype html>
<html>

<head>
    <title><fmt:message key="Cart"/></title>
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
    <div class="col-lg-4">
        <c:if test="${empty lots}">
            <h1><fmt:message key="EmptyCart"/></h1>
        </c:if>
        <c:if test="${!empty lots}">
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
                                <c:forEach var="img" items="${lot.images}">
                                    <c:if test="${img.is_cover()}">
                                        <a href="${lotUrl}">
                                            <img src="data:image/jpg;base64,${img.file}" alt="${lot.title}"
                                                 height="200px" >
                                        </a>
                                    </c:if>
                                </c:forEach>
                            </a>
                        </td>
                        <td width="100%" align="center">
                            <c:out value="${lot.title}"/>
                        </td>
                        <td width="100px" align="left" class="price-td">
                                ${lot.price}
                        </td>
                        <td width="100%" align="center">
                            <a href="<c:out value="${deleteLotUrl}"/>">
                                <button class="btn btn-default" id="delete-button">
                                    <fmt:message key="DeleteFromCart"/>
                                </button>
                            </a>
                        </td>
                    </tr>
            </c:forEach>
                <tr>
                    <td></td>
                    <td></td>
                    <td><fmt:message key="Summary"/>:</td>
                    <td id="total_price"></td>
                    <td></td>
                </tr>
            </table>

        <div class="col-lg-12">
            <a href="/payment">
                <button class="order-button" id="order-button"><fmt:message key="Order"/></button>
            </a>
        </div>
        </c:if>

        <script>
            $(document).ready( function() {

                var sum = 0;

                $('#mytable .price-td').each(function () {
                    var price = parseFloat($(this).html());
                    sum += price;
                })
                $('#total_price').html(sum);
            });
        </script>
    </div>
    <div class="col-lg-4"></div>
</div>


<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>
</html>