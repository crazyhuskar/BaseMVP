package com.crazyhuskar.basesdk;

import android.graphics.Color;


/**
 * activity切换动画
 */
public interface ActivityAnim {

    /**
     * Activity切换动画</br>
     * 00 01 淡入淡出效果</br>
     * 10 11 放大淡出效果</br>
     * 20 21 转动淡出效果1</br>
     * 30 31 转动淡出效果2</br>
     * 40 41 左上角展开淡出效果</br>
     * 50 51 压缩变小淡出效果</br>
     * 60 61 右往左推出效果</br>
     * 70 71 下往上推出效果</br>
     * 80 81 左右交错效果</br>
     * 90 91 放大淡出效果</br>
     * 100 101 缩小效果</br>
     * 110 111 上下交错效果
     * 120 121 左往右推出效果</br>
     */
    int[][] ACTIVITY_ANIMATION = {
            {R.anim.fade_in, R.anim.fade_out},
            {R.anim.scale_in, R.anim.alpha_out},
            {R.anim.scale_rotate_in, R.anim.alpha_out},
            {R.anim.scale_translate_rotate, R.anim.alpha_out},
            {R.anim.scale_translate, R.anim.alpha_out},
            {R.anim.hyperspace_in, R.anim.hyperspace_out},
            {R.anim.push_left_in, R.anim.push_left_out},
            {R.anim.push_up_in, R.anim.push_up_out},
            {R.anim.slide_left, R.anim.slide_right},
            {R.anim.wave_scale, R.anim.alpha_out},
            {R.anim.zoom_enter, R.anim.zoom_exit},
            {R.anim.slide_up_in, R.anim.slide_down_out},
            {R.anim.push_right_in, R.anim.push_right_out}
    };

    /**
     * start_activity基础动画序号
     **/
    int BASE_ANIM_START = 6;
    /**
     * 当前使用界面切换效果in
     **/
    int NOW_ACTIVITY_IN = ACTIVITY_ANIMATION[BASE_ANIM_START][0];
    /**
     * 当前使用界面切换效果out
     **/
    int NOW_ACTIVITY_OUT = ACTIVITY_ANIMATION[BASE_ANIM_START][1];

    /**
     * finish_activity基础动画序号
     **/
    int BASE_ANIM_FINISH = 12;
    /**
     * 当前使用界面切换效果in
     **/
    int NOW_ACTIVITY_FINISH_IN = ACTIVITY_ANIMATION[BASE_ANIM_FINISH][0];
    /**
     * 当前使用界面切换效果out
     **/
    int NOW_ACTIVITY_FINISH_OUT = ACTIVITY_ANIMATION[BASE_ANIM_FINISH][1];

    int[] REFRESH_COLORS = {Color.parseColor("#dd191d"), Color.parseColor("#ffca28"), Color.parseColor("#039be5"), Color.parseColor("#0a8f08"), Color.parseColor("#f57c00")};


}
