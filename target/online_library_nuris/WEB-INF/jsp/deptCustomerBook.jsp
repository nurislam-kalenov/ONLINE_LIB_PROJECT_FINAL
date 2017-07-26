<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 17.04.2017
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="i18n">
    <fmt:message key="register.book.name" var="book_name"/>
    <fmt:message key="register.book.price" var="book_price"/>
    <fmt:message key="customer.book.took" var="book_took"/>
    <fmt:message key="customer.book.state" var="book_state"/>
    <fmt:message key="customer.book.currency" var="book_curr"/>
    <fmt:message key="customer.book.button.onhands" var="book_onhands"/>
    <fmt:message key="customer.book.button.wait" var="book_wait"/>
    <fmt:message key="customer.book.button.story" var="book_story"/>
    <fmt:message key="customer.book.button.return" var="book_return"/>
</fmt:bundle>

<my:designPattern role="user">
    <div class="row">
        <div class="col-md-1">
        </div>
        <div class="col-md-1" style="margin-top: 50px">
            <a href="deptCustomerBook" class="btn btn-danger btn-circle btn-xl" role="button">${book_onhands}</a>
            <a href="returnCustomerBook?active=false" class="btn btn-warning btn-circle btn-xl" role="button">${book_wait}</a>
            <a href="returnCustomerBook?active=true" class="btn btn-success danger btn-circle btn-xl" role="button">${book_story}</a>
        </div>
        <div class="col-md-8">
    <div class="wrapper">
        <table id="acrylic">
            <thead>
            <tr>
                <th class="col-md-2">${book_name}</th>
                <th class="col-md-2">${book_price}</th>
                <th class="col-md-2">${book_took}</th>
                <th class="col-md-2">${book_state}</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${customer_book}" var="book">
                <tr>
                    <td>${book.bookInfo.book.name}</td>
                    <td>${book.bookInfo.price} ${book_curr}</td>
                    <td>${book.startDate}</td>
                    <td>
                        <form action="returnBook" method="POST">
                            <input type="hidden" name="return_book" value="${book.id}">
                            <button id="submit" name="submit" class="btn btn-danger" >${book_return}</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
        </div>
    </div>
</my:designPattern>