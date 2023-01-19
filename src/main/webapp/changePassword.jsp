<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>

<!DOCTYPE html>
<html lang="${sessionScope.language}">

<head>
    <title>Repair Agency<fmt:message key="change.password"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/my.css">
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/showPass.js"></script>
</head>

<body>


<div class="col-lg-5 mx-auto p-4 py-md-5">


    <form method="POST" action="controller">
        <input type="hidden" name="action" value="change-password">

        <div class="form-group">
            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if><br>
            <label class="form-label fs-5" for="old-password"><fmt:message key="label.old.password"/>*: </label>
            <input class="form-control" type="password" name="old-password" id="old-password" required>
            <c:if test="${not empty requestScope.error}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="password"><fmt:message key="label.new.password"/>*: </label>
            <input class="form-control" type="password" name="password" id="password"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                   title="<fmt:message key="label.password.requirements"/>" required><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="confirm-password"><fmt:message key="label.confirm.password"/>*: </label>
            <input class="form-control" type="password" name="confirm-password" id="confirm-password"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                   title="<fmt:message key="label.password.requirements"/>" required><br>
        </div>

        <div class="form-check">
            <input class="form-check-input" type="checkbox" id="flexCheckDefault"
                   onclick="showPass('old-password'); showPass('password'); showPass('confirm-password')">
            <label class="form-check-label" for="flexCheckDefault"><fmt:message key="label.show.password"/></label>
        </div><br>

        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="change.password"/></button>
    </form>
</div>

<%--<jsp:include page="fragments/footer.jsp"/> --%>

</body>
</html>