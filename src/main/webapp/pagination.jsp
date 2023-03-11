<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="pagination" style="margin-left: 40px; margin-top: 0px;">
    <c:if test="${end > 3}">
        <li class="page-item">
            <a class="page-link link-dark" href="${href}offset=0&records=${records}">
                1
            </a>
        </li>
        <c:if test="${end > 4}">
            <li class="page-item">
                <c:set var="currentOffset" value="${(start - 2) * records}"/>
                <a class="page-link link-dark"
                   href="${href}offset=${currentOffset}&records=${records}">
                    ...
                </a>
            </li>
        </c:if>
    </c:if>
    <c:forEach var="page" begin="${start}" end="${end}">
        <li class="page-item">
            <c:set var="currentOffset" value="${(page - 1) * records}"/>
            <a class="page-link ${currentPage eq page ? 'dark-active' : 'link-dark'}"
               href="${href}offset=${currentOffset}&records=${records}">
                    ${page}
            </a>
        </li>
    </c:forEach>
    <c:if test="${end < pages}">
        <c:if test="${end + 1 < pages}">
            <li class="page-item">
                <c:set var="currentOffset" value="${(end) * records}"/>
                <a class="page-link link-dark"
                   href="${href}offset=${currentOffset}&records=${records}">
                    ...
                </a>
            </li>
        </c:if>
        <li class="page-item">
            <a class="page-link link-dark"
                href="${href}offset=${(pages - 1) * records}&records=${records}">
                    ${pages}
                </a>
        </li>
    </c:if>
</ul>