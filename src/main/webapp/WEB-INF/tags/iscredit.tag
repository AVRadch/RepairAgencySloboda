<%@ attribute name="value" type="java.lang.Boolean" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:choose>
    <c:when test="${value}">
        <span class="material-icons">add</span>
    </c:when>
    <c:otherwise>
        <span class="material-icons">remove</span>
    </c:otherwise>
</c:choose>