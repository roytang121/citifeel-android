package com.citifeel.app.core;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.citifeel.app.UIApplication;
import com.citifeel.app.model.JSONModel;
import com.citifeel.app.model.UserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roytang on 8/5/14.
 */
public class ServerRequestManager {
    public static final String TAG = "ServerRequestManager";
    /**
     * login handler
     */
    public interface OnLoginCallback{
        public void onSuccessLogin(UserModel model);
        public void onFailedLogin(String msg);
    }
    public static void login(String email, String password, final OnLoginCallback callback){
        final HashMap<String,String>params = new HashMap<String,String>();
        params.put("password", password);
        params.put("email", email);

        StringRequest req = new StringRequest(Request.Method.POST, Server.url(Server.URL_LOGIN),
                new ServerResponseListener() {
                    @Override
                    public void onStatusSuccess(String json) {
                        if(callback != null) {
                            UserModel user = UserModel.from(json, UserModel.class);
                            callback.onSuccessLogin(user);
                        }
                    }

                    @Override
                    public void onStatusFail(String msg) {
                        if(callback != null) {
                            callback.onFailedLogin(msg);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO : void?
                    }
                })
        {
            @Override
            public Map<String, String> getParams() {
                return params;
            }
        };

        UIApplication.getInstance().addToRequestQueue(req);
    }

    /**
     * register handler
     */
    public interface OnRegisterCallback{
        public void onSuccess(UserModel user);
        public void onFailure(String msg);
    }

    public static void register(String password, String email, OnRegisterCallback callback) {
        final HashMap<String,String>params = new HashMap<String,String>();
        params.put("password", password);
        params.put("email", email);

        StringRequest req = new StringRequest(Request.Method.POST, Server.url(Server.URL_REG),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    public Map<String, String> getParams() {
                        return params;
                    }
                };

        UIApplication.getInstance().addToRequestQueue(req);
    }


    /**
     * Check credential and status code
     *  limited to String
     */

    public static abstract class ServerResponseListener implements Response.Listener<String>{
        Context context;

        public ServerResponseListener(Context context){
            this.context = context;
        }

        public ServerResponseListener(){}

        @Override
        public void onResponse(String json) {

            StandardResponse response = JSONModel.from(json, StandardResponse.class);
            if(response != null){
                if(response.status_code == 1){
                    onStatusSuccess(json);
                } else {
                    if(response.message != null)
                        onStatusFail(response.message);
                }
            } else {
                Log.i(TAG, "response is null ");
            }
        }


        public abstract void onStatusSuccess(String json);
        public abstract void onStatusFail(String msg);
        class StandardResponse extends JSONModel{
            public int status_code;
            public String message;
        }


    }
}
