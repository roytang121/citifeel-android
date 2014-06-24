package com.citifeel.app.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import com.citifeel.app.R;
import com.citifeel.app.UIApplication;
import com.citifeel.app.util.CommonUtils;

/**
 * Created by roytang on 24/6/14.
 */
public class Tag extends Drawable{

    private final String label;
    private final Paint bgPaint;
    private final TextPaint textPaint;
    private final int paddingLeft;

    public Tag(String label) {
        this.label = label;

        /* BG Paint */
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(UIApplication.getInstance().getResources().getColor(R.color.orange_strong));

        /* Text Paint */
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(CommonUtils.dp(UIApplication.getInstance(), 16));
        textPaint.setTextAlign(Paint.Align.CENTER);

        /* params */
        paddingLeft = CommonUtils.dp(UIApplication.getInstance(), 7);

        /* self */
        getBounds().right = (int) textPaint.measureText(label) + paddingLeft*2;
    }

    @Override
    public void draw(Canvas canvas) {
        RectF rect = new RectF(0, 0, getBounds().right, getBounds().bottom);
        canvas.drawRoundRect(rect, 20f, 20f, bgPaint);

        /* label text */
        canvas.drawText(label, getBounds().right/2, (getBounds().bottom/2 - (textPaint.descent() + textPaint.ascent())/2), textPaint);
    }

    public void setHeight(int height) {
        getBounds().bottom = height;
    }
    @Override
    public void setAlpha(int i) {
        /* void */
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        /* void */
    }

    @Override
    public int getOpacity() {
        return 255;
    }
}
