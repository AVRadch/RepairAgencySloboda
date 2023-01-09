<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 29.12.2022
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <title>Title</title>
</head>
<body>

<!------ Include the above in your HEAD tag ---------->

<div class="container">
    <div class="row">
        <div class="span12">
            <div class="head">
                <div class="row-fluid">
                    <div class="span12">
                        <div class="span6">
                            <h1 class="muted">Repair Company Sloboda</h1>
                        </div>
                        <div class="span1 offset1" >
                            <%@ include file="/language_selector.jsp" %>
                        </div>
                        <div class="span2 offset2" style="margin-top:15px;">
                            <button class="btn pull-right" type="button">Sign Out</button>
                        </div>
                    </div>
                </div>

                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="container">
                            <ul class="nav">
                                <li>
                                    <a href="controller?action=adminAllUsers">Users</a>
                                </li>

                                <li>
                                    <a href="#">Feedbacks</a>
                                </li>

                                <li>
                                    <a href="#">Requests</a>
                                </li>

                                <li>
                                    <a href="#">Reports</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
