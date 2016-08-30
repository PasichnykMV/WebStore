<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<%@include file="_defineLocale.jsp"%>

<head>
    <title><c:out value="${lot.title}" /></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">

    <!-- Resource style -->
    <link rel='stylesheet' href="<c:url value="../resources/css/dataTables.bootstrap.min.css"/>">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="<c:url value="../resources/css/app-style.css"/>">

    <script src="<c:url value="../resources/js/jquery.min.js"/>"></script>

    <%@include file="_header.jsp" %>
</head>
<body>
    <div class="col-lg-1"></div>
    <div class="col-lg-10">
        <ol class="breadcrumb">
            <c:if test="${!empty topestCategory}">
                <li><a href="/?category=${topestCategory.id}"><c:out value="${topestCategory.title}" /></a></li>
            </c:if>
            <c:if test="${!empty topCategory}">
                <li><a href="/?category=${topCategory.id}"><c:out value="${topCategory.title}" /></a></li>
            </c:if>

            <li class="active"><c:out value="${lot.category.title}" /></li>

        </ol>
        <br>

        <div class="col-lg-5">
        <div class="carousel-conteiner">
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="false">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <c:forEach var="img" items="${lot.images}" varStatus="status">
                        <c:if test="${status.index == 0}">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        </c:if>
                        <c:if test="${status.index gt 0}">
                            <li data-target="#carousel-example-generic" data-slide-to="${status.index}"
                                class="active"></li>
                        </c:if>

                    </c:forEach>
                </ol>

                <div class="carousel-inner">
                    <c:if test="${!empty lot.images}">
                        <c:forEach var="img" items="${lot.images}">
                            <c:if test="${img.is_cover()}">
                                <div class="item active">
                                    <img src="data:image/jpg;base64,${img.file}" alt="${lot.title}">
                                </div>
                            </c:if>
                            <c:if test="${!img.is_cover()}">
                                <div class="item">
                                    <img src="data:image/jpg;base64,${img.file}" alt="${lot.title}">
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                </a>
            </div> <!-- Carousel -->
        </div>
        </div>

        <div class="col-lg-6 lot-info">
            <h1 class="lot-header"><c:out value="${lot.title}" /></h1>
            <label class="lot_label_date"><fmt:message key="AddedAt"/>
                <fmt:formatDate value="${lot.creationDate}" dateStyle="LONG" type="date" /></label>
            <br>
            <br>
            <div class="row">
                <div class="col-lg-6">
                <label class="lot_label_left"><fmt:message key="Location"/></label>
                <label class="lot_label_right"><c:out value="${lot.city.name}" /></label>
                <br>
                <label class="lot_label_left"><fmt:message key="Price"/></label>
                <label class="lot_label_right price">
                    <fmt:formatNumber value="${lot.price}" type="currency" currencySymbol="$"/>
                </label>
                    <br>
                    <br>

                    <security:authorize access="hasAuthority('USER')">
                        <button class="to-cart-button" id="to-cart-button"><fmt:message key="ToCart"/></button>
                    </security:authorize>
                    <script>
                        $("#to-cart-button").click(function(){
                            $.ajax({
                                url : '/cart',
                                type: 'POST',
                                data : {
                                    lotId : ${lot.id}
                                },
                                success: alert("Successfuly added to your cart!")
                            });

                            $.ajax({
                                url : '/cart',
                                type: 'GET',
                                success: function(response) {
                                    $("#cart-badge").text(response);
                                }
                            });
                        });
                    </script>
                </div>
            </div>
            <br>
            <br>

            <div class="well"><c:out value="${lot.description}" /></div>

        </div>

    </div>

    <div class="col-lg-1"></div>

    <script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
