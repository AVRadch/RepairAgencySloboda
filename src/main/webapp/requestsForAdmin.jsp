<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 29.12.2022
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.my.repairagency007.model.entity.CompletionStatus" %>

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

<div class="text-center" style="padding-top: 20px;">
    <form method="GET" action="controller" class="form-inline">
        <input type="hidden" name="action" value="adminAllRequest">
        <input type="hidden" name="offset" value="0">
        <div class="form-group mr-2" style="padding-left: 0; padding-right: 20px;">
            <label for="repairer" class="mr-2"><fmt:message key="label.selectRepairer"/>:</label>
            <select name="repairer" class="form-control" id="repairer">
                <option value="0" ${param.repairer eq "0" ? "selected" : ""}><fmt:message
                        key="label.selectAllRepairer"/></option>
                <c:forEach var="repairer" items="${sessionScope.repairers}">
                    <option value="${repairer.id}" ${param.repairer eq repairer.id ? "selected" : ""}>${repairer.lastName} ${repairer.firstName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group mr-2" style="padding-left: 20px; padding-right: 0px;">
            <label for="status" class="mr-2"><fmt:message key="label.selectCompletionStatus"/>:</label>
            <select name="status" class="form-control" id="status">
                <option value="-1" ${param.status eq "-1" ? "selected" : ""}><fmt:message
                        key="label.selectAllCompletionStatus"/></option>
                <c:forEach var="status" items="${CompletionStatus.values()}">
                    <option value="${status.ordinal()}" ${param.status eq status.ordinal() ? "selected" : ""}>${status.name()}
                    </option>
                </c:forEach>
            </select>
        </div>
    </form>
    <script>
        const repairerSelect = document.getElementById("repairer");
        const statusSelect = document.getElementById("status");

        repairerSelect.addEventListener("change", function () {
            const form = this.closest("form");
            form.submit();
        });

        statusSelect.addEventListener("change", function () {
            const form = this.closest("form");
            form.submit();
        });
    </script>

</div>

<div class="bd-example-snippet bd-code-snippet">
    <div class="bd-example">
        <table class="table table-striped" aria-label="user-table" style=" margin: 0 auto; max-width: 95%;">
            <thead>
            <c:set var="base"
                   value="controller?action=adminAllRequest&repairer=${param.repairer}&status=${param.status}&date=${param.date}&"/>
            <c:set var="byDate" value="sort=date&"/>
            <c:set var="byCompletionStatus" value="sort=completion_status_id&"/>
            <c:set var="byPaymentStatus" value="sort=payment_status_id&"/>
            <c:set var="byTotalCost" value="sort=total_cost&"/>
            <c:set var="dateOrder"
                   value="order=${param.sort ne 'date' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
            <c:set var="completionStatusOrder"
                   value="order=${param.sort ne 'completion_status_id' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
            <c:set var="paymentStatusOrder"
                   value="order=${param.sort ne 'payment_status_id' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
            <c:set var="totalCostOrder"
                   value="order=${param.sort ne 'total_cost' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
            <c:set var="limits" value="&offset=0&records=${param.records}"/>
            <tr>
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="table.user"/></th>
                <th><fmt:message key="table.description"/></th>
                <th scope="col"><fmt:message key="table.date"/><a
                        href="${base.concat(byDate).concat(dateOrder).concat(limits)}">
                    <i class="bi bi-arrow-down-up link-dark"></i>
                </a></th>
                <th scope="col"><fmt:message key="table.completion"/><a
                        href="${base.concat(byCompletionStatus).concat(completionStatusOrder).concat(limits)}">
                    <i class="bi bi-arrow-down-up link-dark"></i>
                </a></th>
                <th><fmt:message key="table.repairer"/></th>
                <th scope="col"><fmt:message key="table.payment"/><a
                        href="${base.concat(byPaymentStatus).concat(paymentStatusOrder).concat(limits)}">
                    <i class="bi bi-arrow-down-up link-dark"></i>
                </a></th>
                <th scope="col"><fmt:message key="table.totalCost"/><a
                        href="${base.concat(byTotalCost).concat(totalCostOrder).concat(limits)}">
                    <i class="bi bi-arrow-down-up link-dark"></i>
                </a></th>
                <th><fmt:message key="table.action"/></th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="request" items="${sessionScope.requestDTOS}" varStatus="status">
                <tr>
                    <td><c:out value="${request.id}"/></td>
                    <td><c:out value="${request.userFirstName}"/><br>
                        <c:out value="${request.userLastName}"/></td>
                    <td><c:out value="${request.description}"/></td>
                    <td><c:out value="${request.date}"/></td>
                    <td><c:out value="${request.completionStatus}"/></td>
                    <td><c:out value="${request.repairerLastName}"/></td>
                    <td><c:out value="${request.paymentStatus}"/></td>
                    <td><c:out value="${request.totalCost}"/></td>
                    <td>
                        <a class="link-dark" href=controller?action=deleteRequest&request-id=${request.id}>
                            <fmt:message key="button.deleteRequest"/>
                        </a> <br>
                        <a class="link-dark" href=controller?action=editRequest&request-id=${request.id}>
                            <fmt:message key="button.editRequest"/>
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
        <input type="hidden" name="action" value="adminAllRequest">
        <input type="hidden" name="offset" value="0">
        <input type="hidden" name="repairer" value="${param.repairer}">
        <input type="hidden" name="status" value="${param.status}">
        <input type="hidden" name="date" value="${param.date}">
        <input type="hidden" name="sort" value="${param.sort}">
        <input type="hidden" name="order" value="${param.order}">
        <div class="form-row ">
            <div class="flex-column">
                <label for="records"><fmt:message key="label.numberRecords"/></label>
                <input class="col-2" type="number" min="1" name="records" id="records"
                       value="${not empty records ? records : "5"}">
                <button type="submit" class="btn btn-dark mt-2 mb-3"><fmt:message key="label.submit"/></button>
            </div>
        </div>
    </div>
</form>

<c:set var="href" scope="request"
       value="controller?action=adminAllRequest&repairer=${param.repairer}&status=${param.status}&date=${param.date}&sort=${param.sort}&order=${param.order}&"/>

<jsp:include page="pagination.jsp"/>

</body>
</html>
