<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!doctype html>
<html>
<c:set var="title" value="Транзакции" scope="page"/>
<jsp:include page="/WEB-INF/templates/_head.jsp"></jsp:include>
<body>
<%--<jsp:include page="/WEB-INF/templates/_menu.jsp"></jsp:include>--%>
<jsp:include page="/WEB-INF/templates/_menu_customer.jsp"></jsp:include>
<div class="container">
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">
                <fmt:message key="table.th.date"/>
            </th>
            <th scope="col">
                <fmt:message key="table.th.operation.type"/>
            </th>
            <th scope="col">
                <fmt:message key="table.th.amount"/>
            </th>
            <th scope="col">
                <fmt:message key="table.th.description"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="transaction" items="${transactions}">
            <tr>
                <td>${transaction.timestamp}</td>
                <td><tags:iscredit value="${transaction.isCredit()}"/></td>
                <td>${transaction.amount}</td>
                <td>${transaction.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="col">
        <h5><fmt:message
                key="account.menu.private_office.label.balance"/>:</h5>
        value="${fullUser.account.balance}"
    </div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"></jsp:include>
</body>
</html>
