package com.crazyhuskar.basesdk.view.dialogfragment.listener;

import android.view.View;

import com.crazyhuskar.basesdk.view.dialogfragment.MyDialogFragment;
import com.crazyhuskar.basesdk.view.dialogfragment.base.BindViewHolder;


public interface OnViewClickListener {
    void onViewClick(BindViewHolder viewHolder, View view, MyDialogFragment myDialogFragment);
}
