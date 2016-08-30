<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">

        <div class="navbar-header">
            <a class="navbar-brand brand" href="/">SombraWebStore</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-right">
                <c:url var="loginUrl" value="/login"/>
                <c:url var="logoutUrl" value="/logout" />
                <c:url var="adminPage" value="/admin" />
                <c:url var="profileUrl" value="/profile" />
                <c:url var="cartUrl" value="/order" />

                <security:authorize access="hasAuthority('ADMIN')">
                    <li><a href="${adminPage}">Admin CMS</a></li>
                </security:authorize>
                <security:authorize access="hasAuthority('USER')">
                    <li><a href="${profileUrl}">Profile <span class="glyphicon glyphicon-user"></span></a></li>
                    <li><a href="${cartUrl}">Cart <span class="badge" id="cart-badge">
                        <c:out value="${lotsCount}" />
                    </span></a></li>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <li><a href="${logoutUrl}">Log out <span class="glyphicon glyphicon-log-out"></span></a></li>
                </security:authorize>
                <security:authorize access="isAnonymous()">
                    <li><a href="${loginUrl}">Log in</a></li>
                    <li><a href="/registration">Sign up</a></li>
                </security:authorize>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">Language<span class="caret"></span>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${pageContext.request.contextPath}?${pageContext.request.queryString}&locale=en">
                                English
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}?${pageContext.request.queryString}&locale=uk">
                            Ukrainian
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
