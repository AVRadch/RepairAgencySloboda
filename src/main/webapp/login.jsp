<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page language="java" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=chrome">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="Repair Agency"/>
    <meta name="keywords" content="repair, parts">
    <meta name="author" content="AVRad">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="login.css">
    <!------ Include the above in your HEAD tag ---------->

    <title><fmt:message key="label.login"/> | Repair Agency</title>
</head>
<body>
<div id="login">
    <h3 class="text-center text-white pt-5"><fmt:message key="label.loginForm"/></h3>
    <div class="container">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <form id="login-form" class="form" action="controller?action=login" method="post">
                        <h3 class="text-center text-info"><fmt:message key="label.login"/></h3>
                        <tags:notEmptyMessage value="${requestScope.message}"/>
                        <div class="form-group">
                            <label for="email" class="text-info"><fmt:message key="label.email"/></label><br>
                            <input type="text" name="email" id="email" class="form-control"
                                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$">
                            <tags:contains error="${requestScope.error}" value="email"/>
                        </div>
                        <div class="form-group">
                            <label for="password" class="text-info"><fmt:message key="label.password"/></label><br>
                            <input type="password" name="password" id="password" class="form-control">
                            <tags:contains error="${requestScope.error}" value="pass"/><br>
                        </div>
                        <div class="form-group">
                            <label for="remember-me" class="text-info"><span><fmt:message key="label.rememberMe"/></span>
                                <span><input id="remember-me" name="remember-me" type="checkbox"></span>      .</label>
                            <input type="submit" name="submit" class="btn btn-info btn-md" value=<fmt:message key="button.login"/>>
                        </div>
                        <div id="register-link" class="text-right">
                            <a href="registration.jsp" class="text-info"><fmt:message key="button.register"/></a>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        <div class="row justify-content-center align-items-center">
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
    </div>
</div>

</body>
</html>
