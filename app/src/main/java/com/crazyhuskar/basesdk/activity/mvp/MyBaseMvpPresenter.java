package com.crazyhuskar.basesdk.activity.mvp;

import android.os.Bundle;

/**
 * @author CrazyHuskar
 * @date 2018-10-25
 */
public interface MyBaseMvpPresenter<V extends MyBaseMvpView> {

    /**
     * create
     * @param view
     * @param savedInstanceState
     */
    void onMvpAttachView(V view, Bundle savedInstanceState);

    /**
     * start
     */
    void onMvpStart();

    /**
     * resume
     */
    void onMvpResume();

    /**
     * pause
     */
    void onMvpPause();

    /**
     * stop
     */
    void onMvpStop();

    /**
     *
     * @param savedInstanceState
     */
    void onMvpSaveInstanceState(Bundle savedInstanceState);

    /**
     *
     * @param retainInstance
     */
    void onMvpDetachView(boolean retainInstance);

    /**
     * destory
     */
    void onMvpDestroy();
}