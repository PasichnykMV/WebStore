<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="_defineLocale.jsp"%>

<head>
    <title><fmt:message key="Sign_up"/></title>

    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<c:url value="../resources/css/app-style.css"/>">

    <script type="text/javascript" src="../resources/js/jquery.min.js"></script>
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
</head>
<body>

<div class="col-lg-4"></div>

<div class="col-lg-4">
    <div class="login-container">
        <c:url value="/login" var="loginUrl" />
        <c:url value="/registration" var="registrationUrl" />

        <a class="navbar-brand brand-login" href="/">SombraWebStore</a>

        <div id="validate" style="color: white;"></div>

        <form id="sign-up-form" action="${registrationUrl}" method="post">
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
                <input type="email" class="form-control" name="email" placeholder="<fmt:message key="Sign_up.email"/>" required autofocus>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                <input type="text" class="form-control" name="name" placeholder="<fmt:message key="Sign_up.name"/>" required autofocus>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                <input type="text" class="form-control" name="last_name"
                       placeholder="<fmt:message key="Sign_up.lastName"/>" required autofocus>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                <input type="password" class="form-control" name="password"
                       placeholder="<fmt:message key="Sign_up.password"/>" id="password" required>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                <input type="password" class="form-control" name="password_confirm"
                       placeholder="<fmt:message key="Sign_up.passwordConfirm"/>" id="password_confirm" required>
            </div>
            <br>
            <button class="btn btn-block sign-in-button" id="submit_button" type="submit">
                <fmt:message key="Sign_up"/>
            </button>
        </form>

        <div style="text-align: center">
            <a href="${loginUrl}" class="register-link"><fmt:message key="Login"/></a>
        </div>

    </div>
</div>

<div class="col-lg-4"></div>
    <%--<script type="text/javascript" href="//code.jquery.com/jquery-1.12.3.js"></script>--%>

    <script type="text/javascript" src="../resources/js/password-validator.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>