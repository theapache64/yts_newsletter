package com.theah64.yts_nl.servlets;

import com.theah64.webengine.servlets.AdvancedBaseServlet;
import com.theah64.webengine.utils.RequestException;
import com.theah64.yts_api.YtsAPI;
import com.theah64.yts_api.models.YtsMovie;
import com.theah64.yts_nl.database.Movies;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    protected void doAdvancedPost() throws JSONException, SQLException, RequestException, IOException, ServletException, RequestException {

        //Getting all latest movies
        final List<YtsMovie> ytsMovies = YtsAPI.listMovies();
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

        if (!newMovies.isEmpty()) {
            System.out.println(newMovies.size() + " new movies found");
            //TODO: Building newsletter here
            //TODO: Send to subscribers
        } else {
            throw new RequestException("No new movies found in yts.ag");
        }

    }
}
