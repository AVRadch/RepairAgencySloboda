<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>

<!doctype html>
<html>
<c:set var="title" value="Панель администратора" scope="page"/>
<jsp:include page="/WEB-INF/templates/_head.jsp"></jsp:include>
<body>
<jsp:include page="/WEB-INF/templates/_menu_admin.jsp"></jsp:include>
<div class="container">
<%-- Абоненты--%>
<div class="tab-pane fade show active" id="v-pills-users" role="tabpanel"
     aria-labelledby="v-pills-users-tab">
    <ul class="nav nav-tabs" id="usersTab" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="users-tab" data-toggle="tab" href="#users"
               role="tab"
               aria-controls="users" aria-selected="true">
                <fmt:message key="main.tab.subscriber"/>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="new-user-tab" data-toggle="tab" href="#new-user" role="tab"
               aria-controls="new-user"
               aria-selected="false">
                <fmt:message key="main.tab.new_subscriber"/>
            </a>
        </li>
    </ul>
    <div class="tab-content" id="usersTabContent">
        <%-- Список абонентов --%>
        <div class="tab-pane fade show active" id="users" role="tabpanel"
             aria-labelledby="internet-tab">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">
                        <fmt:message key="table.th.surname"/>
                    </th>
                    <th scope="col">
                        <fmt:message key="table.th.name"/>
                    </th>
                    <th scope="col">
                        <fmt:message key="table.th.last_name"/>
                    </th>
                    <th scope="col">
                        <fmt:message key="table.th.account"/>
                    </th>
                    <th scope="col">
                        <fmt:message key="table.th.balance"/>
                    </th>
                    <th scope="col">
                        <fmt:message key="table.th.status"/>
                    </th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="fullUser" items="${fullUser}">
                    <tr>
                        <td>${fullUser.surname}</td>
                        <td>${fullUser.firstName}</td>
                        <td>${fullUser.lastName}</td>
                        <td>${fullUser.account.number}</td>
                        <td>${fullUser.account.balance}</td>
                        <td><tags:isblocked value="${fullUser.blocked}"/></td>
                        <td>
                            <div class="d-flex justify-content-end">
                                <div>
                                    <form action="controller?action=edit_client"
                                          method="post">
                                        <input type="hidden" name="user_id"
                                               value="${fullUser.id}">
                                        <button type="submit"
                                                class="btn btn-outline-secondary btn-sm"
                                                name="btnLock">
                                                ${fullUser.blocked ? '<i class="material-icons">lock_open</i>' : '<i class="material-icons">lock</i>'}
                                        </button>
                                    </form>
                                </div>
                                <div class="ml-1">
                                    <form method="post" action="controller?action=profile">
                                        <input type="hidden" name="user_id"
                                               value="${fullUser.id}">
                                        <button type="submit"
                                                class="btn btn-outline-secondary btn-sm">
                                            <i class="material-icons">info</i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <%-- Новый абонент --%>
        <div class="tab-pane fade" id="new-user" role="tabpanel" aria-labelledby="new-user-tab">
            <form class="mt-2" method="post" action="controller?action=registration">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="surname">
                            <fmt:message key="table.th.surname"/>
                        </label>
                        <input type="text" class="form-control" id="surname" name="surname"
                               placeholder="<fmt:message key="profile.modal.placeholder.surname"/>"
                               minlength="2" maxlength="40" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="city">
                            <fmt:message key="main.new_user.label.city"/>
                        </label>
                        <input type="text" class="form-control" id="city" name="city"
                               placeholder="<fmt:message key="main.new_user.placeholder.city"/>"
                               minlength="2" maxlength="40" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="firstName">
                            <fmt:message key="profile.modal.label.name"/>
                        </label>
                        <input type="text" class="form-control" id="firstName" name="firstName"
                               placeholder="<fmt:message key="profile.modal.placeholder.name"/>"
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
                                       placeholder="<fmt:message key="main.new_user.placeholder.street"/>"
                                       minlength="2"
                                       maxlength="40" required>
                            </div>
                            <div class="flex-fill ml-1">
                                <label for="home">
                                    <fmt:message key="main.new_user.label.home"/>
                                </label>
                                <input type="text" class="form-control" id="home" name="home"
                                       placeholder="<fmt:message key="main.new_user.placeholder.home"/>"
                                       minlength="2" maxlength="40" required>
                            </div>
                            <div class="flex-fill ml-1">
                                <label for="apartment">
                                    <fmt:message key="main.new_user.label.apartment"/>
                                </label>
                                <input type="text" class="form-control" id="apartment"
                                       name="apartment"
                                       placeholder="125а" minlength="2" maxlength="40" required>
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
                               placeholder="<fmt:message key="profile.modal.placeholder.last_name"/>"
                               minlength="2" maxlength="40" required>
                    </div>
                    <div class="form-group col-md-6">
                        <div class="d-flex">
                            <div class="flex-fill">
                                <label for="email">
                                    <fmt:message key="account.menu.edit.label.email"/>
                                </label>
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="my_email@gmail.com" minlength="2"
                                       maxlength="40" required>
                            </div>
                            <div class="flex-fill ml-1">
                                <label for="phone">
                                    <fmt:message key="main.new_user.placeholder.phone"/>
                                </label>
                                <input type="tel" class="form-control" id="phone" name="phone"
                                       value="+380" size="13" maxlength="13" minlength="2"
                                       maxlength="40" required
                                       placeholder="+380991234567">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="login">
                            <fmt:message key="account.menu.edit.label.login"/>
                        </label>
                        <input type="text" class="form-control" id="login" name="login"
                               placeholder="<fmt:message key="account.menu.edit.placeholder.login"/>"
                               minlength="2" maxlength="40" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="password">
                            <fmt:message key="account.menu.edit.label.password"/>
                        </label>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="account.menu.edit.placeholder.password"/>"
                               minlength="6" maxlength="40" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-dark">
                    <fmt:message key="account.menu.edit.button.save"/>
                </button>
            </form>
        </div>
    </div>
</div>
</div>
<jsp:include page="/WEB-INF/templates/_scripts.jsp"></jsp:include>
</body>
</html>
