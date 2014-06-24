package com.citifeel.app.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.citifeel.app.R;
import com.citifeel.app.StackActivity;

public class SearchActivity extends StackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /* config action bar ui */
        configActionBar();
    }

    private void configActionBar() {
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        View root = LayoutInflater.from(this).inflate(R.layout.actionbar_search, null);
        getActionBar().setCustomView(root,
                new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT));

        SearchView sv = (SearchView) root.findViewById(R.id.search_view);
    }
}
