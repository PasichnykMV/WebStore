<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="_defineLocale.jsp"%>

<head>
    <title><fmt:message key="Login"/></title>

    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<c:url value="../resources/css/app-style.css"/>">


</head>
<body>

    <div class="col-lg-4"></div>

    <div class="col-lg-4">
        ${msg}
        <div class="login-container">
            <c:url value="/j_spring_security_check" var="loginUrl" />
            <c:url value="/registration" var="registrationUrl" />

            <a class="navbar-brand brand-login" href="/">SombraWebStore</a>

            <form action="${loginUrl}" method="post">
                <div class="input-group">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
                    <input type="text" class="form-control" name="j_email"
                           placeholder="<fmt:message key="Sign_up.email"/>" required autofocus>
                </div>
                <br>
                <div class="input-group">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                    <input type="password" class="form-control" name="j_password"
                           placeholder="<fmt:message key="Sign_up.password"/>" required>
                </div>
                <br>
                <button class="btn btn-block sign-in-button" type="submit"><fmt:message key="Login"/></button>
            </form>

            <div style="text-align: center">
                <a href="${registrationUrl}" class="register-link"><fmt:message key="CreateNewAccount"/></a>
            </div>

        </div>
    </div>

    <div class="col-lg-4">
        <div class="well" style="background-color: goldenrod; margin-top: 20%; height: 100px;">
            <b>Admin</b>
            <br>
            <b>admin@mail.ru - pass</b>
        </div>
        <div class="well" style="background-color: forestgreen; margin-top: 5%; height: 100px;">
            <b>User</b>
            <br>
            <b>test1@mail.ru - pass</b>
        </div>
    </div>

    <script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
