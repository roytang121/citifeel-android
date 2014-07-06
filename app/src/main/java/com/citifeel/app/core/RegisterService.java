package com.citifeel.app.core;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by roytang on 6/7/14.
 */
public class RegisterService {

    public interface RegisterServiceCallback{
        public void onSuccess();
        public void onFailure();
    }
    public static void register(final Context context, final String email, final String password, final File profilepic,final String firstname, final String lastname, final RegisterServiceCallback callback) throws FileNotFoundException {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("profilepic", profilepic);
        client.post(context, "url", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (callback != null) {
                    Gson gson = new Gson();
                    Response response = gson.fromJson(content, Response.class);
                    if(response.status_code == 1)
                        callback.onSuccess();
                    else
                        callback.onFailure();
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);

                if (callback != null) {
                    callback.onFailure();
                }
            }
        });
    }

    private class Response{
        public int status_code;
    }
}
