package com.crazyhuskar.basesdk.view.calendar.behavior;

import android.util.Log;
import android.view.View;

import com.crazyhuskar.basesdk.view.calendar.CalendarUtil;
import com.crazyhuskar.basesdk.view.calendar.component.CalendarViewAdapter;
import com.crazyhuskar.basesdk.view.calendar.view.MonthPager;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by ldf on 17/6/15.
 */

public class MonthPagerBehavior extends CoordinatorLayout.Behavior<MonthPager> {
    private int top;
    private int touchSlop = 1;
    private int offsetY = 0;

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, MonthPager child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, MonthPager child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        child.offsetTopAndBottom(top);
        return true;
    }

    private int dependentViewTop = -1;

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, MonthPager child, View dependency) {
        CalendarViewAdapter calendarViewAdapter = (CalendarViewAdapter) child.getAdapter();
        if (dependentViewTop != -1) {
            int dy = dependency.getTop() - dependentViewTop;    //dependency对其依赖的view(本例依赖的view是RecycleView)

            int top = child.getTop();

            if( dy > touchSlop){
                calendarViewAdapter.switchToMonth();
            } else if(dy < - touchSlop){
                calendarViewAdapter.switchToWeek(child.getRowIndex());
            }

            if (dy > -top){
                dy = -top;
            }

            if (dy < -top - child.getTopMovableDistance()){
                dy = -top - child.getTopMovableDistance();
            }

            child.offsetTopAndBottom(dy);
            Log.e("ldf","onDependentViewChanged = " + dy);

        }

        dependentViewTop = dependency.getTop();
        top = child.getTop();

        if(offsetY > child.getCellHeight()) {
            calendarViewAdapter.switchToMonth();
        }
        if(offsetY < -child.getCellHeight()) {
            calendarViewAdapter.switchToWeek(child.getRowIndex());
        }

        if(dependentViewTop > child.getCellHeight() - 24
                && dependentViewTop < child.getCellHeight() + 24
                && top > - touchSlop - child.getTopMovableDistance()
                && top < touchSlop - child.getTopMovableDistance()) {
            CalendarUtil.setScrollToBottom(true);
            calendarViewAdapter.switchToWeek(child.getRowIndex());
            offsetY = 0;
        }
        if(dependentViewTop > child.getViewHeight() - 24
                && dependentViewTop < child.getViewHeight() + 24
                && top < touchSlop
                && top > -touchSlop) {
            CalendarUtil.setScrollToBottom(false);
            calendarViewAdapter.switchToMonth();
            offsetY = 0;
        }

        return true;
        // TODO: 16/12/8 dy为负时表示向上滑动，dy为正时表示向下滑动，dy为零时表示滑动停止
    }
}
