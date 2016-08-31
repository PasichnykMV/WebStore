<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<%@include file="../_defineLocale.jsp"%>

<head>
    <title><c:out value="${lot.title}" /></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">

    <!-- Resource style -->
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <link rel="stylesheet" href="../../resources/css/app-style.css">
    <%--<script type="text/javascript" href="//code.jquery.com/jquery-1.12.3.js"></script>--%>
    <script src="<c:url value="../../resources/js/jquery.min.js"/>"></script>

    <%@include file="../_header.jsp" %>

</head>
<body>
<div class="col-lg-1"></div>
<div class="col-lg-10">
    <ol class="breadcrumb">
        <c:if test="${!empty topestCategory}">
            <li><a href="#"><c:out value="${topestCategory.title}" /></a></li>
        </c:if>
        <c:if test="${!empty topCategory}">
            <li><a href="#"><c:out value="${topCategory.title}" /></a></li>
        </c:if>

        <li class="active"><c:out value="${lot.category.title}" /></li>
    </ol>
    <br>

    <div class="col-lg-4">
        <form id="lot-form" action="/edit" method="post">
            <input type="hidden" name="type" value="editlot">
            <input type="hidden" name="lotId" value="${lot.id}">
            <div class="input-group">
                <span class="input-group-addon"><span class="glyphicon glyphicon-tags"></span></span>
                <input type="text" id="title" class="form-control" name="title"
                       placeholder="<fmt:message key="LotTitle"/>" value="<c:out value="${lot.title}" />" required>
            </div>
            <br>
            <div class="input-group">
                <input type="text" id="price" class="form-control" name="price" placeholder="<fmt:message key="Price"/>"
                       value="<c:out value="${lot.price}" />" required>
                <span class="input-group-addon">$</span>
            </div>
            <br>
            <div class="form-group">
                <label class="lot_label_left"><fmt:message key="Category"/></label>
                <select class="form-control" id="category" name="categoryId" required>
                    <option name="categoryId" value="<c:out value="${lot.category.id}"/>" selected>
                        <c:out value="${lot.category.title}"/>
                    </option>
                    <c:forEach var="cat" items="${categories}" varStatus="Status">
                        <c:if test="${cat.id != lot.category.id}">
                            <option name="categoryId" value="${cat.id}">
                                <c:out value="${cat.title}"/>
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <br>
            <div class="form-group">
                <label class="lot_label_left"><fmt:message key="Location"/></label>
                <select class="form-control" id="city" name="cityId" required>
                    <option name="categoryId" value="<c:out value="${lot.city.id}"/>" selected>
                        <c:out value="${lot.city.name}"/>
                    </option>
                    <c:forEach var="city" items="${cities}" varStatus="Status">
                        <c:if test="${city.id != lot.city.id}">
                            <option name="cityId" value="${city.id}">
                                <c:out value="${city.name}"/>
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <br>
            <div class="form-group">
                <textarea class="form-control" id="description" rows="3" name="description"
                          placeholder="<fmt:message key="LotDescription"/>" required><c:out value="${lot.description}"/>
                </textarea>
            </div>
            <br>
            <button class="btn btn-default" id="submit_button" style="margin-left: 40%;"
                    type="submit" ><fmt:message key="Edit"/></button>
        </form>
    </div>


    <div class="col-lg-4">
        <c:if test="${!empty lot.images}">
            <label class="lot_label_left"><fmt:message key="UploadLotsCover"/>:</label>
            <br>
            <c:forEach var="img" items="${lot.images}">
                <c:if test="${img.is_cover()}">
                    <div id="cover-image">
                        <img src="data:image/jpg;base64,${img.file}" alt="${lot.title}"
                        width="300px" height="200px">
                    </div>
                        <br><br>
                        <label class="lot_label_left"><fmt:message key="UploadLotsCoverNew"/>:</label>

                        <form id="cover-form" action="/edit" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="type" value="cover">
                            <input type="hidden" name="coverId" value="${img.id}">
                            <input type="hidden" name="lotId" value="<c:out value="${lot.id}"/>">
                            <div class="input-group image-preview">
                                <input type="text" class="form-control image-preview-filename" disabled="disabled">
                                <span class="input-group-btn">
                                <button type="button" class="btn btn-default image-preview-clear"
                                        style="display:none;">
                                    <span class="glyphicon glyphicon-remove"></span> <fmt:message key="Clear"/>
                                </button>
                                <div class="btn btn-default image-preview-input">
                                    <span class="glyphicon glyphicon-folder-open"></span>
                                    <span class="image-preview-input-title"><fmt:message key="Browse"/></span>
                                    <input type="file" accept="image/png, image/jpeg, image/gif"
                                           name="input-file-preview"/>
                                </div>
                                </span>
                            </div>
                            <br>
                            <button type="submit" class="btn btn-default"><fmt:message key="UplNew"/></button>
                        </form>
                </c:if>
            </c:forEach>
        </c:if>
        <c:if test="${empty lot.images}">
            Lot dont have cover-image now
            <br>
            <form id="cover-form" action="/edit" method="post" enctype="multipart/form-data">
                <input type="hidden" name="type" value="newcover">
                <input type="hidden" name="lotId" value="<c:out value="${lot.id}"/>">
                <div class="input-group image-preview">
                    <input type="text" class="form-control image-preview-filename" disabled="disabled">
                                <span class="input-group-btn">
                                <button type="button" class="btn btn-default image-preview-clear"
                                        style="display:none;">
                                    <span class="glyphicon glyphicon-remove"></span> <fmt:message key="Clear"/>
                                </button>
                                <div class="btn btn-default image-preview-input">
                                    <span class="glyphicon glyphicon-folder-open"></span>
                                    <span class="image-preview-input-title"><fmt:message key="Browse"/></span>
                                    <input type="file" accept="image/png, image/jpeg, image/gif"
                                           name="input-file-preview"/>
                                </div>
                                </span>
                </div>
                <br>
                <button type="submit" class="btn btn-default"><fmt:message key="UplNew"/></button>
            </form>
        </c:if>
    </div>

    <div class="col-lg-4">
        <br>
        <label class="lot_label_left"> <fmt:message key="UploadLotsImages"/>:</label>
        <form id="upload-form" action="/edit" method="post" enctype="multipart/form-data">
            <br>
            <input type="hidden" name="type" value="addimage">
            <input type="hidden" name="lotId" value="<c:out value="${lot.id}"/>">
            <input type="file" accept="image/png, image/jpeg, image/gif" name="file" multiple/>
            <br>
            <input type="submit" ><fmt:message key="UplNew"/></input>
        </form>
        <c:if test="${!empty lot.images}">
            <label class="lot_label_left">Lot images:</label>
            <br>
            <c:forEach var="img" items="${lot.images}">
                <c:if test="${!img.is_cover()}">
                    <br>
                    <img src="data:image/jpg;base64,${img.file}" alt="${lot.title}"
                        width="300" height="200">
                    <br>
                    <form id="delete-form" action="/edit" method="post">
                        <br>
                        <input type="hidden" name="type" value="deleteimage">
                        <input type="hidden" name="lotId" value="<c:out value="${lot.id}"/>">
                        <input type="hidden" name="imageId" value="<c:out value="${img.id}"/>">
                        <br>
                        <button class="btn btn-center btn-default" type="submit"><fmt:message key="Delete"/></button>
                    </form>
                    <br>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
</div>

    <div class="col-lg-1"></div>

<script type="text/javascript" src="../../resources/js/editLot.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
