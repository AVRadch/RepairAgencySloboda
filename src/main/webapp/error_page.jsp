<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 31.12.2022
  Time: 17:08
  To change this template use File | Settings | File Templates.

<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page language="java" %> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>

<!doctype html>
<html>
<meta http-equiv="X-UA-Compatible" content="IE=chrome">
<meta charset="UTF-8">
<c:set var="title" value="Error page" scope="page"/>
<body>
<div class="page-container-responsive">
    <div class="row space-top-8 space-8">
        <div class="col-md-5 col-middle">
            <h1 class="text-jumbo text-ginormous hide-sm"><fmt:message key="error.ups"/></h1>
            <c:choose>
                <c:when test="${not empty requestScope.errorMessage}">
                    <span class="text-danger"><fmt:message key="${requestScope.errorMessage}"/></span>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="error.someError"/></h2>
                </c:otherwise>
            </c:choose>
            <h3><fmt:message key="error.returnTo"/><a href="controller?action=login"> <fmt:message key="error.loginPage"/> </a></h3>
        </div>
        <div class="col-md-5 col-middle text-center">
            <img src="https://a0.muscache.com/airbnb/static/error_pages/404-Airbnb_final-d652ff855b1335dd3eedc3baa8dc8b69.gif" width="313" height="428" class="hide-sm" alt="Девочка уронила свое мороженое.">
        </div>
    </div>
</div>
</body>
</html>
