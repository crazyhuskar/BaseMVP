package com.crazyhuskar.basesdk.util;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class MyUtilFragment {
    private List<Fragment> menuList;
    private FragmentManager manager;
    private Fragment mCurrentFragment;
    private int viewID;

    public MyUtilFragment(FragmentManager manager, int viewID, List<Fragment> meunList) {
        this.manager = manager;
        this.viewID = viewID;
        this.menuList = meunList;
    }

    public void show(int i) {

        FragmentTransaction ft = manager.beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFragment) {
            ft.hide(mCurrentFragment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = manager.findFragmentByTag(menuList.get(i).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = menuList.get(i);
        }
        mCurrentFragment = fragment;
        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(viewID, fragment, fragment.getClass().getName());
            ft.show(fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }
}
