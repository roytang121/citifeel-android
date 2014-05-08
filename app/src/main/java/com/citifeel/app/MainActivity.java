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
import com.citifeel.app.core.ServerRequestManager;
import com.citifeel.app.model.UserModel;
import com.citifeel.app.ui.LoginEditTextView;
import com.citifeel.app.ui.TonyButtonView;
import com.citifeel.app.util.CommonUtils;

/**
 * @author roytang
 * Launcher acitivty
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Login fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        /* start */

        LoginEditTextView edittext = (LoginEditTextView) findViewById(R.id.edittext);
        TonyButtonView loginbutton = (TonyButtonView) findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "jason.ng@citifeel.com";
                String password = "abcd1919";

                ServerRequestManager.login(email, password, new ServerRequestManager.OnLoginCallback() {
                    @Override
                    public void onSuccessLogin(UserModel model) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailedLogin(String msg) {
                        Log.i("asdf", "sdf");
                    }
                });
            }
        });
        CommonUtils.logKeyHash(this);
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
}
