package com.citifeel.app.model;

import java.util.HashMap;
import com.citifeel.app.util.SessionManager;

/**
 * Created by roytang on 8/5/14.
 */
public class UserModel extends JSONModel{
    public String user_id;
    public String email;
    public String firstname;
    public String lastname;
    public String profile_pic;
    public String collection_privacy;
    public String user_info;
    public String status;
    public String session_token;
    public String expire_time;

    public HashMap<String, String> getUserHashMap(){
        HashMap<String, String> user = new HashMap<String, String>();

        //put details to user object
        user.put(SessionManager.KEY_FIRST_NAME, firstname);
        user.put(SessionManager.KEY_LAST_NAME, lastname);
        user.put(SessionManager.KEY_USER_ID, user_id);
        user.put(SessionManager.KEY_EMAIL, email);
        user.put(SessionManager.KEY_PROFILE_PIC_LINK, profile_pic);
        user.put(SessionManager.KEY_COLLECTION_PRIVACY, collection_privacy);
        user.put(SessionManager.KEY_USER_INFO, user_info);
        user.put(SessionManager.KEY_MEMBER_STATUS, status);
        user.put(SessionManager.KEY_SESSION_TOKEN, session_token);
        user.put(SessionManager.KEY_SESSION_EXPIRE_TIME, expire_time);

        return user;
    }
}
