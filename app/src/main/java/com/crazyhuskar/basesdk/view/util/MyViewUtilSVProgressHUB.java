package com.crazyhuskar.basesdk.view.util;

import android.content.Context;
import android.text.TextUtils;

import com.bigkoo.svprogresshud.SVProgressHUD;

/**
 * @author CrazyHuskar
 * @date 2018-10-27
 */
public class MyViewUtilSVProgressHUB {
    private static MyViewUtilSVProgressHUB myViewUtilSVProgressHUB;
    private SVProgressHUD mProgressHUD;

    public static MyViewUtilSVProgressHUB getInstance() {
        if (myViewUtilSVProgressHUB == null) {
            myViewUtilSVProgressHUB = new MyViewUtilSVProgressHUB();
        }
        return myViewUtilSVProgressHUB;
    }

    /**
     * 初始化
     *
     * @param mContext
     */
    public void init(Context mContext) {
        mProgressHUD = new SVProgressHUD(mContext);
    }

    /**
     * 加载提示
     *
     * @param content
     */
    public void showProgressHUD(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "加载提示";
        }
        if (mProgressHUD.isShowing()) {
            mProgressHUD.dismissImmediately();
        }
        mProgressHUD.showWithStatus(content);
    }

    /**
     * 信息提示
     *
     * @param content
     */
    public void showProgressHUDWithInfo(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "信息提示";
        }
        if (mProgressHUD.isShowing()) {
            mProgressHUD.dismissImmediately();
        }
        mProgressHUD.showInfoWithStatus(content);
    }

    /**
     * 成功提示
     *
     * @param content
     */
    public void showProgressHUDWithSuccess(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "成功提示";
        }
        if (mProgressHUD.isShowing()) {
            mProgressHUD.dismissImmediately();
        }
        mProgressHUD.showSuccessWithStatus(content);
    }

    /**
     * 失败提示
     *
     * @param content
     */
    public void showProgressHUDWithError(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "失败提示";
        }
        if (mProgressHUD.isShowing()) {
            mProgressHUD.dismissImmediately();
        }
        mProgressHUD.showErrorWithStatus(content);
    }

    /**
     * 进度提示
     *
     * @param content
     */
    public void showProgressHUDWithProgress(String content_msg, int content) {
        if (content < 0) {
            content = 0;
        }
        if (mProgressHUD.isShowing()) {
            if (mProgressHUD.getProgressBar().getMax() != mProgressHUD.getProgressBar().getProgress()) {
                mProgressHUD.getProgressBar().setProgress(content);
                mProgressHUD.setText(content_msg + content + "%");
            }
        } else {
            mProgressHUD.showWithProgress(content_msg + content + "%", SVProgressHUD.SVProgressHUDMaskType.Black);
        }
    }

    /**
     * 取消提示框
     */
    public void dismissProgressHUD() {
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            mProgressHUD.dismiss();
        }
    }
}
