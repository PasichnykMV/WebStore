<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<body>
    <div class="col-md-12 col-lg-12 table_container">
        <br>
        <table class="table table table-hover" id="datatable">
            <thead>
            <tr>
                <th></th>
                <th>Title</th>
                <th>Description</th>
                <th>Location</th>
                <th>Category</th>
                <th>Price</th>
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
                                        width="400px" height="300px">
                                    </a>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </td>
                    <td><a href="/lot?id=${lot.id}">${lot.title}</a></td>
                    <td>${lot.description}</td>
                    <td>${lot.city.name}</td>
                    <td>${lot.category.title}</td>
                    <td>${lot.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

    <%@include file="_tableScripts.jsp"%>

</body>

