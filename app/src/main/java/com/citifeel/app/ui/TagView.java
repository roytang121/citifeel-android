package com.citifeel.app.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citifeel.app.R;

/**
 * Created by roytang on 13/7/14.
 */
public class TagView extends LinearLayout {
    private TextView text;
    private String tag = "Tag";
    private RoundedDrawable bg;

    public TagView(Context context) {
        super(context);
        init();
    }

    public TagView(Context context, String tag) {
        super(context);
        this.tag = tag;
        init();
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        TagView.inflate(getContext(), R.layout.view_tag, this);
        text = (TextView) findViewById(R.id.tagText);
        bg = new RoundedDrawable();
        setWillNotDraw(false);

        text.setText(tag);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bg.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        bg.draw(canvas);
    }
}
