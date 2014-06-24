package com.citifeel.app;

import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.dim_in, R.anim.dim_out);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
