package com.citifeel.app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author roytang
 * @see android.app.Activity
 * Second level activity
 *
 */
public abstract class StackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //second level activities' transition animation
        this.overridePendingTransition(R.anim.dim_in, R.anim.dim_out);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stack, menu);
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