package com.crazyhuskar.basesdk.view.selector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.crazyhuskar.basesdk.R;

/**
 * Created by yx on 2018/7/1.
 * 字母列表
 */

public class LetterView extends AppCompatButton {
    public LetterView(Context context) {
        super(context);//默认构造
    }

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);//重载
    }

    public LetterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);//重载
    }

    // 首字母表
    private String[] LETTER = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};
    //画笔
    private Paint paint = new Paint();
    // 选择的索引
    //private int selectIndex = 0;
    private int selectIndex = -1;

    //画笔绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取到view的宽高
        int height = getHeight();
        int width = getWidth();
        //计算每一项的间隔
        int interval = height / LETTER.length;
        //循环绘画出字母
        for (int i = 0, length = LETTER.length; i < length; i++) {
            // 抗锯齿
            paint.setAntiAlias(true);
            // 默认粗体
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            // 橙色色字体
            paint.setColor(Color.parseColor("#f09636"));
            if (i == selectIndex) {
                // 被选择的字母改变颜色和粗体
                paint.setColor(Color.parseColor("#f09636"));
                paint.setFakeBoldText(true);//中文仿“粗体”
                paint.setTextSize(30);// 选择后字体放大到30sp
            }
            // 计算字母的X坐标（计算字母绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半）
            float xPos = width / 2 - paint.measureText(LETTER[i]) / 2;// measureText取得字符串的宽
            // 计算字母的Y坐标（计算字母绘制的起点Y坐标 ，计算方式：当前项高度 - 每项间隔）
            float yPos = interval * i + interval;
            canvas.drawText(LETTER[i], xPos, yPos, paint);
            // 清空笔刷；
            paint.reset();

        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 触摸位置坐标
        float y = event.getY();
        //触摸位置最接近的字母下标index
        int index = (int) (y / getHeight() * LETTER.length);
        if (index >= 0 && index < LETTER.length) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    // 如果滑动改变
                    if (selectIndex != index) {
                        selectIndex = index;
                        if (onTouch != null) {
                            onTouch.onTouchAssortListener(LETTER[selectIndex]);
                        }

                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    // 屏幕被按下
                    selectIndex = index;
                    if (onTouch != null) {
                        // 这个方法是在OnTouchAssortListener接口定义的方法
                        onTouch.onTouchAssortListener(LETTER[selectIndex]);
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if (onTouch != null) {
                        onTouch.onTouchAssortUP();
                    }
                    selectIndex = -1;
                    break;
            }
        } else {
            selectIndex = -1;
            if (onTouch != null) {
                onTouch.onTouchAssortUP();
            }
        }
        invalidate();

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    public interface OnTouchAssortListener {
        void onTouchAssortListener(String s);

        void onTouchAssortUP();
    }

    // 字母选中监听器
    private OnTouchAssortListener onTouch;

    public void setOnTouchAssortListener(OnTouchAssortListener onTouch) {
        this.onTouch = onTouch;
    }

    /**
     * 字母选中后在屏幕中间提示
     * 并且选中字母对应的双层Listview中数据行
     *
     * @param letterView
     */
    public static void setLetterDialog(final Context context, final LetterView letterView, final ExpandableListView city_elist, final SelectorAdapter selectorAdapter) {

        letterView.setOnTouchAssortListener(new OnTouchAssortListener() {
            PopupWindow popupWindow;
            View v = LayoutInflater.from(context).inflate(R.layout.pinyin_dialog_letter, null);
            TextView text = (TextView) v.findViewById(R.id.alert_content);

            @Override
            public void onTouchAssortListener(String s) {
                Log.i("aaaa", "----" + s);
                letterView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                int index = selectorAdapter.getAssort().getHashList().indexOfKey(s);
                Log.i("aaaa", "----index----" + index);
                if (index != -1) {
                    // 显示选中的字母对应的Listview的项Group
                    city_elist.setSelectedGroup(index);
                }
                if (popupWindow == null) {
                    // popupWindow自适应大小
                    popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    // 显示在Activity的根视图中心
                    popupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                }
                //当前选中字母是：
                text.setText(s);
            }

            @Override
            public void onTouchAssortUP() {
                letterView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                if (popupWindow != null)
                    popupWindow.dismiss();
                popupWindow = null;

            }
        });
    }


}
