<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 13.01.2023
  Time: 17:12
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

    <title>>Edit user | Repair Agency</title>
</head>
<body>
<div class="container">
    <div class="row main">
        <div class="panel-heading">
            <div class="panel-title text-center">
                <h1 class="title">Sloboda Repair Company</h1>
                <hr />
            </div>
        </div>
        <div class="main-login main-center">
            <form class="form-horizontal" method="post" action="controller?action=updateUser">
                <c:if test="${not empty requestScope.error}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>
                <input type="hidden" id="custId" name="user-id" value="${requestScope.userDTO.id}">

                <div class="form-group">
                    <label for="email" class="cols-sm-3 control-label"><fmt:message key="label.email"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="email" id="email"
                                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$"
                                   required value="${requestScope.userDTO.email}"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="firstname" class="cols-sm-3 control-label"><fmt:message key="label.firstName"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="firstname" id="firstname"
                                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}" required value="${requestScope.userDTO.firstName}"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="lastname" class="cols-sm-3 control-label"><fmt:message key="label.lastName"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="lastname" id="lastname"
                                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}" required value="${requestScope.userDTO.lastName}"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="phoneNumber" class="cols-sm-3 control-label"><fmt:message key="label.phoneNumber"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="phoneNumber" id="phoneNumber"
                            required value="${requestScope.userDTO.phoneNumber}"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="account" class="cols-sm-3 control-label"><fmt:message key="label.account"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="account" id="account"
                                   required value="${requestScope.userDTO.account}"/>
                        </div>
                    </div>
                </div>
<%--
                <form method="POST" class="d-flex mt-3">
                    <label>
                        <select name="role" <%--onchange='submit();' --%>
                <%--                >
                                        <option value="manager">
                                              <fmt:message key="label.selectManager"/>
                                          </option>
                                          <option value="craftsman">
                                              <fmt:message key="label.selectCraftsman"/>
                                          </option>
                                          <option value="user">
                                              <fmt:message key="label.selectUser"/>
                                          </option>

                                      </select>
                                  </label>
                              </form> --%>
                User Role: <select name="role">
                <option>MANAGER</option>
                <option>USER</option>
                <option>CRAFTSMAN</option>
            </select>

                <div class="form-group ">
                    <button type="submit" class="btn btn-primary btn-lg btn-block login-button"><fmt:message key="button.update"/></button>
                </div>
            </form>
<%--
            <p class="fs-6 col-md-8">
                <a href="changePassword.jsp" class="link-dark"><fmt:message key="change.password"/></a>
            </p>    --%>
        </div>
    </div>
</div>

<script type="text/javascript" src="assets/js/bootstrap.js"></script>
</body>
</html>
