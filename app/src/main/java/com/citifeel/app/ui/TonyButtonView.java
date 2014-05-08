package com.citifeel.app.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.citifeel.app.util.CommonUtils;

/**
 * Created by roytang on 8/5/14.
 */
public class TonyButtonView extends View {
    int width,height;
    String text = "登入";
    Paint mTextPaint;

    public TonyButtonView(Context context) {
        super(context);
        init();
    }

    public TonyButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TonyButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(CommonUtils.dp(getContext(), 18));
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec){
        this.width = MeasureSpec.getSize(widthSpec);
        this.height = CommonUtils.dp(getContext(), 40) + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        LoginEditTextDrawable d = new LoginEditTextDrawable(getContext());
        d.setBounds(new Rect(0,0,width,height));
        d.setAlpha(127);
        d.draw(canvas);
        Rect tb = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), tb);
        canvas.drawText(text, width/2, height/2 + tb.bottom/2 + mTextPaint.descent(), mTextPaint);
    }

}
