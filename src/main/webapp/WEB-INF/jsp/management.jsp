<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.04.2017
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="i18n">
    <fmt:message key="register.book.name" var="book_name"/>
    <fmt:message key="register.book.price" var="book_price"/>
    <fmt:message key="register.firstname" var="first_name"/>
    <fmt:message key="register.lastname" var="last_name"/>
    <fmt:message key="register.email" var ="email"/>
    <fmt:message key="management.book.received" var ="book_recived"/>
    <fmt:message key="management.book.processed" var ="book_proccesed"/>
    <fmt:message key="management.book.button.active" var ="book_active"/>
    <fmt:message key="management.book.button.inactive" var ="book_inactive"/>
    <fmt:message key="management.book.button.confirmed" var ="book_conf"/>
    <fmt:message key="management.book.button.approve" var ="book_approve"/>
    <fmt:message key="customer.book.state" var ="book_state"/>

</fmt:bundle>

<my:designPattern role="${sessionScope.role}">
    <div class="row">
        <div class="col-md-1">
        </div>
        <div class="col-md-1" style="margin-top: 50px">
            <a href="management?active=false" class="btn btn-success btn-circle btn-xl" role="button">${book_active}</a>
            <a href="management?active=true" class="btn btn-warning danger btn-circle btn-xl" role="button">${book_inactive}</a>
            <a href="registerBook" class="btn btn-info danger btn-circle btn-xlll" style="font-size: 30px" role="button">+</a>
        </div>

        <div class="col-md-8">
            <div class="wrapper">
                <table id="acrylic">
                    <c:if test="${isActiveState eq false}">
                        <thead>
                        <tr>
                            <th class="col-md-2">${book_name}</th>
                            <th class="col-md-2">${book_recived}</th>
                            <th class="col-md-2">${first_name}</th>
                            <th class="col-md-2">${last_name}</th>
                            <th class="col-md-3">${email}</th>
                            <th class="col-md-2">${book_state}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${managements}" var="manage">
                            <tr>
                                <td>${manage.transaction.bookInfo.book.name}</td>
                                <td>${manage.transaction.endDate}</td>
                                <td>${manage.transaction.customer.person.firstName}</td>
                                <td>${manage.transaction.customer.person.lastName}</td>
                                <td>${manage.transaction.customer.email}</td>
                                <td>
                                    <form action="adminReturnBook" method="POST">
                                        <input type="hidden" name="management_id" value="${manage.id}">
                                        <button id="submit" name="submit" class="btn btn-danger">${book_approve}</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </c:if>

                    <c:if test="${isActiveState eq true}">
                        <thead>
                        <tr>
                            <th class="col-md-2">${book_name}</th>
                            <th class="col-md-2">${book_recived}</th>
                            <th class="col-md-2">${first_name}</th>
                            <th class="col-md-2">${last_name}</th>
                            <th class="col-md-3">${email}</th>
                            <th class="col-md-2">${book_proccesed}</th>
                            <th class="col-md-2">${book_state}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${managements}" var="manage">
                            <tr>
                                <td>${manage.transaction.bookInfo.book.name}</td>
                                <td>${manage.transaction.endDate}</td>
                                <td>${manage.transaction.customer.person.firstName}</td>
                                <td>${manage.transaction.customer.person.lastName}</td>
                                <td>${manage.transaction.customer.email}</td>
                                <td>${manage.returnDate}</td>
                                <td>
                                    <button id="button1id" name="button1id"
                                            class="btn btn-success">${book_conf}</button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </c:if>
                </table>
            </div>
            <div class="col-md-10">
                <my:listPages currentPage="${currentPage}" noOfPages="${noOfPages}" books_url="management?page="/>
            </div>
        </div>
    </div>
</my:designPattern>