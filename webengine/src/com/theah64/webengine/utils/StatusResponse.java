package com.theah64.webengine.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by theapache64 on 13/11/17.
 */
public class StatusResponse {

    private static final String[] randomErrorMessages = new String[]{
            "We are now serving angels. We will be back to serve humans shortly",
            "Shhh... We are now sleeping and dreaming about new features to implement. Will be back soon.",
            "In reality, we are now down. This error message is an illusion. You can come back later and this error message will be gone",
            "You may be addicted to our site. We care about your health and we want to give you a break for a few hours. Will be back soon",
            "We are now testing error messages. Every hour until we fix the bug, you will see a new error message. Thanks for participating",
            "Yes, you are in the right place. We are not. We are trying to get there soon. Thank you",
            "There's so much noise out there. Our solution is to keep ONLY this error message for the entire screen until we fix the bug.",
            "Sorry for interrupting you with only ONE sentence. We will be back with information overload later",
            "If you are able to read this message, you are normal and we are not. Will be back soon",
            "OMG! We will be back soon", "You're OK. We're NOT. Will be back soon",
            "I've not taken a break for 168 days, 12 hours and 22 minutes. Now I really deserve one. Will be back later",
            "Thank you for visiting. I am currently undergoing my annual physical checkup. Will be back soon",
            "This is a 'hide and seek' game. I plan to hide from you until we fix the bug. Thank you for playing with me",
            "Close your eyes. Breathe. Inhale. Exhale. That's what we are doing. See you later.",
            "Someone has kidnapped our site. We are negotiating ransom and will resolve this issue and be back later."
    };

    private static final Random random = new Random();

    public static void redirect(HttpServletResponse response, String title, String message) throws IOException {
        response.sendRedirect("status.jsp?title=" + title + "&message=" + message);
    }

    public static void redirect(HttpServletResponse response, String title) throws IOException {
        response.sendRedirect("status.jsp?title=" + URLEncoder.encode(title, "UTF-8") + "&message=" + URLEncoder.encode(randomErrorMessages[random.nextInt(randomErrorMessages.length)], "UTF-8"));
    }
}
