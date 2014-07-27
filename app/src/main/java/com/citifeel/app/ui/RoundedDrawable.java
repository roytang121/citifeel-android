package com.citifeel.app.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.citifeel.app.R;
import com.citifeel.app.UIApplication;

/**
 * Created by roytang on 13/7/14.
 */
public class RoundedDrawable extends Drawable {
    private Paint bgPaint;

    public RoundedDrawable() {
        /* BG Paint */
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(UIApplication.getInstance().getResources().getColor(R.color.orange_strong));
    }
    @Override
    public void draw(Canvas canvas) {
        RectF rect = new RectF(0, 0, getBounds().right, getBounds().bottom);
        canvas.drawRoundRect(rect, 25f, 25f, bgPaint);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 1;
    }


}
