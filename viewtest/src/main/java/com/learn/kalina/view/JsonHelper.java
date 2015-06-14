package com.learn.kalina.view;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by KalinaRain on 2015/5/1.
 */
public class JsonHelper {
    public static String getJsonString(String key, Object value) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject.toString();
    }

    public static String parseJsonString(String json) {
        JSONTokener jsonTokener = new JSONTokener(json);
        try {
            JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;

    }

}
