<%@ page import="com.theah64.webengine.exceptions.MailException" %>
<%@ page import="com.theah64.webengine.utils.MailHelper" %>
<%@ page import="com.theah64.webengine.utils.Request" %>
<%@ page import="com.theah64.yts_api.models.YtsMovie" %>
<%@ page import="com.theah64.yts_nl.database.Movies" %>
<%@ page import="com.theah64.yts_nl.database.Subscriptions" %>
<%@ page import="com.theah64.yts_nl.misc.NewsLetter" %>
<%@ page import="com.theah64.yts_nl.models.Subscription" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %><%--
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

            final String userEmail = req.getStringParameter(Subscriptions.COLUMN_EMAIL);

            Subscriptions.getInstance().update(new Subscription(
                    userEmail,
                    req.getStringParameter(Subscriptions.COLUMN_VERIFICATION_CODE),
                    true, true));

            //Send a sample
            final List<YtsMovie> movieList = Movies.getInstance().getLast(10);
            final NewsLetter newsLetter = new NewsLetter.Builder(movieList.size())
                    .addMovies(movieList)
                    .build();

            MailHelper.sendMail(userEmail, "YTS Recent movies", newsLetter.getHtml(), "YTS Newsletter");

        } catch (Request.RequestException | SQLException | MailException e) {
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
