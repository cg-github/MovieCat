package com.example.cheng.myapplication.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by cheng on 2017/2/24.
 */

public final class ToastUtil {
    private ToastUtil() {
    }

    private static Toast mToast = null;
    public static void show(final Context context , String msg){
        if (mToast == null){
            mToast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
