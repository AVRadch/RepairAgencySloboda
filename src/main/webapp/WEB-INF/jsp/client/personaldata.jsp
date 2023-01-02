<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!doctype html>
<html>
<c:set var="title" value="Страница входа" scope="page"/>
<jsp:include page="/WEB-INF/templates/_head.jsp"></jsp:include>
<body>
<%--<jsp:include page="/WEB-INF/templates/_menu.jsp"></jsp:include>--%>
<jsp:include page="/WEB-INF/templates/_menu_customer.jsp"></jsp:include>
<div class="container">
<form class="mt-2" method="post" action="controller?action=save_profile">
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="surname">
                <fmt:message key="table.th.surname"/>
            </label>
            <input type="text" class="form-control" id="surname" name="surname"
                   value="${fullUser.surname}"
                   minlength="2" maxlength="40" required>
        </div>
        <div class="form-group col-md-6">
            <label for="city">
                <fmt:message key="main.new_user.label.city"/>
            </label>
            <input type="text" class="form-control" id="city" name="city"
                   value="${fullUser.details.city}"
                   minlength="2" maxlength="40" required>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="firstName">
                <fmt:message key="profile.modal.label.name"/>
            </label>
            <input type="text" class="form-control" id="firstName" name="firstName"
                   value="${fullUser.firstName}"
                   minlength="2" maxlength="40" required>
        </div>
        <div class="form-group col-md-6">
            <div class="d-flex">
                <div class="flex-fill">
                    <label for="street">
                        <fmt:message key="main.new_user.label.street"/>
                    </label>
                    <input type="text" class="form-control" id="street"
                           name="street"
                           value="${fullUser.details.street}"
                           minlength="2"
                           maxlength="40" required>
                </div>
                <div class="flex-fill ml-1">
                    <label for="home">
                        <fmt:message key="main.new_user.label.home"/>
                    </label>
                    <input type="text" class="form-control" id="home" name="home"
                           value="${fullUser.details.home}"
                           minlength="2" maxlength="40" required>
                </div>
                <div class="flex-fill ml-1">
                    <label for="apartment">
                        <fmt:message key="main.new_user.label.apartment"/>
                    </label>
                    <input type="text" class="form-control" id="apartment"
                           name="apartment"
                           value="${fullUser.details.apartment}"
                           minlength="2" maxlength="40" required>
                </div>
            </div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="lastName">
                <fmt:message key="profile.modal.label.last_name"/>
            </label>
            <input type="text" class="form-control" id="lastName" name="lastName"
                   value="${fullUser.lastName}"
                   minlength="2" maxlength="40" required>
        </div>
        <div class="form-group col-md-6">
            <div class="d-flex">
                <div class="flex-fill">
                    <label for="email">
                        <fmt:message key="account.menu.edit.label.email"/>
                    </label>
                    <input type="email" class="form-control" id="email" name="email"
                           value="${fullUser.details.email}"
                           maxlength="40" required>
                </div>
                <div class="flex-fill ml-1">
                    <label for="phone">
                        <fmt:message key="main.new_user.placeholder.phone"/>
                    </label>
                    <input type="tel" class="form-control" id="phone" name="phone"
                           value="${fullUser.details.phone}"
                           size="13" maxlength="13" minlength="2"
                           maxlength="40" required
                    >
                </div>
            </div>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="password">
                <fmt:message key="profile.label.password"/>
            </label>
            <input type="password" class="form-control" id="password" name="password"
                   value=""
                   minlength="6" maxlength="40">
        </div>
    </div>
    <button type="submit" class="btn btn-dark">
        <fmt:message key="account.menu.edit.button.save"/>
    </button>
</form>
</div>

<jsp:include page="/WEB-INF/templates/_scripts.jsp"></jsp:include>
</body>
</html>