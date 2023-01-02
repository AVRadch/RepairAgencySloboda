<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 29.12.2022
  Time: 18:46
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
            <th>Description</th>
            <th>Date</th>
            <th>Completion</th>
            <th>Payment</th>
            <th>Total Cost</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="request" items="${requestScope.requests}" varStatus="status">
            <tr>
            <td><c:out value="${request.id}"/></td>
            <td><c:out value="${request.description}"/></td>
            <td><c:out value="${request.date}"/></td>
            <td><c:out value="${request.completionStatus}"/></td>
            <td><c:out value="${request.paymentStatus}"/></td>
            <td><c:out value="${request.totalCost}"/></td>
            </tr>
        </c:forEach>

        </tbody>
        </table>
</div>

</body>
</html>
