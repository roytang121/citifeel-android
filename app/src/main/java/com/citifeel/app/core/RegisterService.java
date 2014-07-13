package com.citifeel.app.core;

import android.content.Context;
import android.util.Log;

import com.citifeel.app.model.ResponseModel;
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
        public void onFailure(String msg);
    }
    public static void register(final Context context, final String email, final String password, final File profilepic,final String username, final RegisterServiceCallback callback) throws FileNotFoundException {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        //params.put("firstname", firstname);
        //params.put("lastname", lastname);
        params.put("username", username);
        params.put("profilepic", profilepic);
        client.post(context, Server.url(Server.URL_REG), params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (callback != null) {
                    //Log.i("reg response",content);
                    Gson gson = new Gson();
                    ResponseModel response = gson.fromJson(content, ResponseModel.class);
                    //Log.i("reg request response:", content);
                    if(response.status_code == 1) {
                        callback.onSuccess();
                    }else {
                        callback.onFailure(response.message);
                    }
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);

                if (callback != null) {
                    callback.onFailure(content);
                }
            }
        });
    }

    private class Response{
        public int status_code;
    }
}
