<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 31.12.2022
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page language="java" %>

<!doctype html>
<html>
<meta http-equiv="X-UA-Compatible" content="IE=chrome">
<meta charset="UTF-8">
<c:set var="title" value="Страница входа" scope="page"/>
<body>
<div class="page-container-responsive">
    <div class="row space-top-8 space-8">
        <div class="col-md-5 col-middle">
            <h1 class="text-jumbo text-ginormous hide-sm">Ой!</h1>
            <h2>Looks like there's been some kind of error.</h2>
            <h6>Return to <a href="controller?action=login">home page </a></h6>
        </div>
        <div class="col-md-5 col-middle text-center">
            <img src="https://a0.muscache.com/airbnb/static/error_pages/404-Airbnb_final-d652ff855b1335dd3eedc3baa8dc8b69.gif" width="313" height="428" class="hide-sm" alt="Девочка уронила свое мороженое.">
        </div>
    </div>
</div>
</body>
</html>
