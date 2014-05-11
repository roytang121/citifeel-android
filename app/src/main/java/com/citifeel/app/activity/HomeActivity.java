package com.citifeel.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.citifeel.app.R;
import com.citifeel.app.core.ServerRequestManager;
import com.citifeel.app.ui.TonyButtonView;
import com.citifeel.app.util.SessionManager;
import com.facebook.Session;

import java.util.HashMap;

public class HomeActivity extends Activity {

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Session class instance
        session = new SessionManager(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        if(session.checkLogin()){
            // get user data from session
            HashMap<String, String> user = session.getUserDetails();

            // email
            String email = user.get(SessionManager.KEY_EMAIL);

            Log.i("Login successful, user email:", email);
        }

        TonyButtonView logoutbutton = (TonyButtonView) findViewById(R.id.logoutbutton);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener","logoutbutton onclick");

                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();

                //clear fb session info
                Session fbsession = Session.getActiveSession();
                if (!fbsession.isClosed()) {
                    fbsession.closeAndClearTokenInformation();
                }


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
