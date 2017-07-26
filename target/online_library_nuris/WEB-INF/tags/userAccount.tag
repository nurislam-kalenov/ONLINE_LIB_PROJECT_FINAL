<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="role" required="true" rtexprvalue="true" type="java.lang.String" %>
<c:url var="profile_edit_url" value="/kz/profileEdit"/>
<c:url var="personal_date_edit_url" value="/kz/personalDataEdit"/>

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
    <fmt:message key="profile.button.edit" var="loc_edit"/>
    <fmt:message key="register.button.home" var="loc_back"/>
    <fmt:message key="profile.button.delete" var="loc_delete"/>
</fmt:bundle>

<c:if test="${role.equals('user')}">
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
                                <p>${loc_status} : ${customer_info.customerRole.name} <img src="../../images/user.png"
                                                                                           width="40" height="40" alt="lorem"></p>
                            </div>

                        </div>

                    </div>

                </div>
                <a href="${profile_edit_url}" class="btn btn-warning col-md-6 pull-right" role="button">${loc_edit}</a>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${role.equals('admin')}">
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
                    <div class="panel-body libreStatsPanelBody collapse in" id="uniqueVisitor_admin">
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
                <a href="${personal_date_edit_url}" class="btn btn-warning col-md-6 pull-right"
                   role="button">${loc_edit}</a>

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
                    <div class="panel-body libreStatsPanelBody collapse in" id="activity_admin">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${loc_email} : ${customer_info.email}</p>
                                <p>${loc_register_date}: ${customer_info.registerDate} </p>
                                <p>${loc_status} : ${customer_info.customerRole.name} <img src="../../images/admin.png"
                                                                                           width="40" height="40" alt="lorem"></p>
                            </div>
                        </div>
                    </div>
                </div>

                <form action="deleteProfile" method="POST">
                    <input type="hidden" name="delete_id" value="${customer_info.id}">
                    <button id="submit" name="submit" class="btn btn-danger col-md-6 pull-right">${loc_delete}</button>
                </form>

                <a href="${profile_edit_url}" class="btn btn-warning col-md-6 pull-right" role="button">${loc_edit}</a>

            </div>
        </div>
    </div>
</c:if>