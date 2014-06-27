package com.citifeel.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.citifeel.app.R;
import com.citifeel.app.ui.FlowLayout.LayoutParams;
import com.citifeel.app.ui.RoundedImageView;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.graphics.*;
import android.text.method.LinkMovementMethod;
import android.text.Html;
import android.app.ActionBar;
import java.io.InputStream;

import java.util.Arrays;

public class RegisterActivity extends Activity {
    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private TextView userNameView;
    private RoundedImageView profilePicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewGroup flowContainer = (ViewGroup) findViewById(R.id.flow_layout);
        TextView statTextView=new TextView(this);
        statTextView.setText(Html.fromHtml("閣下確認註冊CitiFeel帳戶即表示閣下同意本公司之<a href=\"http://www.google.com\">使用條款</a>及<a href=\"http://www.google.com\">私隱聲明</a> 。"));
        statTextView.setMovementMethod(LinkMovementMethod.getInstance());

        userNameView= (TextView) findViewById(R.id.usernametextfield);
        profilePicView=(RoundedImageView) findViewById(R.id.profileImageView);

        flowContainer.addView(statTextView,
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(getResources().getDrawable( R.drawable.ic_action_back ));

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

        //login method 2: login with fb account
        com.facebook.widget.LoginButton fbloginbutton = (com.facebook.widget.LoginButton) findViewById(R.id.usefbinfobutton);
        fbloginbutton.setReadPermissions(Arrays.asList("public_profile", "user_about_me"));
        fbloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener", "fbloginbutton onclick");

                // Check if there is any Active Session, otherwise Open New Session
                Session fbsession = Session.getActiveSession();

                if (!fbsession.isOpened()) {
                    fbsession.openForRead(new Session.OpenRequest(RegisterActivity.this).setCallback(statusCallback));
                } else {
                    Session.openActiveSession(RegisterActivity.this, true, statusCallback);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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

    @Override
    public void onBackPressed() {
        if(!finishRegister()){
            /* back to login activity */
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            /* actually noy very possible to come to this stage , just for place holder */
            super.onBackPressed();
        }
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

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session fbsession, SessionState state, Exception exception) {
            //make request to server to validate the login by fb
            //post access token to server
            Log.i("fb session state", state.toString());
            if(fbsession!=null&&fbsession.isOpened()) {
                String access_token = fbsession.getAccessToken();
                Log.i("access_token", access_token);

                makeMeRequest(fbsession);


            }

        }
    }

    private boolean finishRegister() {
        return false;
    }

    private void makeMeRequest(Session fbsession) {
        Request request = Request.newMeRequest(fbsession,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (user != null) {
                            new DownloadImageTask((ImageView) profilePicView)
                                    .execute("https://graph.facebook.com/"+user.getId()+"/picture?width=999&height=999");

                            userNameView.setText(user.getName());

                        } else if (response.getError() != null) {
                            // handle error
                        }
                    }
                });
        request.executeAsync();

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
