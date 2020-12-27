package com.crazyhuskar.basesdk.util;

import java.util.UUID;

/**
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public class MyUtilUUID {

    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
