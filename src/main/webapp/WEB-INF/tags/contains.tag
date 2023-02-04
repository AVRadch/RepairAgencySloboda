<%@ attribute name="error" required="true" %>
<%@ attribute name="value" required="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>

<c:if test="${fn:contains(error, value)}">
    <span class="text-danger"><fmt:message key="${error}"/></span>
</c:if>