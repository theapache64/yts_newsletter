package com.theah64.yts_nl.database;

import com.theah64.webengine.database.BaseTable;
import com.theah64.yts_nl.models.Movie;

/**
 * Created by theapache64 on 1/10/17.
 */
public class Movies extends BaseTable<Movie> {

    public Movies() {
        super("movies");
    }
}
