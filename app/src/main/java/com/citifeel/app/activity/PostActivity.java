package com.citifeel.app.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.citifeel.app.BaseActivity;
import com.citifeel.app.R;
import com.facebook.Session;
import com.facebook.SessionState;

import java.io.File;

/**
 * Created by ywng on 16/7/14.
 */
public class PostActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

         /*set back icon*/
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(getResources().getDrawable( R.drawable.ic_action_back ));

        /* price & score spinners*/
        Spinner scores_spinner = (Spinner) findViewById(R.id.scores_spinner);

        ArrayAdapter<CharSequence> score_spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.scores_array, android.R.layout.simple_spinner_item);
        score_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scores_spinner.setAdapter(score_spinner_adapter);

        Spinner price_spinner = (Spinner) findViewById(R.id.price_spinner);
        ArrayAdapter<CharSequence> price_spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.price_array, android.R.layout.simple_spinner_item);
        price_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price_spinner.setAdapter(price_spinner_adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_confirm_reg:
                //post();
                break;
            case R.id.action_settings:
                //return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!finishPost()){
            /* back to login activity */
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            /* actually not very possible to come to this stage , just for place holder */
            super.onBackPressed();
        }
    }

    /********** Activity Methods **********/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Save current session
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onStart() {
        // TODO Add status callback
        super.onStart();

    }

    @Override
    protected void onStop() {
        // TODO Remove callback
        super.onStop();

    }



    private boolean finishPost() {
        return false;
    }



}
