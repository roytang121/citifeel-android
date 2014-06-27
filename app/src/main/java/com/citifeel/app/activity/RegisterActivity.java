package com.citifeel.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import com.citifeel.app.R;
import com.citifeel.app.ui.FlowLayout.LayoutParams;
import android.text.method.LinkMovementMethod;
import android.text.Html;
import android.app.ActionBar;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewGroup flowContainer = (ViewGroup) findViewById(R.id.flow_layout);
        TextView statTextView=new TextView(this);
        statTextView.setText(Html.fromHtml("閣下確認註冊CitiFeel帳戶即表示閣下同意本公司之<a href=\"http://www.google.com\">使用條款</a>及<a href=\"http://www.google.com\">私隱聲明</a> 。"));
        statTextView.setMovementMethod(LinkMovementMethod.getInstance());

        flowContainer.addView(statTextView,
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(getResources().getDrawable( R.drawable.ic_action_back ));

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

    private boolean finishRegister() {
        return false;
    }


}
