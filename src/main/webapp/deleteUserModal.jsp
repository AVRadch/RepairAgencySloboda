<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="modal fade" id="exampleModalDefault" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
                <h1 class="modal-title fs-5 text-md-center">Delete user</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body py-0">
                <p>Delete user confirmation </p>
            </div>
            <div class="modal-footer flex-column border-top-0">
                <form method="POST" action="controller">
                    <input type="hidden" name="action" value="delete-user">
                    <input type="hidden" name="user-id" value=${requestScope.user-id}>
                    <button type="submit" class="btn btn-dark mt-4 mb-4">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>