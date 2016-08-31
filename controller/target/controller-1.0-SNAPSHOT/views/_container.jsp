<body>

    <div class="">
        <c:forEach var="lot" items="${lots}" varStatus="Status">
                <c:url var="lotUrl" value="/lot?id=${lot.id}" />
                <div class="col-lg-3">
                    <div class="thumbnail" style="height: 550px;">
                        <c:if test="${!empty lot.images}">
                            <c:forEach var="img" items="${lot.images}">
                                <c:if test="${img.is_cover()}">
                                    <a href="${lotUrl}">
                                        <img src="data:image/jpg;base64,${img.file}" alt="${lot.title}"
                                        height="200px">
                                    </a>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty lot.images}">
                            <a href="${lotUrl}">
                                <img src="http://podushki.kz/images/stories/1002100_34908873.jpg" alt="...">
                            </a>
                        </c:if>
                        <div class="caption">
                            <h3 style="text-align: center;"><c:out value="${lot.title}"/></h3>
                            <p class="price">
                                $${lot.price}
                            </p>
                            <security:authorize access="hasAuthority('ADMIN')">
                                <p style="text-align: center;"><a href="/edit?id=${lot.id}">Edit</a></p>
                            </security:authorize>
                        </div>
                    </div>
                </div>

        </c:forEach>
    </div>

</body>
