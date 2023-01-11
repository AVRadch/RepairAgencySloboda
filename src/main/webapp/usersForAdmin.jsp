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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <title>Admin Page | Repair Agency</title>
</head>
<body>
<%@ include file="/header.jsp" %>


<div class="container table-responsive">
    <table class="table table-striped table-hover w-auto">
        <thead>
        <tr>
            <th>id</th>
            <th>Email</th>
            <th>Lastname</th>
            <th>Firstname</th>
            <th>Account</th>
            <th>Phone Number</th>
            <th>Notification</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="user" items="${requestScope.userDTOS}" varStatus="status">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.account}"/></td>
                <td><c:out value="${user.phoneNumber}"/></td>
                <td><c:out value="${user.notification}"/></td>
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

</body>
</html>
