<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 20.01.2023
  Time: 20:24
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
            </div>
        </div>
        <div class="main-login main-center">
            <form class="form-horizontal" method="post" action="controller?action=updateRequest">
                <c:if test="${not empty requestScope.error}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>
                <input type="hidden" id="custId" name="request-id" value="${requestScope.requestDTO.id}">

                <div class="form-group">
                    <label for="user" class="cols-sm-3 control-label"><fmt:message key="label.userName"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                       <%--     <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span> --%>
                            <input type="text" class="form-control" name="user" id="user"
                                   pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                                   required value="${requestScope.requestDTO.userFirstName} ${requestScope.requestDTO.userLastName}" />
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="message" class="cols-sm-3 control-label"><fmt:message key="label.description"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                    <textarea id="message" name="description1" class="form-control" >${requestScope.requestDTO.description}</textarea>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="date" class="cols-sm-3 control-label"><fmt:message key="table.date"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group"></span>
                            <input type="text" class="form-control" name="date" id="date"
                                    required value="${requestScope.requestDTO.date}"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="completionStatus" class="cols-sm-3 control-label"><fmt:message key="label.phoneNumber"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="completionStatus" id="completionStatus"
                                   required value="${requestScope.requestDTO.completionStatus}"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="totalCost" class="cols-sm-3 control-label"><fmt:message key="label.account"/></label>
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="totalCost" id="totalCost"
                                   required value="${requestScope.requestDTO.totalCost}"/>
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
                <label for="paymentStatus">
                    <span><fmt:message key="table.payment"/>:</span>
                </label>
                <select id="paymentStatus" name="paymentStatus">
                    <option value="1"><fmt:message key="label.select.pandingPayment"/></option>
                    <option value="2"><fmt:message key="label.select.paid"/></option>
                    <option value="3"><fmt:message key="label.select.canceled"/></option>
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

        <br>
        <%@ include file="/language_selector.jsp" %>
    </div>
</div>

<script type="text/javascript" src="assets/js/bootstrap.js"></script>
</body>
</html>
