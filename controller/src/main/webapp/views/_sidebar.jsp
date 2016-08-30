<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page session="true" %>
<%@page isELIgnored="false" %>
<head>

    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/menu.css"/>" />

</head>
<body>

<div class="container">
    <div class="row">
        <hr>
        <div class="dropdown open">
        <ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">
            <li><a href="/">All</a></li>
            <c:forEach var="cat" items="${categories}" varStatus="Status">
                <c:url var="categoryUrl" value="/" >
                    <c:param name="category" value="${cat.id}" />
                </c:url>

                <c:if test="${empty cat.subcategories}">
                    <li><a href="${categoryUrl}">${cat.title}</a></li>
                </c:if>
                <c:if test="${!empty cat.subcategories}">
                    <li class="dropdown-submenu">
                        <a href="${categoryUrl}">${cat.title}</a>
                        <ul class="dropdown-menu">
                             <c:forEach var="subcat" items="${cat.subcategories}" varStatus="Status">
                                 <c:url var="subcategoryUrl" value="/" >
                                     <c:param name="category" value="${subcat.id}" />
                                 </c:url>

                                 <c:if test="${empty subcat.subcategories}">
                                    <li>
                                        <a href="${subcategoryUrl}"><c:out value="${subcat.title}" /></a>
                                     </li>
                                 </c:if>
                                 <c:if test="${!empty subcat.subcategories}">
                                     <li class="dropdown-submenu">
                                         <a href="${subcategoryUrl}">
                                            <c:out value="${subcat.title}" />
                                         </a>
                                         <ul class="dropdown-menu">
                                            <c:forEach var="subcat1" items="${subcat.subcategories}" varStatus="Status">
                                                <c:url var="subcat1egoryUrl" value="/" >
                                                    <c:param name="category" value="${subcat1.id}" />
                                                </c:url>

                                                <li>
                                                    <a href="${subcat1egoryUrl}">
                                                        <c:out value="${subcat1.title}" />
                                                    </a>
                                                </li>
                                            </c:forEach>
                                         </ul>
                                     </li>
                                 </c:if>
                             </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
        </div>
    </div>
</div>

    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/js/menu.js"/>" type="text/javascript"></script>

</body>
</html>


