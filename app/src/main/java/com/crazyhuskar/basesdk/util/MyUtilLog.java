package com.crazyhuskar.basesdk.util;

import com.crazyhuskar.basesdk.activity.MyBaseApplication;
import com.orhanobut.logger.Logger;



/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */

public class MyUtilLog {
    /**
     * @param msg 内容
     */
    public static void i(String msg) {
        if (MyBaseApplication.getInstance().isDebug()) {
            Logger.d(msg);
        }
    }

    /**
     * @param msg 内容
     */
    public static void d(String msg) {
        if (MyBaseApplication.getInstance().isDebug()) {
            Logger.d(msg);
        }
    }

    /**
     * @param msg 内容
     */
    public static void e(String msg) {
        if (MyBaseApplication.getInstance().isDebug()) {
            Logger.e(msg);
        }
    }

    /**
     * @param msg 内容
     */
    public static void v(String msg) {
        if (MyBaseApplication.getInstance().isDebug()) {
            Logger.v(msg);
        }
    }
}
