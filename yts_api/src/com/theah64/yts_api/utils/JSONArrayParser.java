package com.theah64.yts_api.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 1/10/17.
 */
public abstract class JSONArrayParser<T> {
    private final JSONArray jaItems;

    public JSONArrayParser(JSONArray jaItems) {
        this.jaItems = jaItems;
    }

    public abstract T get(final JSONObject jaItem) throws JSONException;


    public List<T> parse() throws JSONException {
        final int totalItems = jaItems.length();
        final List<T> items = new ArrayList<>(totalItems);
        for (int i = 0; i < totalItems; i++) {
            items.add(get(jaItems.getJSONObject(i)));
        }
        return items;
    }
}
