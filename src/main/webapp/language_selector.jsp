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
<div class="container-fluid">
     <form action="controller?action=locale-handler" method="post">
         <select class="form-select form-select-lg" name = "language" onchange='submit();' aria-label="close">
             <option value = "en" ${sessionScope.language == 'en' ? 'selected' : ''}>English</option>
             <option value = "ru" ${sessionScope.language == 'ru' ? 'selected' : ''}>Русский</option>
         </select>
     </form>
</div>
</body>
</html>
