package com.theah64.yts_nl.servlets;

import com.theah64.webengine.exceptions.MailException;
import com.theah64.webengine.servlets.AdvancedBaseServlet;
import com.theah64.webengine.utils.CommonUtils;
import com.theah64.webengine.utils.MailHelper;
import com.theah64.webengine.utils.RequestException;
import com.theah64.webengine.utils.Response;
import com.theah64.yts_api.YtsAPI;
import com.theah64.yts_api.models.YtsMovie;
import com.theah64.yts_nl.database.LettersSent;
import com.theah64.yts_nl.database.Movies;
import com.theah64.yts_nl.database.Subscriptions;
import com.theah64.yts_nl.misc.NewsLetter;
import com.theah64.yts_nl.models.LetterSent;
import com.theah64.yts_nl.models.Subscription;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by theapache64 on 26/9/17.
 */
@WebServlet(urlPatterns = {"/yts_watcher"})
public class YtsWatcherServlet extends AdvancedBaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[0];
    }

    @Override
    protected void doAdvancedPost() throws JSONException, SQLException, IOException, ServletException, RequestException {

        System.out.println(new Date());

        //Getting all latest movies
        final List<YtsMovie> ytsMovies = YtsAPI.listMovies();
        if (ytsMovies != null) {
            final List<YtsMovie> newMovies = new ArrayList<>();
            final Movies moviesTable = Movies.getInstance();

            for (YtsMovie ytsMovie : ytsMovies) {
                final boolean isExistInDB = moviesTable.get(Movies.COLUMN_IMDB_ID, ytsMovie.getImdbId(), Movies.COLUMN_ID, false) != null;
                if (!isExistInDB) {
                    //Added to news letter list
                    newMovies.add(ytsMovie);

                    //Adding to db
                    moviesTable.add(ytsMovie);
                }
            }

            final int totalNewMovies = newMovies.size();

            if (totalNewMovies > 0) {

                System.out.println(totalNewMovies + " new movies found");

                //Building newsletter here
                final NewsLetter newsLetter = new NewsLetter.Builder(totalNewMovies)
                        .addMovies(newMovies)
                        .build();

                // Send to subscribers
                final List<Subscription> subscriptions = Subscriptions.getInstance().getAllValidSubscriptions();
                if (subscriptions != null) {
                    final StringBuilder sb = new StringBuilder();
                    for (final Subscription subscription : subscriptions) {
                        sb.append(subscription.getEmail()).append(",");
                    }

                    try {
                        MailHelper.sendMail(sb.toString(), String.format("%d new %s found", totalNewMovies, CommonUtils.getProper(totalNewMovies, "movie", "movies")), newsLetter.getHtml());

                        //Storing report
                        LettersSent.getInstance().add(new LetterSent(subscriptions.size(), totalNewMovies));

                        //Newsletter report
                        final String report = "Movies found: " + totalNewMovies + "\nLetters sent:" + subscriptions.size();
                        getWriter().write(new Response(report, null).getResponse());

                    } catch (MailException e) {
                        e.printStackTrace();
                        throw new RequestException(e.getMessage());
                    }


                } else {
                    throw new RequestException("No subscribers found");
                }

            } else {
                throw new RequestException("No new movies found in yts.ag");
            }
        } else {
            throw new RequestException("Failed to get movie list");
        }
    }
}
