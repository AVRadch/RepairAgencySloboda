<%--
  Created by IntelliJ IDEA.
  User: Алексей Радченко
  Date: 20.01.2023
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="rls" uri="/WEB-INF/tld/custom"%><html>
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
            <div class="span5">
              <h1 class="muted">Repair Company Sloboda</h1>
            </div>
            <div class="span1 offset1" >
              <%@ include file="/language_selector.jsp" %>
            </div>
            <div class="span2 offset2">
              <br><rls:roletag role="${sessionScope.logged_user_role}"/>
            </div>
            <div class="span1 offset4" style="margin-top:0px;">
              <!--           <button class="btn pull-right" type="button">Sign Out</button>  -->
              <a href="controller?action=logout" class="btn pull-right" role="button"><fmt:message key="button.logout"/></a>
            </div>
          </div>
        </div>

        <div class="navbar">
          <div class="navbar-inner">
            <div class="container-fluid">
              <ul class="nav">
                <li>
                  <a href="controller?action=userFeedbacks"><fmt:message key="button.feedbacks"/></a>
                </li>

                <li>
                  <a href="controller?action=userRequest"><fmt:message key="button.requests"/></a>
                </li>

                <li>
                  <a href="userProfile.jsp"><fmt:message key="button.profile"/></a>
                </li>

                <li>
                  <a href="#"><fmt:message key="button.reports"/></a>
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

