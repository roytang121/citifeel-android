package com.citifeel.app.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.citifeel.app.util.CommonUtils;

/**
 * Created by roytang on 8/5/14.
 */
public class LoginEditTextDrawable extends Drawable {
    int height, width;
    Paint mBgPaint;
    RectF bgRect;
    Context context;

    public LoginEditTextDrawable(Context context){
        this.context = context;
        init();
    }

    private void init(){
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setStyle(Paint.Style.FILL);
        bgRect = new RectF();
    }

    @Override
    public int getOpacity(){
        return 1;
    }

    @Override
    public void setAlpha(int alpha){
        mBgPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf){

    }

    @Override
    public void setBounds(Rect rect){
        super.setBounds(rect);
        this.width = rect.right;
        this.height = rect.bottom;
    }

    public void setColor(int color){
        mBgPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        bgRect.top = 0;
        bgRect.left = 0;
        bgRect.right = width;
        bgRect.bottom = height;

        //Log.i("spec", width + "," + height);
        canvas.drawRoundRect(bgRect, CommonUtils.dp(context, 4), CommonUtils.dp(context, 4), mBgPaint);
    }
}
