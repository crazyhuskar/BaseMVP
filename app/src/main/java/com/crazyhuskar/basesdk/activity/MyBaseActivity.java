package com.crazyhuskar.basesdk.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crazyhuskar.basesdk.R;
import com.crazyhuskar.basesdk.SystemConfig;
import com.crazyhuskar.basesdk.util.MyUtilActivity;
import com.crazyhuskar.basesdk.util.bean.EventBean;
import com.crazyhuskar.basesdk.view.CommonTitle;
import com.gyf.barlibrary.ImmersionBar;
import com.noober.background.BackgroundLibrary;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * @author CrazyHuskar
 * @date 2018-10-25
 */
public abstract class MyBaseActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * 当前加载的视图界面
     **/
    protected View mContentView;
    /**
     * 公共标题
     */
    protected CommonTitle commonTitle;

    /**
     * 沉浸式标题栏
     */
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        mContentView = LayoutInflater.from(this).inflate(setLayoutID(), null);
        setContentView(mContentView);
        if (registerActivity() != null) {
            init();
            initEventBus();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (registerActivity() != null) {
            EventBus.getDefault().unregister(registerActivity());
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        MyUtilActivity.getInstance().finishActivity(registerActivity());
    }

    /**
     * 初始化EventBus
     */
    public void initEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(registerActivity());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(EventBean eventBean) {
        if (eventBean.getFlag() == SystemConfig.EXITAPP) {
            MyUtilActivity.getInstance().finishActivity(registerActivity());
        }
    }

    /**
     * 初始化视图ID
     *
     * @return
     */
    public abstract int setLayoutID();

    /**
     * 注册当前Activity
     */
    public abstract AppCompatActivity registerActivity();

    /**
     * 初始化activity
     */
    public abstract void init();

    /**
     * 初始化沉浸式标题栏
     */
    public void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 设置标题栏
     *
     * @param centerTxt 文字或id
     */
    public void setCommonTitle(Object centerTxt) {
        setCommonTitle(null, centerTxt, null, R.drawable.arrow_back_24dp, null, null, null, null);
    }

    /**
     * 设置标题栏
     *
     * @param centerTxt 文字或id
     * @param leftRes   左边按钮图片id
     */
    public void setCommonTitle(Object centerTxt, Object leftRes) {
        setCommonTitle(null, centerTxt, null, leftRes, null, null, null, null);
    }

    /**
     * 设置标题栏
     *
     * @param leftText   文字或id
     * @param centerText 文字或id
     * @param rightText  文字或id
     * @param leftRes    图片id
     * @param rightRes   图片id
     * @param bgColor    #ffffffff
     * @param lineColor  #ffffffff
     */
    public void setCommonTitle(Object leftText, Object centerText, Object rightText, Object leftRes, Object rightRes,
                               Object textColor, Object bgColor, Object lineColor) {
        TextView left_text = findViewById(R.id.common_title_left_text);
        TextView right_text = findViewById(R.id.common_title_right_text);
        TextView center_text = findViewById(R.id.common_title_center_text);
        if (textColor != null) {
            if (textColor instanceof String) {
                left_text.setTextColor(Color.parseColor((String) textColor));
                center_text.setTextColor(Color.parseColor((String) textColor));
                right_text.setTextColor(Color.parseColor((String) textColor));
            } else if (textColor instanceof Integer) {
                left_text.setTextColor(((Integer) textColor));
                center_text.setTextColor(((Integer) textColor));
                right_text.setTextColor(((Integer) textColor));
            }
        }
        commonTitle = new CommonTitle(this, (ImageButton) findViewById(R.id.common_title_left_img),
                (ImageButton) findViewById(R.id.common_title_right_img),
                left_text,
                center_text,
                right_text,
                findViewById(R.id.view_common_title),
                findViewById(R.id.view_common_title_line));
        commonTitle.addCommonTitle(leftText, centerText, rightText, leftRes, rightRes, bgColor, lineColor);
    }
}
