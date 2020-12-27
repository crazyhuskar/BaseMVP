package com.crazyhuskar.basesdk.util;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */

public class MyUtil {



    /**
     * 沉浸式状态栏
     *
     * @param activity
     */
    public static void setWindowStatus(Activity activity, int colorPrimary) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
