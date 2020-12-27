package com.crazyhuskar.basesdk;


import com.crazyhuskar.basesdk.util.MyUtilSDCard;

/**
 * 系统常量
 *
 * @author CrazyHuskar
 * @date 2018/6/1
 */
public interface SystemConfig {


    int EXITAPP = -100;

    /**
     * SD卡根目录
     */
    String SDCARD_DIR = MyUtilSDCard.getSDCardPath() + "/APP/";
    /**
     * 图片缓存目录
     */
    String PHOTO_CACHE_DIR = SDCARD_DIR + "/ImageCache/";
    /**
     * 文件下载目录
     */
    String DOWNLOAD_FILE_DIR = SDCARD_DIR + "/Download/";
    /**
     * 保存在手机里面的文件名
     */
    String SP_FILE_NAME = "share_data";

    /**
     * 拍照请求
     */
    int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    /**
     * 录像请求
     */
    int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 101;
    /**
     * 权限请求
     */
    int MY_PERMISSION_REQUEST_CODE = 200;
}
