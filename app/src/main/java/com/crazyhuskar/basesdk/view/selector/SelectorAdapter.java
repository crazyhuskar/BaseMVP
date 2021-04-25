package com.crazyhuskar.basesdk.view.selector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazyhuskar.basesdk.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yx on 2018/7/1.
 * 城市列表Adapter
 */
public class SelectorAdapter extends BaseExpandableListAdapter {
    // 字符串
    private List<String> strList;
    private List<TxlDataBean> dataList;
    //字符串首字母 的集合
    private AssortPinyinList assort;

    private LayoutInflater inflater;
    // 中文排序
    private LanguageComparator_CN cnSort = new LanguageComparator_CN();

    public SelectorAdapter(Context context, List<TxlDataBean> mLists) {
        super();
        this.inflater = LayoutInflater.from(context);
        this.dataList = mLists;

        if (strList == null) {
            strList = new ArrayList<String>();
        }
        for (int i = 0; i < mLists.size(); i++) {
            String str = mLists.get(i).getName() + ",_," + i;//Name+索引下标
            strList.add(str);
        }

        assort = new AssortPinyinList();

        // 字符串首字母转拼音,并排序
        sort();
    }

    /**
     * 更新数据
     *
     * @param mLists
     */
    public void updateStrList(ExpandableListView city_elist, List<TxlDataBean> mLists) {
        this.dataList = mLists;
        if (strList == null) {
            strList = new ArrayList<String>();
        } else {
            strList.clear();
        }
        for (int i = 0; i < mLists.size(); i++) {
            String str = mLists.get(i).getName() + ",_," + i;//Name+索引下标
            strList.add(str);
        }
        assort = new AssortPinyinList();
        // 字符串首字母转拼音,并排序
        sort();
        //双层listview更新方法,,由于Group 的伸缩会引起getChildView(int, int, boolean, View, ViewGroup)  的运行!所以更新使用：
        notifyDataSetChanged();//更新数据
        city_elist.collapseGroup(0);
        city_elist.expandGroup(0);

        // 展开所有
        for (int i = 0, length = getGroupCount(); i < length; i++) {
            city_elist.expandGroup(i);
        }
    }

    /**
     * 更新数据2
     *
     * @param mLists
     */
    public void updateData(List<TxlDataBean> mLists) {
        this.dataList = mLists;
        notifyDataSetChanged();
    }

    /**
     * 字符串首字母转拼音,并排序
     */
    private void sort() {
        for (String str : strList) {
            assort.getHashList().add(str);
        }
        assort.getHashList().sortKeyComparator(cnSort);
        //for (int i = 0; i < assort.getHashList().size(); i++) {
        //    Log.i("aaaa", "---------" + assort.getHashList().getKeyIndex(i));
        //}
        for (int i = 0, length = assort.getHashList().size(); i < length; i++) {
            Collections.sort((assort.getHashList().getValueListIndex(i)),
                    cnSort);
        }

    }

    //首字母集合
    public AssortPinyinList getAssort() {
        return assort;
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return assort.getHashList().size();
    }

    @Override
    public int getChildrenCount(int group) {
        // TODO Auto-generated method stub
        return assort.getHashList().getValueListIndex(group).size();
    }

    @Override
    public Object getGroup(int group) {
        // TODO Auto-generated method stub
        return assort.getHashList().getValueListIndex(group);
    }

    @Override
    public Object getChild(int group, int child) {
        return assort.getHashList().getValueIndex(group, child);
    }

    @Override
    public long getGroupId(int group) {
        // TODO Auto-generated method stub
        return group;
    }

    @Override
    public long getChildId(int group, int child) {
        // TODO Auto-generated method stub
        return child;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public View getGroupView(int group, boolean b, View view, ViewGroup viewGroup) {
        //层级 一：拼音
        if (view == null) {
            view = inflater.inflate(R.layout.pinyin_group_items, null);
            view.setClickable(true);
        }
        TextView textView = (TextView) view
                .findViewById(R.id.group_name);
        textView.setText(assort.getFirstChar(assort.getHashList()
                .getValueIndex(group, 0)));
        // 用于获取分组的名
        // contentView.setTag(textView);
        return view;
    }

    @Override
    public View getChildView(int group, int child, boolean arg2,
                             View view, ViewGroup viewGroup) {
        //层级  二：内容
        // TODO Auto-generated method stub
        if (view == null) {
            view = inflater.inflate(R.layout.pinyin_child_items, null);
        }
        RelativeLayout child_rl = view.findViewById(R.id.child_rl);
        TextView tvname = (TextView) view.findViewById(R.id.child_name);

        // 取到assort中的内容进行设置
        String mData = assort.getHashList().getValueIndex(group, child);//获取到字符Name+dataList对应的索引下标;
        String[] strs = mData.split(",_,");
        String name = strs.length > 0 ? strs[0] : "";
        String mIndex = strs.length > 1 ? strs[1] : "-1";//该child内容对应的dataList列表索引
        tvname.setText(name);
        int index = Integer.parseInt(mIndex);
        if (index > -1) {
            //GlideUtils.setImageCircle(dataList.get(index).getAvatar(), child_img);

            child_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClick.onClicks(index, dataList.get(index));
                }
            });

        }


        return view;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

    private OnAdapterClick mClick;

    public interface OnAdapterClick {
        void onClicks(int position, TxlDataBean txlDataBean);
    }

    public void setOnAdapterClick(OnAdapterClick click) {
        this.mClick = click;
    }
}
