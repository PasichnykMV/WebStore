<c:if test="${!(empty param.locale)}">
    <c:set var="locale" value="${param.locale}" scope="session"/>
</c:if>
<fmt:setLocale value="en" />
<c:if test="${sessionScope.locale eq 'uk'}">
    <fmt:setLocale value="uk" />
</c:if>
<fmt:setBundle basename="ResourceBundle"/>
