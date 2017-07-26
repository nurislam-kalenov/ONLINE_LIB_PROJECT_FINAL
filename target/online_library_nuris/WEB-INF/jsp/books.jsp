<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.04.2017
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="i18n">
    <fmt:message key="book.info.genre" var="book_info_genre"/>
    <fmt:message key="book.info.search" var="book_info_search"/>
    <fmt:message key="book.info.read.more" var="book_info_read_more"/>
    <fmt:message key="book.info.pholder.search" var="book_info_ph_search"/>
</fmt:bundle>

<my:designPattern role="${sessionScope.role}">

    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">${book_info_genre}</h3>
                    </div>

                    <ul class="list-group">
                        <c:forEach items="${genres}" var="genre">
                            <a href="books?genre_id=${genre.id}" class="list-group-item">${genre.name}</a>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <div class="col-md-8">
                <form role="form">
                    <div class="row">
                        <div class="form-group">
                            <div class="input-group">
                                <input class="form-control" type="text" name="search" placeholder=${book_info_ph_search}/>
                                <span class="input-group-btn">
                            <button class="btn btn-success" type="submit"><span style="margin-left:10px;">${book_info_search}</span></button>
                        </span>
                                </span>
                            </div>
                        </div>
                    </div>


                    <c:forEach items="${books}" var="book">
                    <hr>
                    <h1><font color="#5f9ea0"><c:out value="${book.name}"/></font></h1>
                    <p><c:out value="${book.description}"/></p>
                    <div>
                        <div class="more label"><a href="aboutBook?book_id=${book.id}">${book_info_read_more}</a></div>
                    </div>
                    <hr>
                    </c:forEach>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-5">
                <my:listPages currentPage="${currentPage}" noOfPages="${noOfPages}" books_url="books?page="/>
            </div>
        </div>
    </div>


</my:designPattern>


