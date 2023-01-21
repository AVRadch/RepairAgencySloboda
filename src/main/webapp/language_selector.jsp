<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 07.01.2023
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>


<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid text-center">
    <form method="POST" class="d-flex mt-3">
        <label>
            <select name="language" onchange='submit();'>
                <option value="en" ${sessionScope.language eq 'en' ? 'selected' : ''}>
                    <fmt:message key="label.selectEnglish"/>
                </option>
                <option value="ru" ${sessionScope.language eq 'ru' ? 'selected' : ''}>
                    <fmt:message key="label.selectRussian"/>
                </option>
            </select>
        </label>
    </form>
</div>
</body>
</html>
