<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.03.2017
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="register_url" value="/kz/register"/>
<c:url var="home_url" value="/kz/welcome"/>

<fmt:bundle basename="i18n">

    <fmt:message key="register.firstname" var="first_name"/>
    <fmt:message key="register.lastname" var="last_name"/>
    <fmt:message key="register.middlename" var="middle_name"/>
    <fmt:message key="register.birthday" var="birthday"/>
    <fmt:message key="register.address" var="input_address"/>
    <fmt:message key="register.email" var="email"/>
    <fmt:message key="register.password" var="password"/>
    <fmt:message key="register.confirm.password" var="confirm_password"/>
    <fmt:message key="register.city" var="choise_city"/>
    <fmt:message key="register.phone" var="phone"/>

    <fmt:message key="register.pholder.phone" var="pholder_phone"/>
    <fmt:message key="register.pholder.address" var="pholder_address"/>
    <fmt:message key="register.pholder.email" var="pholder_email"/>
    <fmt:message key="register.pholder.firstname" var="pholder_first_name"/>
    <fmt:message key="register.pholder.lastname" var="pholder_last_name"/>
    <fmt:message key="register.pholder.middlename" var="pholder_middle_name"/>
    <fmt:message key="register.button.registor" var="button_registor"/>
    <fmt:message key="register.button.home" var="button_home"/>
    <fmt:message key="register.head" var="registration"/>
    <fmt:message key="register.hblock.password" var="hblock_password"/>
    <fmt:message key="register.hblock.confirm.password" var="hblock_confirm_password"/>

    <fmt:message key="register.error.date" var="error_date"/>
    <fmt:message key="register.error.name" var="error_name"/>
    <fmt:message key="register.error.password" var="error_password"/>
    <fmt:message key="register.error.repeat.password" var="error_re_password"/>
    <fmt:message key="register.error.address" var="error_address"/>
    <fmt:message key="register.error.phone" var="error_phone"/>
    <fmt:message key="register.error.email" var="error_email"/>

</fmt:bundle>

<my:designPattern role="guest">

    <form class="form-horizontal" action="${register_url}" method="POST">
        <fieldset>
            <legend>${registration}</legend>
            <div class="form-group">
                <label class="col-md-4 control-label" for="fn">${first_name}</label>
                <div class="col-md-4">

                    <input id="fn" name="first_name" placeholder="${pholder_first_name}" class="form-control input-md">
                    <c:if test="${first_name_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${first_name} ${error_name}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="ln">${last_name}</label>
                <div class="col-md-4">
                    <input id="ln" name="last_name" type="text" placeholder="${pholder_last_name}"
                           class="form-control input-md">
                    <c:if test="${last_name_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${last_name} ${error_name}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="mn">${middle_name}</label>
                <div class="col-md-4">
                    <input id="mn" name="middle_name" type="text" placeholder="${pholder_middle_name}"
                           class="form-control input-md">
                    <c:if test="${middle_name_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${middle_name} ${error_name}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label for="birthDate" class="col-md-4 control-label">${birthday}</label>
                <div class="col-md-4">
                    <input id="birthDate" name="birthday" type="text" placeholder="1995-12-31"
                           class="form-control input-md">
                    <c:if test="${birthday_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${error_date}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="phone">${phone}</label>
                <div class="col-md-4">
                    <input id="phone" name="phone" type="text" placeholder="${pholder_phone}"
                           class="form-control input-md">
                    <c:if test="${phone_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${error_phone}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="email">${email}</label>
                <div class="col-md-4">
                    <input type="text" id="email" name="email" placeholder="${pholder_email}"
                           class="form-control input-md">
                    <c:if test="${email_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${error_email}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="password">${password}</label>
                <div class="col-md-4">
                    <input type="password" id="password" name="password" class="form-control input-md">
                    <p class="help-block">${hblock_password}</p>
                    <c:if test="${password_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${error_password}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="password_confirm">${confirm_password}</label>
                <div class="col-md-4">
                    <input type="password" id="password_confirm" name="password_confirm" placeholder=""
                           class="form-control input-md">
                    <p class="help-block">${hblock_confirm_password}</p>
                    <c:if test="${re_password_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${error_re_password}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="city">${choise_city}</label>
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
                <label class="col-md-4 control-label" for="add">${input_address}</label>
                <div class="col-md-4">
                    <input id="add" name="address" type="text" placeholder="${pholder_address}"
                           class="form-control input-md">
                    <c:if test="${address_error eq true}">
                        <p class="alert alert-warning"
                           style="height: 30px;padding: 5px">${error_address}</p>
                    </c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="submit"></label>
                <div class="col-md-4">
                    <button id="submit" name="submit" class="btn btn-primary">${button_registor}</button>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <a href="${home_url}" class="btn btn-info" role="button">${button_home}</a>
                </div>
            </div>

        </fieldset>
    </form>
</my:designPattern>

