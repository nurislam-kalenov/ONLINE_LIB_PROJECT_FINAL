<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 18.04.2017
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:bundle basename="i18n">
    <fmt:message key="register.firstname" var="loc_first_name"/>
    <fmt:message key="register.lastname" var="loc_last_name"/>
    <fmt:message key="register.email" var="loc_email"/>
    <fmt:message key="readers.register.date" var="loc_register_date"/>
    <fmt:message key="book.info.read.more" var="loc_read_more"/>
    <fmt:message key="readers.register.action" var="loc_action"/>

</fmt:bundle>
<my:designPattern role="admin">
    <div class="container">
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-4">
                <table class="table table-striped custab">
                    <thead>
                    <tr>
                        <th class="col-md-2">${loc_first_name}</th>
                        <th class="col-md-2">${loc_last_name}</th>
                        <th class="col-md-2">${loc_email}</th>
                        <th class="col-md-2">${loc_register_date}</th>
                        <th class="col-md-4">${loc_action}</th>
                    </tr>
                    </thead>
                    <c:forEach items="${readers}" var="reader">
                        <tr>
                            <td>${reader.person.firstName}</td>
                            <td>${reader.person.lastName}</td>
                            <td>${reader.email}</td>
                            <td>${reader.registerDate}</td>
                            <td class="text-center"><a class='btn btn-info btn-xs' href="aboutReader?customer_id=${reader.id}">${loc_read_more}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-4">
                <my:listPages currentPage="${currentPage}" noOfPages="${noOfPages}" books_url="readers?page="/>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <c:if test="${not empty delete_error}">
            <div class="alert alert-danger fade in"><p>Ошибочка</p></div>
        </c:if>
    </div>
</my:designPattern>