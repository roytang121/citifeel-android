package com.citifeel.app.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.citifeel.app.util.CommonUtils;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

/**
 * Created by roytang on 8/5/14.
 */
public class LoginEditTextView extends EditText implements View.OnFocusChangeListener{
    int width, height;
    Spring spring;
    boolean pressed = false;
    
    public LoginEditTextView(Context context) {
        super(context);
        init();
    }

    public LoginEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setOnFocusChangeListener(this);
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        this.width = MeasureSpec.getSize(widthSpec);
        this.height = CommonUtils.dp(getContext(), 40) + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(width, height);
    }


//    @Override
//    public void onSizeChanged(int l, int t, int r, int b){
//        this.width = r;
//        this.height = b;
//    }

    @Override
    public void onDraw(Canvas canvas){
        LoginEditTextDrawable d = new LoginEditTextDrawable(getContext());
        d.setBounds(new Rect(0, 0, width, height));
        d.draw(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            pressed = true;
            onPress();
        } else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
            pressed = false;
            onRelease();
        }

        return true;
    }

    private void onPress() {
        this.setFocusableInTouchMode(true);
        requestFocus();

        SpringSystem ss = SpringSystem.create();
        spring = ss.createSpring();
        spring.setVelocity(50);
        spring.addListener(new SimpleSpringListener(){
            @Override
            public void onSpringUpdate(Spring spring){
                float value = (float) spring.getCurrentValue();
                float scale = (float) (1 - value*0.1);
                setScaleX(scale);
                setScaleY(scale);
            }
        });

        spring.setEndValue(1);
    }

    private void onRelease() {
        spring.setEndValue(0);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(isInEditMode()) return;
        if(b) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
        }else{
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
    }
}
