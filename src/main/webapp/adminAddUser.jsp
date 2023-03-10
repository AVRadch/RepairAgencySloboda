<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 16.01.2023
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>


<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">

    <!-- Website CSS style -->
    <link rel="stylesheet" type="text/css" href="assets/css/main.css">

    <!-- Website Font style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">

    <!-- Google Fonts -->
    <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" type="text/css" href="registration.css">

    <title>>New user | Repair Agency</title>
</head>
<body>
<div class="container">
    <div class="row main">
        <div class="panel-heading">
            <div class="panel-title text-center">
                <h1 class="title">Sloboda Repair Company</h1>
            </div>
        </div>
        <div class="main-login main-center">
            <form class="form-horizontal" method="post" action="controller?action=registrationAdmin">

                <div class="form-group">
                    <label for="email" class="cols-sm-2 control-label"><fmt:message key="table.email"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="email" id="email"
                                   placeholder="<fmt:message key="label.email"/>"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="firstname" class="cols-sm-2 control-label"><fmt:message key="table.firstname"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="firstname" id="firstname"
                                   placeholder="<fmt:message key="label.firstName"/>"/>
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <label for="lastname" class="cols-sm-2 control-label"><fmt:message key="table.lastname"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="lastname" id="lastname"
                                   placeholder="<fmt:message key="label.lastName"/>"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="cols-sm-2 control-label"><fmt:message key="label.password"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="<fmt:message key="label.password"/>"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="confirm" class="cols-sm-2 control-label"><fmt:message key="label.confirm.password"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                            <input type="password" class="form-control" name="confirm" id="confirm"
                                   placeholder="<fmt:message key="label.confirm.password"/>"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="phoneNumber" class="cols-sm-2 control-label"><fmt:message key="table.phoneNumber"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="phoneNumber" id="phoneNumber"
                                   placeholder="<fmt:message key="label.phoneNumber"/>"/>
                        </div>
                    </div>
                </div>
                <fmt:message key="table.role"/>: <select name="role">
                <option>MANAGER</option>
                <option>USER</option>
                <option>CRAFTSMAN</option>
            </select>
                <br>
                <div class="form-group ">
                    <button type="submit" class="btn btn-primary btn-lg btn-block login-button"><fmt:message
                            key="button.register"/></button>
                </div>
            </form>
        </div>
        <br>
        <%@ include file="/language_selector.jsp" %>
    </div>
</div>

<script type="text/javascript" src="assets/js/bootstrap.js"></script>
</body>
</html>
