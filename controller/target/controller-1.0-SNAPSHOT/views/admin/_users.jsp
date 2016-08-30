<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<head>
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<c:url value="/resources/css/app-style.css"/>">
    <script src="<c:url value="../resources/js/jquery.min.js"/>"></script>
</head>
<body>
<c:if test="${!empty users}">
    <br>
    <table class="table" id="mytable">
    <c:forEach var="user" items="${users}" varStatus="Status">
        <tr>
            <td>
                <a href="${lotUrl}"><c:out value="${user.name}" /> </a>
            </td>
            <td>
                <c:out value="${user.lastName}"/>
            </td>
            <td width="100px" align="left">
                    ${user.email}
            </td>
            <td width="100%" align="center">
                    <form  action="/profile" method="post" id="ban-form">
                        <input type="hidden" name="user_id" value="${user.id}">
                        <input type="hidden" name="enable" value="change">
                        <button type="submit">
                            <c:if test="${user.is_enable()}">
                                <span  class="glyphicon glyphicon-eye-open"></span>
                            </c:if>
                            <c:if test="${!user.is_enable()}">
                                <span  class="glyphicon glyphicon-eye-close"></span>
                            </c:if>
                        </button>
                    </form>
            </td>
        </tr>
    </c:forEach>
    </table>
</c:if>
</body>
