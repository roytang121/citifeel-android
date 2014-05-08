package com.citifeel.app.core;

/**
 * Created by roytang on 8/5/14.
 */
public class Server {
    public static final String URL_BASE = "http://www.citifeel.com/api/";
    public static final String URL_REG = "user/register/";
    public static final String URL_LOGIN = "user/login/";

    public static String url(String url){
        return URL_BASE + url;
    }
}
