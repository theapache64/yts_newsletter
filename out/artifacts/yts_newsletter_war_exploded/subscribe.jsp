<%@ page import="com.theah64.webengine.utils.Form" %>
<%@ page import="com.theah64.yts_nl.database.Subscriptions" %>
<%@ page import="com.theah64.webengine.utils.RandomString" %>
<%@ page import="com.theah64.yts_nl.models.Subscription" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.theah64.webengine.utils.MailHelper" %><%--
  Created by IntelliJ IDEA.
  User: theapache64
  Date: 24/9/17
  Time: 8:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Subscribe - Yts Newsletter</title>
    <%@include file="common_head.jsp" %>
</head>
<body>
<div class="container">

    <div class="row">
        <div class="col-md-12">
            <h2>Subscribe</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-md-10">
            <p>Want to get new movie notifications from <a target="_blank" href="https://yts.ag"> yts.ag</a> to your
                inbox?
                Subscribe to our FREE Newsletter.</p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4">
            <form action="subscribe.jsp">

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input id="email" name="<%=Subscriptions.COLUMN_EMAIL%>" type="email" class="form-control"
                           required/>
                </div>

                <div class="form-group">
                    <input class="btn btn-primary" name="is_submitted" type="submit" value="Subscribe!"/>
                </div>

            </form>
        </div>
    </div>

</div>
</body>
</html>

<%
    final Form form = new Form(request);
    if (form.isSubmitted()) {
        final String email = form.getString(Subscriptions.COLUMN_EMAIL);
        if (email.matches(Form.EMAIL_REGEX)) {
            //Valid email. adding to subscription list
            final String verificationCode = RandomString.get(20);
            final Subscription subscription = new Subscription(email, verificationCode, false, true);
            try {
                Subscriptions.getInstance().add(subscription);

                //Sending verification mail
                MailHelper.sendMail(email, "YTS Newsletter", "Your verification link is " + getVerificationLink(subscription));
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("result.jsp?title=Error&message=" + e.getMessage());
                return;
            }

            response.sendRedirect("result.jsp?title=Subscribed&message=We've sent a verification link to your email. Please check your inbox to complete the subscription");
            return;
        }
    }
%>
<%!
    private String getVerificationLink(Subscription subscription) {
        return String.format("http://localhost:8080/yts_newsletter/verify.jsp?email=%s&verification_code=%s", subscription.getEmail(), subscription.getVerificationCode());
    }
%>