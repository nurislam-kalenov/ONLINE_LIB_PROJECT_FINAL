<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="role" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<c:url var="register_url" value="/kz/register"/>
<c:url var="books_url" value="/kz/books"/>
<c:url var="logout_url" value="/kz/logout"/>
<c:url var="account_url" value="/kz/account"/>
<c:url var="customer_book_url" value="/kz/deptCustomerBook"/>
<c:url var="management_url" value="/kz/management"/>
<c:url var="readers_url" value="/kz/readers"/>
<c:url var="basket_url" value="/kz/basket"/>

<html lang=en>
<head>
    <meta charset="UTF-8">

    <fmt:bundle basename="i18n">
        <fmt:message key="navbar.books" var="books"/>
        <fmt:message key="navbar.logout" var="logout"/>
        <fmt:message key="navbar.management" var="management"/>
        <fmt:message key="navbar.readers" var="readers"/>
        <fmt:message key="navbar.account" var="account"/>
        <fmt:message key="navbar.mybooks" var="my_books"/>
        <fmt:message key="navbar.hello" var="hello"/>
    </fmt:bundle>

    <script>
        <jsp:directive.include file="/WEB-INF/js/register.js"/>
    </script>

    <style>
        <jsp:directive.include file="/WEB-INF/css/bootstrap.min.css"/>
        <jsp:directive.include file="/WEB-INF/css/header.css"/>
        body {
            background-image: url("../../images/bg.png");
            background-repeat: repeat;
        }
    </style>

    <title>Online Library</title>
</head>

<body>
<nav class="navbar navbar-default" role="navigation">
    <c:if test="${role.equals('admin')}">
        <div class="container">
            <div class="navbar-header">
                <div class="navbar-brand navbar-brand-centered">Online LIB
                </div>
            </div>
            <div class="collapse navbar-collapse" id="navbar-brand-admin">
                <ul class="nav navbar-nav">
                    <li><a href="${books_url}">${books}</a></li>
                    <li><a href="${readers_url}">${readers}</a></li>
                    <li><a href="${management_url}">${management}</a></li>
                    <li><a href="${account_url}">${account}</a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">${hello}, ${sessionScope.name}!</a></li>
                    <li><a href="set-language?lang=en"><img src="../../images/en.png"
                                                            width="21" height="21" alt="lorem"></a></li>
                    <li><a href="set-language?lang=ru"><img src="../../images/ru.png"
                                                            width="21" height="21" alt="lorem"></a></li>
                    <li>
                        <form action="${logout_url}" class="button_header" method="post">
                            <input type="submit" value="${logout}"/>
                        </form>
                    </li>
                </ul>
            </div>

        </div>
    </c:if>
    <c:if test="${role.equals('user')}">
        <div class="container">
            <div class="navbar-header">
                <div class="navbar-brand navbar-brand-centered">Online LIB</div>
            </div>
            <div class="collapse navbar-collapse" id="navbar-brand-customer">

                <ul class="nav navbar-nav">
                    <li><a href="${books_url}">${books}</a></li>
                    <li><a href="${customer_book_url}">${my_books}</a></li>
                    <li><a href="${account_url}">${account}</a></li>

                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="${basket_url}"><img src="../../images/basket.png"
                                                     width="21" height="21" alt="lorem"></a></li>
                    <li><a href="#">${hello}, ${sessionScope.name}!</a></li>

                    <li><a href="set-language?lang=en"><img src="../../images/en.png"
                                                            width="21" height="21" alt="lorem"></a></li>
                    <li><a href="set-language?lang=ru"><img src="../../images/ru.png"
                                                            width="21" height="21" alt="lorem"></a></li>
                    <li>
                        <form action="${logout_url}" class="button_header" method="post">
                            <input type="submit" value="${logout}"/>
                        </form>
                    </li>
                </ul>
            </div>

        </div>
    </c:if>
    <c:if test="${role.equals('guest')}">
        <div class="container">
            <div class="navbar-header">
                <div class="navbar-brand navbar-brand-centered">
                    Online LIB
                </div>
            </div>
            <div class="collapse navbar-collapse" id="navbar-brand-centered">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="set-language?lang=en"><img src="../../images/en.png"
                                                            width="21" height="21" alt="lorem"></a></li>
                    <li><a href="set-language?lang=ru"><img src="../../images/ru.png"
                                                            width="21" height="21" alt="lorem"></a></li>
                </ul>
            </div>

        </div>
    </c:if>
</nav>

<div id="body" style="height: 100%">
    <jsp:doBody/>
</div>

<div id="footer" style="flex: 0 0 auto">

    <c:if test="${role.equals('admin') || role.equals('user')}">
        <footer class="container-fluid text-center bg-lightgray">
            <div class="copyrights" style="margin-top:25px;">
                <p>Epam Systems Lab21 © 2017, All Rights Reserved
                    <br>
                    <span>Web Design By: Kalenov Nurislam</span></p>
                <p><a href="https://vk.com/chaknuris" target="_blank">VKontakte<i class="fa fa-linkedin-square"
                                                                                  aria-hidden="true"></i> </a></p>
            </div>
        </footer>
    </c:if>
    <jsp:invoke fragment="footer"/>
</div>
</body>

</html>