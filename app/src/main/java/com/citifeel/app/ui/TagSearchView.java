package com.citifeel.app.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.citifeel.app.R;

/**
 * Created by roytang on 24/6/14.
 */
public class TagSearchView extends SearchView implements SearchView.OnQueryTextListener{
    private TextView tt;

    public TagSearchView(Context context) {
        super(context);
        init();
    }

    public TagSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        /* listener */
        setOnQueryTextListener(this);
        /* text view */
        int id = getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        tt = (TextView) findViewById(id);
        tt.setTextColor(Color.WHITE);
        tt.setHintTextColor(Color.WHITE);

        int searchImgId = getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView icon = (ImageView) findViewById(searchImgId);
        icon.setImageResource(R.drawable.ic_action_search);

    }

    public void addTag(String tag) {
        Tag d = new Tag("tag");
        int tagH = getHeight() / 2;
        d.setHeight(tagH);
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("app [tag]");
        builder.setSpan(new ImageSpan(d), builder.length() - "[tag]".length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append("[tag] app");
        builder.setSpan(new ImageSpan(d), builder.length() - "[tag]".length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tt.setText(builder);
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
//        s = s.replace("[tag]", "");
        addTag(s);
        return true;
    }
}
