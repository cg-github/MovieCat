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
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case
        }
        return super.onTouchEvent(ev);
    }
}
