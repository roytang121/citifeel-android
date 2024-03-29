package com.citifeel.app.util;

/**
 * Created by Jason Ng on 11/5/14.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.citifeel.app.activity.LoginActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "CitiFeelPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_FIRST_NAME = "firstname";
    public static final String KEY_LAST_NAME = "lastname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_PROFILE_PIC_LINK = "profile_pic";
    public static final String KEY_COLLECTION_PRIVACY = "collection_privacy";
    public static final String KEY_USER_INFO = "user_info";
    public static final String KEY_MEMBER_STATUS = "status";
    public static final String KEY_SESSION_TOKEN = "session_token";
   // public static final String KEY_SESSION_EXPIRE_TIME = "expire_time";
   // public static final String KEY_FB_ACCESS_TOKEN = "fb_access_token";

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(HashMap<String, String> storeData){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing data from HashMap
        for (String key : storeData.keySet()) {
            editor.putString(key, storeData.get(key));
        }

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        //put details to user object
        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, null));
        user.put(KEY_LAST_NAME, pref.getString(KEY_LAST_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));
        user.put(KEY_SESSION_TOKEN, pref.getString(KEY_SESSION_TOKEN, null));
      //  user.put(KEY_SESSION_EXPIRE_TIME, pref.getString(KEY_SESSION_EXPIRE_TIME, null));
      //user.put(KEY_FB_ACCESS_TOKEN, pref.getString(KEY_FB_ACCESS_TOKEN, null));

        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean checkLogin(){
        /* no session expire::not implement
        long diff=0;
        try{
            Date now =new Date();
            Date d2 = dateFormat.parse(pref.getString(KEY_SESSION_EXPIRE_TIME,null));

            //in milliseconds
            diff = d2.getTime() - now.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        } */

        if(!this.isLoggedIn()){ //if session expire implement : && diff> 1000
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            //kill the original activity from context
            ((Activity)_context).finish();

            return false;
        }
        return true;

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}