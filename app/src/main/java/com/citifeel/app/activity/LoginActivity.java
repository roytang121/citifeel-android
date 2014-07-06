package com.citifeel.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.citifeel.app.BaseActivity;
import com.citifeel.app.R;
import com.citifeel.app.core.ServerRequestManager;
import com.citifeel.app.model.UserModel;
import com.citifeel.app.ui.LoginEditTextView;
import com.citifeel.app.ui.TonyButtonView;
import com.citifeel.app.util.AlertDialogManager;
import com.citifeel.app.util.CommonUtils;
import com.citifeel.app.util.SessionManager;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author roytang
 * Launcher acitivty
 */
public class LoginActivity extends BaseActivity {

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    ProgressDialog dialog;

    // Session Manager Class
    SessionManager session;

    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //generate hash key
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.citifeel.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        // Session Manager
        session = new SessionManager(LoginActivity.this);

        //FB session
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        Session fbsession = Session.getActiveSession();
        if(fbsession == null) {
            if(savedInstanceState != null) {
                fbsession = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if(fbsession == null) {
                fbsession = new Session(this);
            }
            Session.setActiveSession(fbsession);
            fbsession.addCallback(statusCallback);
        }

        //Login fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        /* start */
        final LoginEditTextView emailtextfield = (LoginEditTextView)findViewById(R.id.emailtextfield);
        final LoginEditTextView passwordtextfield = (LoginEditTextView) findViewById(R.id.passwordtextfield);


        //login method 1: login with our registered account
        TonyButtonView loginbutton = (TonyButtonView) findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener","loginbutton onclick");
                String email = emailtextfield.getText().toString();
                if(email.equals("")){
                    alert.showAlertDialog(LoginActivity.this,"登入失敗","請輸入有效的用戶名！",false);
                    return;
                }
                String password = passwordtextfield.getText().toString();
                if(password.equals("")){
                    alert.showAlertDialog(LoginActivity.this,"登入失敗","請輸入正確的密碼！",false);
                    return;
                }
                /* on start progress */
                final ProgressDialog pd = alert.showProgress(LoginActivity.this, "登入", "登入中，請稍候...", true, false);
                ServerRequestManager.login(email, password, new ServerRequestManager.OnLoginCallback() {
                    @Override
                    public void onSuccessLogin(UserModel user) {
                        // Creating user login session
                        pd.dismiss();
                        session.createLoginSession(user.getUserHashMap());

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailedLogin(String msg) {
                        pd.dismiss();
                        Log.i("error message", msg);
                        alert.showAlertDialog(LoginActivity.this, "登入失敗..", msg, null);
                    }
                });
            }
        });

        //login method 2: login with fb account
        com.facebook.widget.LoginButton fbloginbutton = (com.facebook.widget.LoginButton) findViewById(R.id.fbloginbutton);
        fbloginbutton.setReadPermissions(Arrays.asList("public_profile", "user_status","email"));
        fbloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener","fbloginbutton onclick");

                // Check if there is any Active Session, otherwise Open New Session
                Session fbsession = Session.getActiveSession();

                if(!fbsession.isOpened()) {
                    fbsession.openForRead(new Session.OpenRequest(LoginActivity.this).setCallback(statusCallback));
                } else {
                    Session.openActiveSession(LoginActivity.this, true, statusCallback);
                }
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

    /********** Activity Methods **********/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("FbLogin", "Result Code is - " + resultCode +"");
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Save current session
        super.onSaveInstanceState(outState);
        Session fbsession = Session.getActiveSession();
        Session.saveSession(fbsession, outState);
    }

    @Override
    protected void onStart() {
        // TODO Add status callback
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    protected void onStop() {
        // TODO Remove callback
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
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
            if(fbsession!=null&&fbsession.isOpened()) {
                String access_token = fbsession.getAccessToken();
                Log.i("access_token", access_token);

                //Show Progress Dialog
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("使用Facebook 登入中..");
                dialog.show();

                ServerRequestManager.fblogin(access_token, new ServerRequestManager.OnLoginCallback() {
                    @Override
                    public void onSuccessLogin(UserModel user) {
                        // Creating user login session
                        session.createLoginSession(user.getUserHashMap());
                        if (dialog!=null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailedLogin(String msg) {
                        Log.i("error message", msg);
                        if (dialog!=null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        alert.showAlertDialog(LoginActivity.this, "登入失敗..", msg, null);
                    }
                });

            }

        }
    }


}
