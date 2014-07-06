package com.citifeel.app.core;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.params.ClientPNames;

/**
 * Created by roytang on 31/3/14.
 * Use volley as first pref.
 * AsyncHttpClient is used only when posting large file for application/multipart
 */
@Deprecated
public class Client {
    private static final String BASE_URL = "http://server-host/";

    /**
     * static client for calling GET/POST
     */
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void raw_get(String url, RequestParams params,
                               AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params,
                            ResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
        responseHandler.setContext(context);
        responseHandler.setParams(params);
    }

    public static void raw_post(String url, RequestParams params,
                                AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void setEnableCircularRedirect(boolean enable){
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, enable);
    }

    public static AsyncHttpClient getClientInstance(){
        return client;
    }
}
