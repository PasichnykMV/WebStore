
<body>
<br>
<ul>
    <li><a href="/">All</a>
        <form action="/categories" method="post">
            <input type="text" name="title" placeholder="add category">
            <input type="hidden" name="parentId" value="0">
            <input type="submit">
        </form>
    </li>
<ul>
    <c:forEach var="cat" items="${categories}" varStatus="Status">
        <c:url var="categoryUrl" value="/" >
            <c:param name="category" value="${cat.id}" />
        </c:url>
        <c:if test="${cat.parentId eq ''}" >
            <c:if test="${empty cat.subcategories}">
                <li><a href="${categoryUrl}">${cat.title}</a>
                    <form action="/categories" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${cat.id}">
                        <button type="submit">delete</button>
                    </form>
                    <form action="/categories" method="post">
                        <input type="hidden" name="parentId" value="${cat.id}">
                        <input type="text" name="title" placeholder="add subcategory">
                        <input type="submit">
                    </form>
                </li>
            </c:if>
            <c:if test="${(!empty cat.subcategories) }">
                <li>
                    <a href="${categoryUrl}">${cat.title}</a>
                        <form action="/categories" method="post">
                            <input type="hidden" name="parentId" value="${cat.id}">
                            <input type="text" name="title" placeholder="add subcategory">
                            <input type="submit">
                        </form>
                    <ul>
                        <c:forEach var="subcat" items="${cat.subcategories}" varStatus="Status">
                            <c:url var="subcategoryUrl" value="/" >
                                <c:param name="category" value="${subcat.id}" />
                            </c:url>

                            <c:if test="${empty subcat.subcategories}">
                                <li>
                                    <a href="${subcategoryUrl}"><c:out value="${subcat.title}" /></a>
                                        <form action="/categories" method="post">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="id" value="${subcat.id}">
                                            <button type="submit">delete</button>
                                        </form>
                                        <form action="/categories" method="post">
                                            <input type="hidden" name="parentId" value="${subcat.id}">
                                            <input type="text" name="title" placeholder="add subcategory">
                                            <input type="submit">
                                        </form>
                                </li>
                            </c:if>
                            <c:if test="${!empty subcat.subcategories}">
                                <li>
                                    <a href="${subcategoryUrl}">
                                        <c:out value="${subcat.title}" />
                                    </a>
                                    <form action="/categories" method="post">
                                        <input type="hidden" name="parentId" value="${subcat.id}">
                                        <input type="text" name="title" placeholder="add subcategory">
                                        <input type="submit">
                                    </form>
                                    <ul>
                                        <c:forEach var="subcat1" items="${subcat.subcategories}" varStatus="Status">
                                            <c:url var="subcat1egoryUrl" value="/" >
                                                <c:param name="category" value="${subcat1.id}" />
                                            </c:url>

                                            <li>
                                                <a href="${subcat1egoryUrl}">
                                                    <c:out value="${subcat1.title}" />
                                                </a>
                                                <form action="/categories" method="post">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="id" value="${subcat1.id}">
                                                    <button type="submit">delete</button>
                                                </form>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </li>
            </c:if>
        </c:if>
    </c:forEach>
</ul>
</ul>
</body>