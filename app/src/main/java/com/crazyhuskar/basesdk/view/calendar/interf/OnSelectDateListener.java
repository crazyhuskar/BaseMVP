package com.crazyhuskar.basesdk.view.calendar.interf;


import com.crazyhuskar.basesdk.view.calendar.model.CalendarDate;

/**
 * Created by ldf on 17/6/2.
 */

public interface OnSelectDateListener {
    void onSelectDate(CalendarDate date);

    void onSelectOtherMonth(int offset);//点击其它月份日期
}
