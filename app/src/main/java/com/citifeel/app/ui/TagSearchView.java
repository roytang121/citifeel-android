package com.citifeel.app.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.citifeel.app.R;
import com.citifeel.app.adapter.TagSeachAdapter;
import com.citifeel.app.model.TagModel;

import java.util.ArrayList;

/**
 * Created by roytang on 24/6/14.
 */
public class TagSearchView extends AutoCompleteTextView implements AdapterView.OnItemClickListener{
    private static final String TAG = TagSearchView.class.getSimpleName();
    private PopupMenu popup;
    private ArrayList<TagModel> suggestions = new ArrayList<TagModel>();
    private TagSeachAdapter mAdapter;
    private OnTagSelectedListener l;

    public TagSearchView(Context context) {
        super(context);
        init();
    }

    public TagSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        fakeData();
        mAdapter = new TagSeachAdapter(getContext(), suggestions);
        setAdapter(mAdapter);

        addTextChangedListener(watcher);

        setOnItemClickListener(this);
    }

    public void addTag(String tag) {
//        Log.i(TAG, "called addTag once");
//        Tag d = new Tag(tag);
//        int tagH = getHeight() / 2;
//        d.setHeight(tagH);
//        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
//
//        SpannableStringBuilder builder = new SpannableStringBuilder();
//        builder.append(editText.getText());      //append original text in edittext field
//        builder.append("[tag]");
//        builder.setSpan(new ImageSpan(d), builder.length() - "[tag]".length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tt.setText(builder);
//
//        editText.setSelection(editText.length());
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
//            Log.i("ADS", editable.toString());
//            suggestions.clear();
//            suggestions.add(new TagModel(editable.toString()));
//            mAdapter = new TagSeachAdapter(getContext(), suggestions);
//            setAdapter(mAdapter);
//            if(editable.length() > 0) showDropDown();
//            showDropDown();
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        if(this.l != null) {
            TextView tt = (TextView) view.findViewById(R.id.tag);
            l.onTagSelected(tt.getText().toString());
        }
    }

    public interface OnTagSelectedListener{
        public void onTagSelected(String tag);
    }

    public void setOnTagSelectedListener(OnTagSelectedListener l) {
        this.l = l;
    }

    private void fakeData() {
        String[] set = {
                "一田",
                "椰林閣",
                "GigaSports",
                "GigaBytes",
                "Giga",
                "Wastons",
                "Ruby Tuesday"
        };

        for(String s : set) {
            suggestions.add(new TagModel(s));
        }
    }
}
