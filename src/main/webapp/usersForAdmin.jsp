<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 09.01.2023
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}"/>
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
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-icons.css">
    <link rel="stylesheet" href="css/my.css">
    <script src="assets/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <title>Admin Page | Repair Agency</title>
</head>
<body>

<%@ include file="/header.jsp" %>

<div class="container-fluid">
    <div class="form-row text-center">
        <div class="btn-group">
        <a href="controller?action=addUser" class="btn btn" role="button"><fmt:message key="button.addUser"/></a>
        </div>
    </div>
</div>
<br>


<div class="bd-example-snippet bd-code-snippet">
    <div class="bd-example">
        <table class="table table-striped" aria-label="user-table">
            <thead>
            <tr>
                <c:set var="base" value="controller?action=adminAllUsers&date=${param.date}&"/>
                <c:set var="byId" value="sort=u_id&"/>
                <c:set var="byEmail" value="sort=email&"/>
                <c:set var="byFirstName" value="sort=first_name&"/>
                <c:set var="byLastName" value="sort=last_name&"/>
                <c:set var="idOrder"
                        value="order=${param.sort ne 'u_id' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                <c:set var="emailOrder"
                       value="order=${param.sort ne 'email' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                <c:set var="firstNameOrder"
                       value="order=${param.sort ne 'first_name' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                <c:set var="lastNameOrder"
                       value="order=${param.sort ne 'last_name' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                <c:set var="limits" value="&offset=0&records=${param.records}"/>
                <th scope="col"><fmt:message key="table.id"/><a href="${base.concat(byId).concat(idOrder).concat(limits)}">
                    <i class="bi bi-arrow-down-up link-dark"></i>
                </a></th>
                <th scope="col"><fmt:message key="table.email"/><a href="${base.concat(byEmail).concat(emailOrder).concat(limits)}">
                    <i class="bi bi-arrow-down-up link-dark"></i>
                    </a></th>
                <th scope="col"><fmt:message key="table.lastname"/><a href="${base.concat(byLastName).concat(lastNameOrder).concat(limits)}">
                    <i class="bi bi-arrow-down-up link-dark"></i>
                </a></th>
                <th scope="col"><fmt:message key="table.firstname"/><a href="${base.concat(byFirstName).concat(firstNameOrder).concat(limits)}">
                    <i class="bi bi-arrow-down-up link-dark"></i>
                </a></th>
                <th><fmt:message key="table.account"/></th>
                <th><fmt:message key="table.phoneNumber"/></th>
                <th style="width:5%"><fmt:message key="table.role"/></th>
                <th><fmt:message key="table.action"/></th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="user" items="${sessionScope.userDTOS}" varStatus="status">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.account}"/></td>
                    <td><c:out value="${user.phoneNumber}"/></td>
                    <td><c:out value="${user.role}"/></td>
                    <td>
                        <a class="link-dark" href=controller?action=deleteUser&user-id=${user.id}>
                            <fmt:message key="button.deleteUser"/>
                        </a> <br>
                        <a class="link-dark" href=controller?action=editUser&user-id=${user.id}>
                            <fmt:message key="button.editUser"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>


<form method="GET" action="controller" class="flex">
    <div class="d-flex justify-content-between text-center">
        <input type="hidden" name="action" value="adminAllUsers">
        <input type="hidden" name="offset" value="0">
        <div class="form-row ">
        <div class="flex-column">
            <label for="records"><fmt:message key="label.numberRecords"/></label>
            <input class="col-2" type="number" min="1" name="records" id="records"
                   value="${not empty requestScope.records ? requestScope.records : "6"}">&nbsp&nbsp&nbsp&nbsp&nbsp
            <button type="submit" class="btn btn-dark mt-2 mb-3"><fmt:message key="label.submit"/></button>
        </div>
        </div>
    </div>
</form>

<c:set var="href" scope="request"
       value="controller?action=adminAllUsers&date=${param.date}&sort=${param.sort}&order=${param.order}&"/>

<jsp:include page="pagination.jsp"/>

</body>
</html>
