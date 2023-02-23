<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 19.01.2023
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <title>Title</title>
</head>
<body>
<%@ include file="/header.jsp" %>

<!------ Include the above in your HEAD tag ---------->

<div class="container">
    <div class="row">
        <form role="form" class="col-md-9 go-right" method="post" action="controller?action=addRequest">
            <h2><fmt:message key="label.newRequest"/></h2>
            <div class="form-group">
                <textarea id="message" name="description" class="form-control" required></textarea>
                <label for="message"><fmt:message key="label.insertRequestText"/></label>
            </div>
            <div class="form-group ">
                <button type="submit" class="btn btn-primary btn-lg btn-block login-button"><fmt:message key="label.submit"/></button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
