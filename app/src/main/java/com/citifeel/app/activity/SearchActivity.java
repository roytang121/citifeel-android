package com.citifeel.app.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.citifeel.app.R;
import com.citifeel.app.StackActivity;
import com.citifeel.app.ui.TagSearchView;
import com.citifeel.app.ui.TagView;
import com.citifeel.app.util.CommonUtils;

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

//        SearchView sv = (SearchView) root.findViewById(R.id.search_view);
        final TagSearchView sv = (TagSearchView) findViewById(R.id.search_view);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.layoutTags);

        sv.setOnTagSelectedListener(new TagSearchView.OnTagSelectedListener() {
            @Override
            public void onTagSelected(String tag) {
                TagView tagView = new TagView(SearchActivity.this, tag);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, CommonUtils.dp(SearchActivity.this, 5), 0);
                tagView.setLayoutParams(params);
                ll.addView(tagView);
                sv.setText("");
            }
        });

    }
}
