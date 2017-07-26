<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.04.2017
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="books_url" value="/kz/books"/>
<fmt:bundle basename="i18n">
    <fmt:message key="register.button.home" var="button_back"/>
    <fmt:message key="book.error.delete.head.warn" var="delete_error"/>
    <fmt:message key="book.error.delete.head.delete" var="warn_delete"/>
    <fmt:message key="book.error.delete.head.delete.book" var="warn_delete_book"/>
    <fmt:message key="book.error.delete.cause" var="delete_cause"/>
    <fmt:message key="book.error.delete.cause.answer" var="delete_cause_answer"/>
    <fmt:message key="book.error.delete.decision" var="delete_decision"/>
    <fmt:message key="book.error.delete.decision.first.answer" var="delete_decision_first_answer"/>
    <fmt:message key="book.error.delete.decision.second.answer" var="delete_decision_second_answer"/>
</fmt:bundle>
<my:designPattern role="${sessionScope.role}">

    <div class="container">
        <br>
        <br>
        <br>
        <br>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <div class="panel panel-danger">
                    <div class="panel-heading">
                        <h3 class="text-center">
                                ${delete_error}
                            <small>${warn_delete} <b>${warn_delete_book}</b></small>
                        </h3>
                    </div>
                    <div class="panel-body">
                        <p>${delete_cause}:</p>

                        <ul class="list-group">
                            <li class="list-group-item">${delete_cause_answer}
                            </li>
                        </ul>
                        <p>${delete_decision}</p>
                        <ul class="list-group">
                            <li class="list-group-item">${delete_decision_first_answer}</li>
                            <li class="list-group-item">${delete_decision_second_answer}</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                </div>
                <div class="col-md-4">
                    <a href="${books_url}" class="btn btn-info col-md-3" role="button">${button_back}</a>
                </div>
            </div>
        </div>
        </a>
    </div>
</my:designPattern>
