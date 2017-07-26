<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10.04.2017
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="account_url" value="/kz/account"/>

<fmt:bundle basename="i18n">
    <fmt:message key="personal.edit.passport" var="passport_edit"/>
    <fmt:message key="register.book.button.save" var="passport_save"/>
    <fmt:message key="register.button.home" var ="button_home"/>
</fmt:bundle>

<my:designPattern role="${sessionScope.role}">
    <form class="form-horizontal" action="personalDataEdit" method="POST">
    <fieldset>
    <legend>${passport_edit}</legend>
        <div class="form-group">
            <label class="col-md-4 control-label" for="fn"></label>
            <div class="col-md-4">
                <input id="fn" name="first_name" type="text" placeholder="${first_name}" class="form-control input-md">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="fn"></label>
            <div class="col-md-4">
                <input id="lname" name="last_name" type="text" placeholder="${last_name}" class="form-control input-md">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="fn"></label>
            <div class="col-md-4">
                <input id="mname" name="middle_name" type="text" placeholder="${middle_name}" class="form-control input-md">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="fn"></label>
            <div class="col-md-4">
                <input id="phone" name="phone" type="text" placeholder="${phone}" class="form-control input-md">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="fn"></label>
            <div class="col-md-4">
                <input id="bday" name="birthday" type="text" placeholder="${birthday}" class="form-control input-md">
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4 control-label" for="fn"></label>
            <div class="col-md-4">
                <input id="addr" name="address" type="text" placeholder="${address}" class="form-control input-md">
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-4 control-label" for="city"></label>
            <div class="col-md-4">
                <select id="city" name="city" class="form-control input-md">
                    <option disabled></option>
                    <c:forEach items="${cityList}" var="city">
                        <option value="${city.id}">${city.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4 control-label" for="submit"></label>
            <div class="col-md-4">
                <button id="submit" name="submit" class="btn btn-primary">${passport_save}</button>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-4 control-label"></label>
            <div class="col-md-4">
                <a href="${account_url}" class="btn btn-info" role="button">${button_home}</a>
            </div>
        </div>
    </fieldset>
    </form>
</my:designPattern>
