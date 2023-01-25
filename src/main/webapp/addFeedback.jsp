<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 24.01.2023
  Time: 17:15
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
        <form role="form" class="col-md-9 go-right" method="post" action="controller?action=createFeedback">
            <h2>New Feedback</h2>
            <div class="form-group">
                <textarea id="message" name="feedback" class="form-control" required></textarea>
                <label for="message">Insert request text</label>
            </div>
            <input class="col-2" type="number" min="1" name="rating" id="records"
                   value="10">&nbsp&nbsp&nbsp&nbsp&nbsp
            <div class="form-group ">
                <button type="submit" class="btn btn-primary btn-lg btn-block login-button"><fmt:message key="label.submit"/></button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
