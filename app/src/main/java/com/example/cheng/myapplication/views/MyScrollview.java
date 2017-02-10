package com.example.cheng.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by cheng on 2017/1/19.
 */

public class MyScrollview extends ScrollView {
    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                super.onTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                return false;
            case MotionEvent.ACTION_CANCEL:
                super.onTouchEvent(ev);
                break;
            case MotionEvent.ACTION_UP:
                return false;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
