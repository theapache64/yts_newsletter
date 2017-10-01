package com.theah64.yts_api;

import com.theah64.yts_api.models.YtsMovie;
import com.theah64.yts_api.utils.JSONArrayParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by theapache64 on 1/10/17.
 */
public class YtsAPI {

    private static final String BASE_URL = "https://yts.ag/api/v2";
    private static final String LIST_MOVIES_END_POINT = BASE_URL + "/list_movies.json";

    public static List<YtsMovie> listMovies() {
        final String apiResponse = getNetworkResponse(LIST_MOVIES_END_POINT);
        try {
            final JSONObject joListMovies = new JSONObject(apiResponse);
            if (joListMovies.getString("status").equals("ok")) {
                //Valid response
                final JSONArray jaMovies = joListMovies.getJSONObject("data").getJSONArray("movies");
                return new JSONArrayParser<YtsMovie>(jaMovies) {
                    @Override
                    public YtsMovie get(JSONObject jaItem) throws JSONException {
                        return new YtsMovie(
                                jaItem.getString("title"),
                                jaItem.getString("imdb_code"),
                                jaItem.getString("medium_cover_image"),
                                String.valueOf(jaItem.getInt("year")),
                                String.valueOf(jaItem.getDouble("rating")),
                                YtsAPI.toString(jaItem.getJSONArray("genres")),
                                jaItem.getString("url")
                        );
                    }
                }.parse();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toString(JSONArray jsonArray) throws JSONException {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            sb.append(jsonArray.getString(i)).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static String getNetworkResponse(String url) {
        try {
            final URL urlOB = new URL(url);
            final HttpURLConnection con = (HttpURLConnection) urlOB.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
            con.connect();
            final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            final StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
