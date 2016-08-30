<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<%@include file="_defineLocale.jsp"%>

<html>

<head>
    <title>Payment</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">

    <!-- Resource style -->
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<c:url value="../resources/css/app-style.css"/>">

    <%--<script type="text/javascript" href="//code.jquery.com/jquery-1.12.3.js"></script>--%>
    <script src="<c:url value="../resources/js/jquery.min.js"/>"></script>
    <script src="<c:url value="../resources/js/jquery.card.js"/>"></script>
    <a href="/">Back to WebStore</a>

    <style>
        form {
            margin: 30px;
        }

        input {
            width: 200px;
            margin: 10px auto;
            display: block;
        }
    </style>

</head>
<body>
<div>
    <div class="col-lg-4"></div>
    <div class="col-lg-4">
        <div class="card-wrapper"></div>

        <div class="form-container active">
            <form action="/payment" method="post">
                <input placeholder="Card number" type="text" name="number">
                <input placeholder="Full name" type="text" name="name">
                <input placeholder="MM/YY" type="text" name="expiry">
                <input placeholder="CVC" type="text" name="cvc">

                <input type="submit">
            </form>
        </div>

        <script>
            new Card({
                form: document.querySelector('form'),
                container: '.card-wrapper'
            });
        </script>
    </div>
    <div class="col-lg-4"></div>
</div>

<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>
</html>