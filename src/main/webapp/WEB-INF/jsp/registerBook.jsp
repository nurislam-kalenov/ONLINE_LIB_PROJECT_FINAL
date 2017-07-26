<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.04.2017
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="home_url" value="/kz/welcome"/>

<fmt:bundle basename="i18n">
    <fmt:message key="register.firstname" var="first_name"/>
    <fmt:message key="register.lastname" var="last_name"/>
    <fmt:message key="register.middlename" var="middle_name"/>
    <fmt:message key="register.book.name" var="book_name"/>
    <fmt:message key="register.book.description" var="description"/>
    <fmt:message key="register.book.genre" var="book_genre"/>
    <fmt:message key="register.book.count" var="book_count"/>
    <fmt:message key="register.book.price" var="book_price"/>
    <fmt:message key="register.book.year" var="book_year"/>
    <fmt:message key="register.book.legend.about.book" var="legend_about_book"/>
    <fmt:message key="register.book.legend.about.author" var="legend_about_author"/>
    <fmt:message key="register.book.legend.economic.part" var="legend_econ_part"/>
    <fmt:message key="register.book.pholder.description" var="ph_about_book"/>
    <fmt:message key="register.book.pholder.isbn" var="ph_isbn"/>
    <fmt:message key="register.book.pholder.description" var="ph_descrip_book"/>
    <fmt:message key="register.book.pholder.year" var="ph_year_book"/>
    <fmt:message key="register.book.pholder.name" var="ph_name_book"/>
    <fmt:message key="register.button.home" var="button_back"/>
    <fmt:message key="register.book.button.save" var="button_save"/>

    <fmt:message key="register.book.error.title" var="error_title"/>
    <fmt:message key="register.book.error.count" var="error_count"/>
    <fmt:message key="register.book.error.date" var="error_date"/>
    <fmt:message key="register.book.error.description" var="error_description"/>
    <fmt:message key="register.book.error.isbn" var="error_isbn"/>
    <fmt:message key="register.book.error.price" var="error_price"/>
    <fmt:message key="register.error.name" var ="error_name"/>


</fmt:bundle>


<my:designPattern role="admin">
    <div class="row">
        <div class="col-md-8 col-md-offset-1">
            <form class="form-horizontal" action="registerBook" method="POST">
                <fieldset>
                    <legend><font color="teal">${legend_about_book}</font></legend>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <label>${book_name}</label>
                            <input type="text" name="book_name" placeholder="${ph_name_book}" class="form-control">
                            <c:if test="${book_name_error eq true}">
                                <p class="alert alert-warning">${error_title}</p>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-4">
                            <label>ISBN</label>
                            <input type="text" name="isbn" placeholder="${ph_isbn}" class="form-control">
                            <c:if test="${isbn_error eq true}">
                                <p class="alert alert-warning">${error_isbn}</p>
                            </c:if>
                        </div>

                        <div class="col-sm-3">
                            <label>${book_year}</label>
                            <input type="text" name="year" placeholder="${ph_year_book}" class="form-control">
                            <c:if test="${ year_error eq true}">
                                <p class="alert alert-warning">${error_date}</p>
                            </c:if>
                        </div>

                        <div class="col-sm-3">
                            <label>${book_genre}</label>
                            <select id="genre_id" name="genre_name" class="form-control input-md">
                                <option disabled></option>
                                <c:forEach items="${genreList}" var="genre">
                                    <option value="${genre.id}">${genre.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-10">
                            <label>${description}</label>
                            <textarea placeholder="${ph_descrip_book}" name="description" rows="4"
                                      class="form-control"></textarea>
                            <c:if test="${ description_error eq true}">
                                <p class="alert alert-warning">${error_description}</p>
                            </c:if>
                        </div>
                    </div>

                    <legend><font color="teal">${legend_econ_part}</font></legend>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <label>${book_count}</label>
                            <input type="text" name="book_amount" class="form-control"  minlength="1" maxlength="4">
                            <c:if test="${ book_amount_error eq true}">
                                <p class="alert alert-warning">${error_count}</p>
                            </c:if>
                        </div>

                        <div class="col-sm-5">
                            <label>${book_price}</label>
                            <input type="text" name="book_price" class="form-control" minlength="3" maxlength="6">
                            <c:if test="${book_price_error eq true}">
                                <p class="alert alert-warning">${error_price}</p>
                            </c:if>
                        </div>
                    </div>

                    <legend><font color="teal">${legend_about_author}</font></legend>
                    <div class="form-group">
                        <div class="col-sm-3">
                            <label>${first_name}</label>
                            <input type="text" name="first_name" class="form-control">
                            <c:if test="${first_name_error eq true}">
                                <p class="alert alert-warning">${first_name} ${error_name}</p>
                            </c:if>
                        </div>

                        <div class="col-sm-4">
                            <label>${last_name}</label>
                            <input type="text" name="last_name" class="form-control">
                            <c:if test="${ last_name_error eq true}">
                                <p class="alert alert-warning">${last_name} ${error_name}</p>
                            </c:if>
                        </div>

                        <div class="col-sm-3">
                            <label>${middle_name}</label>
                            <input type="text" name="middle_name" class="form-control">
                            <c:if test="${middle_name_error eq true}">
                                <p class="alert alert-warning">${middle_name} ${error_name}</p>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-5 col-sm-offset-1">
                            <div class="pull-right">
                                <a href="${home_url}" class="btn btn-info" role="button">${button_back}</a>
                                <button type="submit" class="btn btn-primary">${button_save}</button>
                            </div>
                        </div>
                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</my:designPattern>
