package com.crazyhuskar.basesdk.activity;

import android.app.Application;

import com.crazyhuskar.basesdk.util.MyUtilAppInfo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.multidex.MultiDex;


/**
 * @author CrazyHuskar
 * @date 2018-10-25
 */

public class MyBaseApplication extends Application {
    private static MyBaseApplication myBaseApplication;
    private  boolean isDebug = true;

    public static MyBaseApplication getInstance() {
        if (myBaseApplication == null) {
            myBaseApplication = new MyBaseApplication();
        }
        return myBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isDebug = MyUtilAppInfo.isDebug(this);
        MultiDex.install(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public boolean isDebug() {
        return isDebug;
    }
}
