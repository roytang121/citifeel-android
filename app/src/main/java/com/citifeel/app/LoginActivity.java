package com.citifeel.app;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.citifeel.app.activity.HomeActivity;
import com.citifeel.app.activity.RegisterActivity;
import com.citifeel.app.core.ServerRequestManager;
import com.citifeel.app.model.UserModel;
import com.citifeel.app.ui.LoginEditTextView;
import com.citifeel.app.ui.TonyButtonView;
import com.citifeel.app.util.CommonUtils;
import com.citifeel.app.util.AlertDialogManager;
import com.citifeel.app.util.SessionManager;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;

import java.util.Arrays;

/**
 * @author roytang
 * Launcher acitivty
 */
public class LoginActivity extends BaseActivity {

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        //FB session
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        Session fbsession = new Session(this);
        Session.setActiveSession(fbsession);

//        Login fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        /* start */

        //login method 1: login with our registered account
        TonyButtonView loginbutton = (TonyButtonView) findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener","loginbutton onclick");
                final LoginEditTextView emailtextfield = (LoginEditTextView) findViewById(R.id.emailtextfield);
                final LoginEditTextView passwordtextfield = (LoginEditTextView) findViewById(R.id.passwordtextfield);
                String email = emailtextfield.getText().toString();
                String password = passwordtextfield.getText().toString();

                ServerRequestManager.login(email, password, new ServerRequestManager.OnLoginCallback() {
                    @Override
                    public void onSuccessLogin(UserModel user) {
                        // Creating user login session
                        session.createLoginSession(user.getUserHashMap());

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailedLogin(String msg) {
                        Log.i("error message", msg);
                        alert.showAlertDialog(LoginActivity.this, "登入失敗..", msg, null);
                    }
                });
            }
        });

        //login method 2: login with fb account
        com.facebook.widget.LoginButton fbloginbutton = (com.facebook.widget.LoginButton) findViewById(R.id.fbloginbutton);
        fbloginbutton.setReadPermissions(Arrays.asList("public_profile", "user_status"));
        fbloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener","fbloginbutton onclick");
                Session.openActiveSession(LoginActivity.this, true, statusCallback);
            }
        });

        TonyButtonView registerbutton = (TonyButtonView) findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        CommonUtils.logKeyHash(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private Point getScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session fbsession, SessionState state, Exception exception) {
            //make request to server to validate the login by fb
            //post access token to server
            Log.i("fb session state", state.toString());
            if(state.equals(SessionState.OPENED)) {
                String access_token = fbsession.getAccessToken();

                Log.i("access_token", access_token);
                ServerRequestManager.fblogin(access_token, new ServerRequestManager.OnLoginCallback() {
                    @Override
                    public void onSuccessLogin(UserModel user) {
                        // Creating user login session
                        session.createLoginSession(user.getUserHashMap());

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailedLogin(String msg) {
                        Log.i("error message", msg);
                        alert.showAlertDialog(LoginActivity.this, "登入失敗..", msg, null);
                    }
                });

            }

        }
    }


}
