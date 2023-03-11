<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 09.01.2023
  Time: 21:22
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


<div class="bd-example-snippet bd-code-snippet">
  <div class="bd-example">
    <table class="table table-striped" aria-label="user-table" style="margin-top: 20px; margin-left: 40px; max-width: 95%;">
    <thead>
    <c:set var="base" value="controller?action=adminAllFeedbacks&date=${param.date}&"/>
    <c:set var="byDate" value="sort=date_time&"/>
    <c:set var="byRating" value="sort=rating&"/>
    <c:set var="dateOrder"
           value="order=${param.sort ne 'date_time' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
    <c:set var="ratingOrder"
           value="order=${param.sort ne 'rating' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
    <c:set var="limits" value="&offset=0&records=${param.records}"/>
    <tr>
      <th><fmt:message key="table.id"/></th>
      <th><fmt:message key="table.user"/></th>
      <th><fmt:message key="table.feedback"/></th>
      <th><fmt:message key="table.repairer"/></th>
      <th><fmt:message key="table.date"/><a href="${base.concat(byDate).concat(dateOrder).concat(limits)}">
        <i class="bi bi-arrow-down-up link-dark"></i>
      </a></th>
      <th><fmt:message key="table.rating"/><a href="${base.concat(byRating).concat(ratingOrder).concat(limits)}">
        <i class="bi bi-arrow-down-up link-dark"></i>
      </a></th>
      <th><fmt:message key="table.request"/></th>
      <th><fmt:message key="table.action"/></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="feedback" items="${sessionScope.feedbackDTOS}" varStatus="status">
      <tr>
        <td><c:out value="${feedback.id}"/></td>
        <td><c:out value="${feedback.userFirstName}"/><br>
            <c:out value="${feedback.userLastName}"/></td>
        <td><c:out value="${feedback.feedback}"/></td>
        <td><c:out value="${feedback.repairerFirstName}"/><br>
              <c:out value="${feedback.repairerLastName}"/> </td>
        <td><c:out value="${feedback.date}"/></td>
        <td><c:out value="${feedback.rating}"/></td>
        <td><c:out value="${feedback.requestDescription}"/></td>
        <td>
          <a class="link-dark" href=controller?action=deleteFeedback&feedback-id=${feedback.id}>
            <fmt:message key="button.deleteFeedback"/>
          </a> <br>
          <a class="link-dark" href=controller?action=editFeedback&user-id=${feedback.id}>
            <fmt:message key="button.editFeedback"/>
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
    <input type="hidden" name="action" value="adminAllFeedbacks">
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
       value="controller?action=adminAllFeedbacks&date=${param.date}&sort=${param.sort}&order=${param.order}&"/>

<jsp:include page="pagination.jsp"/>

</body>
</html>
