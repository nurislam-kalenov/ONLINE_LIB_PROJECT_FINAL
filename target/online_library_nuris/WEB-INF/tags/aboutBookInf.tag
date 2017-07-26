<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="books_url" value="/kz/books"/>

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
    <fmt:message key="register.button.home" var="button_back"/>
    <fmt:message key="register.book.button.save" var="button_save"/>
    <fmt:message key="about.book.button.card" var="button_card"/>
    <fmt:message key="about.book.button.take" var="button_take"/>
    <fmt:message key="about.book.button.delete" var="button_delete"/>
    <fmt:message key="profile.button.edit" var="button_edit"/>
    <fmt:message key="book.info.notify.book.already.taken" var="notif_already_taken"/>
    <fmt:message key="book.info.notify.book.count" var="notif_count"/>
    <fmt:message key="book.info.notify.book.over" var="notif_over"/>
</fmt:bundle>

<c:if test="${role.equals('admin')}">
    <div class="col-md-12 column ">
        <div class="row clearfix ">
            <div class="col-md-3 column">
                <div class="panel panel-default">
                    <div class="panel-heading libreStatsPanelHeading">
                        <div class="panel-title">
                            <span>${legend_about_book}</span>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${book_name}: ${book_info.book.name}</p>
                                <p>${book_genre}: ${book_info.book.genre.name}</p>
                                <p>${book_year}: ${book_info.book.date}</p>
                                <p>ISBN:${book_info.book.isbn} </p>
                                <p>${description}: ${book_info.book.description}</p>
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
                            <span>${legend_about_author}</span>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${first_name}: ${book_info.book.author.firstName}</p>
                                <p>${last_name}:${book_info.book.author.lastName} </p>
                                <p>${middle_name}: ${book_info.book.author.middleName}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3 column">
                <div class="panel panel-default">
                    <div class="panel-heading libreStatsPanelHeading">
                        <div class="panel-title">
                            <span>${legend_econ_part}</span>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${book_price}: ${book_info.price}</p>
                                <p style="font-size: 30px ; color: darkgreen" >${book_count}:${book_info.amount} </p>
                            </div>
                        </div>
                    </div>
                </div>
                <a href="${books_url}" class="btn btn-info col-md-3" role="button">${button_back}</a>
                <a href="bookEdit?book_id=${book_info.book.id}" class="btn btn-warning col-md-6 "
                   role="button">${button_edit}</a>
                <form action="deleteBook" method="POST">
                    <input type="hidden" name="delete_id" value="${book_info.id}">
                    <button id="submit" name="submit"
                            class="btn btn-danger col-md-3 pull-right">${button_delete}</button>
                </form>
            </div>
        </div>

    </div>
</c:if>

<c:if test="${role.equals('user')}">
    <div class="col-md-12 column ">
        <div class="row clearfix ">
            <div class="col-md-3 column">
                <div class="panel panel-default">
                    <div class="panel-heading libreStatsPanelHeading">
                        <div class="panel-title">
                            <span>${legend_about_book}</span>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${book_name}: ${book_info.book.name}</p>
                                <p>${book_genre}: ${book_info.book.genre.name}</p>
                                <p>${book_year}: ${book_info.book.date}</p>
                                <p>ISBN:${book_info.book.isbn} </p>
                                <p>${description}: ${book_info.book.description}</p>
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
                            <span>${legend_about_author}</span>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${first_name}: ${book_info.book.author.firstName}</p>
                                <p>${last_name}:${book_info.book.author.lastName} </p>
                                <p>${middle_name}: ${book_info.book.author.middleName}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3 column">
                <div class="panel panel-default">
                    <div class="panel-heading libreStatsPanelHeading">
                        <div class="panel-title">
                            <span>${legend_econ_part}</span>
                        </div>
                    </div>
                    <div class="panel-body libreStatsPanelBody collapse in">
                        <div class="row clearfix libreStatsPanelRow">
                            <div class="col-md-12 column libreStatsPanelValueColumn">
                                <p>${book_price}: ${book_info.price}</p>
                                <p style="font-size: 30px ; color: darkgreen" >${book_count}:${book_info.amount} </p>
                            </div>
                        </div>
                    </div>
                </div>
                <a href="${books_url}" class="btn btn-info col-md-3" role="button">${button_back}</a>

                <c:if test="${not empty already_taken}">
                    <button class="btn btn-disabled col-md-5">${button_take}</button>
                    <button class="btn btn-disabled col-md-4">${button_card}</button>
                </c:if>

                <c:if test="${empty already_taken}">
                    <form action="takeBook" method="POST">
                        <input type="hidden" name="book_id" value="${book_info.id}">
                        <button name="submit" class="btn btn-warning col-md-5">${button_take}</button>
                    </form>
                    <form action="basket" method="POST">
                        <input type="hidden" name="book_id" value="${book_info.book.id}">
                        <button name="submit" class="btn btn-danger col-md-4">${button_card}</button>
                    </form>
                </c:if>

                <c:if test="${not empty count_error}">
                    <p class="alert alert-danger ">${notif_count}</p>
                </c:if>

                <c:if test="${not empty over_error}">
                    <p class="alert alert-danger ">${notif_over}</p>
                </c:if>

                <c:if test="${not empty already_taken}">
                    <p class="alert alert-danger ">${notif_already_taken}</p>
                </c:if>


            </div>
        </div>
    </div>
</c:if>