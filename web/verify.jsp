<%@ page import="com.theah64.webengine.utils.Request" %>
<%@ page import="com.theah64.webengine.utils.RequestException" %>
<%@ page import="com.theah64.yts_nl.database.Subscriptions" %>
<%@ page import="com.theah64.yts_nl.models.Subscription" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: theapache64
  Date: 24/9/17
  Time: 8:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        Request req;
        try {
            req = new Request(request, new String[]{Subscriptions.COLUMN_EMAIL, Subscriptions.COLUMN_VERIFICATION_CODE});

            Subscriptions.getInstance().update(new Subscription(
                    req.getStringParameter(Subscriptions.COLUMN_EMAIL),
                    req.getStringParameter(Subscriptions.COLUMN_VERIFICATION_CODE),
                    true, true));

        } catch (RequestException | SQLException e) {
            response.sendRedirect("result.jsp?title=Error&message=" + e.getMessage());
            return;
        }
    %>
    <title>Verify - Yts Newsletter</title>
    <%@include file="common_head.jsp" %>
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-md-12">
            <h2>Verified</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-md-10">
            <p>Your email is verified</p>
        </div>
    </div>


</div>
</body>
</html>
