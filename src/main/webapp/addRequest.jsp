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
            <h2>New Request</h2>
         <%--   <p>To see how it works, you clink in a input field.</p>
            <div class="form-group">
                <input id="name" name="name" type="text" class="form-control" required>
                <label for="name">Your Name</label>
            </div>
            <div class="form-group">
                <input id="phone" name="phone" type="tel" class="form-control" required>
                <label for="phone">Primary Phone</label>
            </div>      --%>
            <div class="form-group">
                <textarea id="message" name="description" class="form-control" required></textarea>
                <label for="message">Insert request text</label>
            </div>
            <div class="form-group ">
                <button type="submit" class="btn btn-primary btn-lg btn-block login-button"><fmt:message key="label.submit"/></button>
            </div>
        </form>
 <%--       <p class="bg-success" style="padding:10px;margin-top:20px;clear:both"><small><a href="http://css-tricks.com/float-labels-css/" target="_blank">Link</a> to original article</small></p> --%>

    </div>
</div>
</body>
</html>
