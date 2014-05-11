package com.citifeel.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

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

        final LoginEditTextView email_input = (LoginEditTextView) findViewById(R.id.email_input);
        final LoginEditTextView password_input = (LoginEditTextView) findViewById(R.id.password_input);


        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email_input, InputMethodManager.SHOW_IMPLICIT);

        TonyButtonView loginbutton = (TonyButtonView) findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "Log in..", "Please wait while login", false);

                String email = email_input.getText().toString();
                String password = password_input.getText().toString();

                ServerRequestManager.login(email, password, new ServerRequestManager.OnLoginCallback() {
                    @Override
                    public void onSuccessLogin(UserModel model) {
                        pd.dismiss();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailedLogin(String msg) {
                        pd.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(msg);
                        builder.show();
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
