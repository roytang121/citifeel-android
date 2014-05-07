package com.citifeel.app.core;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by roytang on 31/3/14.
 */
public class ResponseHandler extends AsyncHttpResponseHandler{
    private AsyncHttpResponseHandler responseHandler;
    private Context context;
    private RequestParams params;

    public ResponseHandler(){
        this.responseHandler = this;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onSuccess(int status_code, String json){
        super.onSuccess(status_code, json);

    }
}
