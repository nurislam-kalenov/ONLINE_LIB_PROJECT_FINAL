<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="noOfPages" required="true" type="java.lang.Integer" %>
<%@ attribute name="books_url" required="true" %>

<ul class="pagination">
    <c:choose>
        <c:when test="${currentPage != 1}">
            <li><a href="${books_url}${currentPage - 1}">«</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="">«</a></li>
        </c:otherwise>
    </c:choose>

    <c:forEach begin="1" end="${noOfPages}" var="i">
        <c:choose>
            <c:when test="${currentPage eq i}">
                <li class="active"><a href="${books_url}${i}">${i} <span class="sr-only">(current)</span></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${books_url}${i}">${i}</a></li>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:choose>
        <c:when test="${currentPage lt noOfPages}">
            <li><a href="${books_url}${currentPage + 1}">»</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="">»</a></li>
        </c:otherwise>
    </c:choose>
</ul>
