package com.citifeel.app.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by roytang on 8/5/14.
 */
public abstract class JSONModel<T> {
    public static<T> T from(String json , Class<T> cls){
        try {
            Gson gson = new Gson();
            T obj = gson.fromJson(json, cls);
            return obj;
        }catch(JsonSyntaxException e){
            e.printStackTrace();
            return null;
        }
    }
}
