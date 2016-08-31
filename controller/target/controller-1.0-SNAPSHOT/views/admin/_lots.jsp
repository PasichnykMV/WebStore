<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<body>
<div class="col-lg-4">
    <br>
    <label class="lot_label_left">Upload lots cover-image:</label>
    <form id="cover-form" action="javascript:;" method="post" enctype="multipart/form-data">
        <br>
        <input type="hidden" name="type" value="cover">

    <div class="input-group image-preview">
        <input type="text" class="form-control image-preview-filename" disabled="disabled"> <!-- don't give a name === doesn't send on POST/GET -->
                <span class="input-group-btn">
                    <button type="button" class="btn btn-default image-preview-clear" style="display:none;">
                        <span class="glyphicon glyphicon-remove"></span> Clear
                    </button>
                    <div class="btn btn-default image-preview-input">
                        <span class="glyphicon glyphicon-folder-open"></span>
                        <span class="image-preview-input-title">Browse</span>
                        <input type="file" accept="image/png, image/jpeg, image/gif" name="input-file-preview"/> <!-- rename it -->
                    </div>
                </span>
    </div>
        <br>
        <input type="submit">
    </form>

    <br>
    <label class="lot_label_left">Upload lots images:</label>
    <form id="file-form" action="javascript:;" method="post" enctype="multipart/form-data">
        <br>
        <input type="hidden" name="type" value="images">
        <input type="file" accept="image/png, image/jpeg, image/gif" name="file" multiple/>
        <br>
        <input type="submit">
    </form>
</div>
<div class="col-lg-4">
    <br>
    <form id="lot-form" action="lot" method="post">
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-tags"></span></span>
            <input type="text" id="title" class="form-control" name="title"
                   placeholder="Lot title" required>
        </div>
        <br>
        <div class="input-group">
            <input type="text" id="price" class="form-control" name="price"
                   placeholder="Price" required>
            <span class="input-group-addon">$</span>
        </div>
        <br>
        <div class="form-group">
            <label class="lot_label_left">Category</label>
            <select class="form-control" id="category" name="categoryId" required>
                <c:forEach var="cat" items="${categories}" varStatus="Status">
                    <option name="categoryId" value="${cat.id}">
                            <c:out value="${cat.title}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-group">
            <label class="lot_label_left">Location</label>
            <select class="form-control" id="city" name="cityId" required>
                <c:forEach var="city" items="${cities}" varStatus="Status">
                    <option name="cityId" value="${city.id}">
                        <c:out value="${city.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-group">
            <textarea class="form-control" id="description" rows="3" name="description" placeholder="Lot description"
                   required></textarea>
        </div>
        <br>
        <button class="btn btn-default" id="submit_button" style="margin-left: 40%;"
                type="submit" >Create</button>
    </form>

    <form id="update-form" action="lot" method="post" style="display: none;">
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-tags"></span></span>
            <input type="text" id="new-title" class="form-control" name="title" placeholder="Lot title" required>
        </div>
        <br>
        <div class="input-group">
            <input type="text" id="new-price" class="form-control" name="price" placeholder="Price" required>
            <span class="input-group-addon">$</span>
        </div>
        <br>
        <div class="form-group">
            <label class="lot_label_left">Category</label>
            <select class="form-control" id="new-category" name="categoryId" required>
                <c:forEach var="cat" items="${categories}" varStatus="Status">
                    <option name="categoryId" value="${cat.id}">
                        <c:out value="${cat.title}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-group">
            <label class="lot_label_left">Location</label>
            <select class="form-control" id="new-city" name="cityId" required>
                <c:forEach var="city" items="${cities}" varStatus="Status">
                    <option name="cityId" value="${city.id}">
                        <c:out value="${city.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-group">
            <textarea class="form-control" id="new-description" rows="3" name="description" placeholder="Lot description"
                      required></textarea>
        </div>
        <br>
        <button class="btn btn-default" id="update_button" style="margin-left: 40%;"
                type="submit" >Edit</button>
    </form>
</div>

<div class="col-lg-4"></div>

<script type="text/javascript" src="../resources/js/lotsScripts.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
