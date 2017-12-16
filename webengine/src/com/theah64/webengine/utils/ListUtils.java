package com.theah64.webengine.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by theapache64 on 16/12/17.
 */
public abstract class ListUtils<T> {
    public JSONArray toJSONArray(List<T> list) throws JSONException {
        final JSONArray ja = new JSONArray();
        for (T t : list) {
            ja.put(getJSONObject(t));
        }
        return ja;
    }

    protected abstract JSONObject getJSONObject(T t) throws JSONException;

}
