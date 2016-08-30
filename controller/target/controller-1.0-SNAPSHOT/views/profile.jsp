<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<%@include file="_defineLocale.jsp"%>

<head>
    <title>Profile</title>
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
    <div class="col-lg-2">
        <br>
        <form id="edit-profile-form" action="/profile" method="post">
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
                <input type="email" class="form-control" name="email" placeholder="<fmt:message key="Sign_up.email"/>"
                      value="<c:out value="${user.email}"/>" required autofocus>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                <input type="text" class="form-control" name="name" placeholder="<fmt:message key="Sign_up.name"/>"
                       value="<c:out value="${user.name}"/>" required>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                <input type="text" class="form-control" name="last_name" placeholder=
                        "<fmt:message key="Sign_up.lastName"/>" value="<c:out value="${user.lastName}"/>" required>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                <input type="text" class="form-control" name="newpassword"
                       placeholder="<fmt:message key="NewPassword"/>">
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                <input type="password" class="form-control" name="password"
                       placeholder="<fmt:message key="Sign_up.password"/>" id="password" required>
            </div>
            <br>
            <button class="btn btn-block sign-in-button" id="submit_button" type="submit"><fmt:message key="Edit"/>
            </button>
        </form>

        <script>
            $(function(){
                $('#edit-profile-form').submit(function(e){
                    e.preventDefault();
                    var m_data=$(this).serialize() +"&edit=true";
                    $.ajax({
                        type: 'post',
                        url: '/profile',
                        data: m_data,
                        success: function(){
                            alert('Updated!');
                        }
                    });
                });
            });
        </script>

    </div>
    <div class="col-lg-1"></div>
    <div class="col-lg-8" style="max-height: 600px; overflow-y: scroll;
                overflow-x:hidden;" >
        <br>
        <label class="lot_label_left"><fmt:message key="OrderHistory"/>:</label>
        <br>
        <table class="table table table-hover" id="datatable">
            <thead>
            <tr>
                <th></th>
                <th><fmt:message key="Title"/></th>
                <th><fmt:message key="Description"/></th>
                <th><fmt:message key="Location"/></th>
                <th><fmt:message key="Category"/></th>
                <th><fmt:message key="Price"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${lots}" var="lot">
                <jsp:useBean id="lot" scope="page" type="com.sombra.model.Lot"/>
                <tr>
                    <td>
                        <c:if test="${!empty lot.images}">
                            <c:forEach var="img" items="${lot.images}">
                                <c:if test="${img.is_cover()}">
                                    <a href="${lotUrl}">
                                        <img src="data:image/jpg;base64,${img.file}" alt="${lot.title}"
                                             width="100" height="100">
                                    </a>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </td>
                    <td><a href="/lot?id=${lot.id}">${lot.title}</a></td>
                    <td width="400px">${lot.description}</td>
                    <td>${lot.city.name}</td>
                    <td>${lot.category.title}</td>
                    <td>${lot.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>