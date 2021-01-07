package com.crazyhuskar.basesdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
/**
 * 滑动菜单
*@author CrazyHuskar
*caeat at 2021/1/7  16:42
*/
public class SwipeMenu extends HorizontalScrollView {

    private Context context;
    /**
     * 左侧菜单宽度
     */
    private int mLeftMenuWidth = 0;

    /**
     * 右侧菜单宽度
     */
    private int mRightMenuWidth = 0;

    /**
     * 滑动超过如下比例，就自动完整显示菜单。
     */
    private double mShowMenuRatio = 0.5;
    private boolean hasMeasured = false;
    private float scrollX = 0;
    private int flag = 1;

    public SwipeMenu(Context context) {
        super(context);
        this.context = context;
    }

    public SwipeMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SwipeMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public SwipeMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 显式设置中间内容区域宽度
         */
        if (!hasMeasured) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            ViewGroup mContent = (ViewGroup) wrapper.getChildAt(1);
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mContent.getLayoutParams();
            linearParams.width = getScreenWidth();
            mContent.setLayoutParams(linearParams);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            mLeftMenuWidth = wrapper.getChildAt(0).getWidth();
            mRightMenuWidth = wrapper.getChildAt(2).getWidth();
            hasMeasured = true;
        }
        //隐藏菜单，显示主体内容
        hideMenus();
    }

    /**
     * 左右滑动，超过菜单本身宽度一定比例，就完整显示菜单。
     *
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (scrollX == 0) {
            scrollX = ev.getX();
        } else if (MotionEvent.ACTION_UP == action) {
            if (((scrollX - ev.getX()) > mRightMenuWidth * mShowMenuRatio) && flag != 0) {
                flag = 2;
                smoothScrollTo(mLeftMenuWidth + mRightMenuWidth, 0);
            } else if (((scrollX - ev.getX()) < -mLeftMenuWidth * mShowMenuRatio) && flag != 2) {
                flag = 0;
                smoothScrollTo(0, 0);
            } else {
                hideMenus();
            }
            scrollX = 0;
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 正常显示内容，隐藏菜单
     */
    public void hideMenus() {
        flag = 1;
        smoothScrollTo(mLeftMenuWidth, 0);
    }
}

