<body>
    <form action="/cities" method="post">
        <br>
        <div class="input-group">
            <input type="text" id="price" class="form-control" name="name" placeholder="New city" required>
        </div>
        <button class="btn btn-default" type="submit">Add City</button>
    </form>
    <br>
    <ul>
        <c:forEach var="city" items="${cities}">
            <li>
                <c:out value="${city.name}" />
                <form  action="/cities" method="post">
                    <input type="hidden" name="city_id" value="${city.id}">
                    <input type="hidden" name="action" value="delete">
                    <button type="submit">
                        <span  class="glyphicon glyphicon-remove"></span>
                    </button>
                </form>
            </li>
        </c:forEach>
    </ul>
</body>
