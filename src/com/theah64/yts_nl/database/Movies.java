package com.theah64.yts_nl.database;

import com.theah64.webengine.database.BaseTable;
import com.theah64.webengine.database.Connection;
import com.theah64.webengine.utils.Request;
import com.theah64.yts_api.models.YtsMovie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 1/10/17.
 */
public class Movies extends BaseTable<YtsMovie> {

    private static final Movies instance = new Movies();
    public static final String COLUMN_IMDB_ID = "imdb_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_YTS_URL = "yts_url";

    private Movies() {
        super("movies");
    }

    public static Movies getInstance() {
        return instance;
    }

    @Override
    public boolean add(YtsMovie ytsMovie) {
        final String query = "INSERT INTO movies (title, imdb_id, image_url,year, rating, genre,yts_url) VALUES (?,?,?,?,?,?,?);";
        boolean isAdded = false;
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, ytsMovie.getTitle());
            ps.setString(2, ytsMovie.getImdbId());
            ps.setString(3, ytsMovie.getImageUrl());
            ps.setString(4, ytsMovie.getYear());
            ps.setString(5, ytsMovie.getImdbRating());
            ps.setString(6, ytsMovie.getGenre());
            ps.setString(7, ytsMovie.getYtsUrl());

            if (ps.executeUpdate() == 1) {
                isAdded = true;
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isAdded;
    }

    @Override
    public List<YtsMovie> getLast(int count) throws Request.RequestException {
        final String query = "SELECT * FROM movies ORDER BY id DESC LIMIT ?";
        List<YtsMovie> movies = null;
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, count);
            final ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                movies = new ArrayList<>();
                do {
                    final String title = rs.getString(COLUMN_TITLE);
                    final String imdbId = rs.getString(COLUMN_IMDB_ID);
                    final String imageUrl = rs.getString(COLUMN_IMAGE_URL);
                    final String year = rs.getString(COLUMN_YEAR);
                    final String imdbRating = rs.getString(COLUMN_RATING);
                    final String genre = rs.getString(COLUMN_GENRE);
                    final String ytsUrl = rs.getString(COLUMN_YTS_URL);
                    movies.add(new YtsMovie(title, imdbId, imageUrl, year, imdbRating, genre, ytsUrl));
                } while (rs.next());
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Request.RequestException(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
