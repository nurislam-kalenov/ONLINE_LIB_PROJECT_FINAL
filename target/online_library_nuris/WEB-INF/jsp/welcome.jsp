<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 29.03.2017
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="register_url" value="/kz/register"/>
<c:url var="login_url" value="/kz/login"/>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
    <fmt:message key="authentication.signin" var="sign_in"/>
    <fmt:message key="authentication.login" var="login"/>
    <fmt:message key="register.button.registor" var="registration"/>
    <fmt:message key="register.email" var="email"/>
    <fmt:message key="register.password" var="password"/>
    <fmt:message key="register.password" var="password"/>
    <fmt:message key="login.error" var="login_error_aut"/>

</fmt:bundle>

<my:designPattern role="guest">
    <div class="login-box">
        <form id="loginForm" action="${login_url}" method="POST">
            <div class="panel panel-default">
                <div class="panel-heading"><b>${sign_in}</b></div>
                <div class="panel-body">
                    <div class="form-group has-primary has-feedback">
                        <label class="control-label" for="login">${login}<span
                                class="regForm text-danger">*</span></label>
                        <input type="text" class="form-control" name="login" id="login" aria-describedby="login">
                    </div>
                    <div class="form-group has-primary has-feedback">
                        <label class="control-label" for="password">${password} <span
                                class="regForm text-danger">*</span></label>
                        <input type="password" class="form-control" name="password" id="password"
                               aria-describedby="password">
                    </div>
                </div>
                <div class="panel-footer">
                    <input type="submit" class="btn btn-success" id="goToChat" value="${sign_in}"/>
                    <a class="btn btn-warning pull-right" href="${register_url}">${registration}</a>
                </div>
                <c:if test="${not empty login_error}">
                    <p class="alert alert-warning"
                       style="height: 30px;padding: 5px">${login_error_aut}</p>
                </c:if>
            </div>
        </form>
    </div>

</my:designPattern>

<footer class="container-fluid text-center bg-lightgray">
    <div class="copyrights" style="margin-top:25px;">
        <p>Epam Systems Lab21 © 2017, All Rights Reserved
            <br>
            <span>Web Design By: Kalenov Nurislam</span></p>
        <p><a href="https://vk.com/chaknuris" target="_blank">VKontakte<i class="fa fa-linkedin-square"
                                                                          aria-hidden="true"></i> </a></p>
    </div>
</footer>