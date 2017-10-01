package com.theah64.yts_nl.database;

import com.theah64.webengine.database.BaseTable;
import com.theah64.webengine.database.Connection;
import com.theah64.yts_api.models.YtsMovie;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by theapache64 on 1/10/17.
 */
public class Movies extends BaseTable<YtsMovie> {

    private static final Movies instance = new Movies();
    public static final String COLUMN_IMDB_ID = "imdb_id";

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
}
