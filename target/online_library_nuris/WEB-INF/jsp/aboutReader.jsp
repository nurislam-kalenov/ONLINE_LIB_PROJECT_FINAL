<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 18.04.2017
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="i18n">
    <fmt:message key="register.firstname" var="loc_first_name"/>
    <fmt:message key="register.lastname" var="loc_last_name"/>
    <fmt:message key="register.middlename" var="loc_middle_name"/>
    <fmt:message key="register.birthday" var="loc_birthday"/>
    <fmt:message key="register.address" var="loc_address"/>
    <fmt:message key="register.email" var="loc_email"/>
    <fmt:message key="register.phone" var="loc_phone"/>
    <fmt:message key="register.city" var="loc_choise_city"/>
    <fmt:message key="profile.data.passport" var="loc_passport"/>
    <fmt:message key="profile.data.register.date" var="loc_register_date"/>
    <fmt:message key="profile.data.user" var="loc_user"/>
    <fmt:message key="profile.data.user.status" var="loc_status"/>
    <fmt:message key="register.button.home" var="loc_back"/>
    <fmt:message key="profile.button.delete" var="loc_delete"/>
    <fmt:message key="about.reader.onhand" var="loc_onhand"/>
</fmt:bundle>

<my:designPattern role="admin">
    <div class="col-md-12 column ">
        <div class="row clearfix ">
            <div class="col-md-3 column">
                <div class="panel panel-default">
                    <div class="panel-heading libreStatsPanelHeading">
                        <div class="panel-title">
                            <span>${loc_passport}</span>
                            <a class="fa fa-caret-down pull-right libreStatsPanelHeadingIcon" href="#uniqueVisitor"
                               data-toggle="collapse"></a>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in" id="uniqueVisitor">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${loc_first_name}: ${customer_info.person.firstName}</p>
                                <p>${loc_last_name}: ${customer_info.person.lastName} </p>
                                <p>${loc_middle_name} : ${customer_info.person.middleName}</p>
                                <p>${loc_phone} : ${customer_info.person.phone}</p>
                                <p>${loc_address} : ${customer_info.person.address}</p>
                                <p>${loc_choise_city}: ${customer_info.person.city.name}</p>
                                <p>${loc_birthday} : ${customer_info.person.birthday}</p>
                            </div>
                            <div class="col-md-4 column">
                                <span class="pull-right fa fa-user fa-5x libreStatsIcon"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3 column">
                <div class="panel panel-default">
                    <div class="panel-heading libreStatsPanelHeading">
                        <div class="panel-title">
                            <span>${loc_user}</span>
                            <a class="fa fa-caret-down pull-right libreStatsPanelHeadingIcon" href="#activity"
                               data-toggle="collapse"></a>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in" id="activity">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${loc_email} : ${customer_info.email}</p>
                                <p>${loc_register_date}: ${customer_info.registerDate} </p>
                                <p>${loc_status} : ${customer_info.customerRole.name}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <form action="deleteProfile" method="POST">
                    <input type="hidden" name="delete_id" value="${customer_info.id}">
                    <button id="submit" name="submit" class="btn btn-danger col-md-6 pull-right">${loc_delete}</button>
                </form>
            </div>

            <div class="col-md-3 column">
                <div class="panel panel-default">
                    <div class="panel-heading libreStatsPanelHeading">
                        <div class="panel-title">
                            <span>${loc_onhand}</span>
                            <a class="fa fa-caret-down pull-right libreStatsPanelHeadingIcon" href="#activity"
                               data-toggle="collapse"></a>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in" id="actiggvity">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <c:forEach items="${transactions}" var="tran">
                                    <p>${tran.bookInfo.book.name} , ${tran.startDate}</p>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</my:designPattern>


