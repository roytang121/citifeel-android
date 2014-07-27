package com.citifeel.app.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.PopupMenu;

import java.util.ArrayList;

/**
 * Created by roytang on 24/6/14.
 */
public class TagSearchView extends AutoCompleteTextView implements AdapterView.OnItemClickListener{
    private static final String TAG = TagSearchView.class.getSimpleName();
    private PopupMenu popup;
    private ArrayList<String> suggestions = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;
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
        mAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,suggestions);
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
            suggestions.clear();
            suggestions.add(editable.toString());
            mAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,suggestions);
            setAdapter(mAdapter);
            if(editable.length() > 0) showDropDown();
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        if(this.l != null) {
            l.onTagSelected(suggestions.get(i));
        }
    }

    public interface OnTagSelectedListener{
        public void onTagSelected(String tag);
    }

    public void setOnTagSelectedListener(OnTagSelectedListener l) {
        this.l = l;
    }
}
