package com.crazyhuskar.basesdk.view.selector;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.basesdk.R;
import com.crazyhuskar.basesdk.util.MyUtilJson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectorActivity extends AppCompatActivity {
    private ImageView break_iv;
    private TextView title;
    private EditText city_etext;
    private ExpandableListView city_elist;
    private LetterView city_eassort;

    //数据
    private List<TxlDataBean> roo = new ArrayList<>();//0或特殊字符开头均归结为#号列表
    private SelectorAdapter selectorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        break_iv = (ImageView) findViewById(R.id.break_iv);
        break_iv.setOnClickListener(view -> closedSelector(new TxlDataBean()));
        title = (TextView) findViewById(R.id.title_tv);
        city_etext = (EditText) findViewById(R.id.city_etext);
        city_etext.addTextChangedListener(new EditChangedListener());//监听输入状态
        city_elist = (ExpandableListView) findViewById(R.id.city_elist);
        city_eassort = (LetterView) findViewById(R.id.city_eassort);
        Intent ins = getIntent();
        if (ins != null && ins.getSerializableExtra("yx_roo") != null && ins.getSerializableExtra("yx_roo") instanceof List) {
            roo = (List<TxlDataBean>) ins.getSerializableExtra("yx_roo");//传过String[]也行，传来List<String>也行
        } else {
            //默认数据
            TxlDataBean bean = new TxlDataBean();
            bean.setId("1");
            bean.setName("北京");
            roo.add(bean);
            bean = new TxlDataBean();
            bean.setId("2");
            bean.setName("上海");
            roo.add(bean);
            bean = new TxlDataBean();
            bean.setId("3");
            bean.setName("广州");
            roo.add(bean);
            bean = new TxlDataBean();
            bean.setId("4");
            bean.setName("深圳");
            roo.add(bean);
        }
        if (ins != null && ins.getStringExtra("yx_title") != null) {
            title.setText(ins.getStringExtra("yx_title"));
        }
        //ins.putExtra("roo", (Serializable) roo);
        selectorAdapter = new SelectorAdapter(this, roo);
        city_elist.setAdapter(selectorAdapter);
        // 展开所有
        for (int i = 0, length = selectorAdapter.getGroupCount(); i < length; i++) {
            city_elist.expandGroup(i);
        }
        //屏幕中间显示字母，并且选中字母对应的列表项
        LetterView.setLetterDialog(this, city_eassort, city_elist, selectorAdapter);

        //city_elist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
        //    @Override
        //    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
        //        String rooValue = selectorAdapter.getChild(groupPosition, childPosition).toString();//获取到字符拼接(Name+roo列表索引下标，张三,_,49);
        //        if (isDebug) {
        //            Log.i("aaaa", "---点击了----" + rooValue);
        //            Log.i("aaaa", "==1层下标id是:" + groupPosition);//当前点击项的字母层下标
        //            Log.i("aaaa", "==2层下标id是:" + childPosition);//当前点击的字母层对应的儿子层下标
        //        }
        //        return true;
        //    }
        //});

        selectorAdapter.setOnAdapterClick((position, txlDataBean) -> closedSelector(txlDataBean));

    }

    /**
     * 关闭当前页面
     *
     * @param rooValue
     */
    private void closedSelector(TxlDataBean rooValue) {
        Intent inc = new Intent();
        inc.putExtra("selectValue", MyUtilJson.toJSONString(rooValue));
        setResult(6, inc);
        finish();
    }

    @Override
    public void onBackPressed() {//手机物理返回键
        closedSelector(new TxlDataBean());
        //super.onBackPressed();
    }

    //输入框监听
    private class EditChangedListener implements TextWatcher {
        private CharSequence temp = "";//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 140;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {//s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {//S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
            //cr_tvnum.setText("还能输入" + (charMaxNum - s.length()) + "字符");
            //cr_tvnum.setText(s.length() + "/140");
            String text = s.toString();//当前输入内容
            if (text == null || "".equals(text)) {
                selectorAdapter.updateStrList(city_elist, roo);
            } else {
//                Log.i("aaaa", "========" + selectorAdapter.getAssort().getHashList().getKey(text));//通过text获得首字母
//                int index = selectorAdapter.getAssort().getHashList().indexOfKey(s);//通过首字母获取到index
                List<TxlDataBean> strList = search(text);//模糊查询到的列表
                Log.i("aaaa", "=================" + strList.size());
                if (strList.size() > 0) {
                    selectorAdapter.updateStrList(city_elist, strList);
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {//s:变化后的所有字符
            try {

                if (temp.length() > charMaxNum) {
                    //ToastUtils.showToastApplication("你输入的字数已经超过140限制！");
                }
            } catch (Exception ex) {

            }

        }

    }

    /**
     * 模糊查询
     *
     * @param name
     * @return
     */
    public List search(String name) {
        List results = new ArrayList();
        Pattern pattern = Pattern.compile(name);//输入的内容
        for (int i = 0; i < roo.size(); i++) {
            Matcher matcher = pattern.matcher(roo.get(i).getName());//集合里：字符串或者实体类中的某个字段
            if (matcher.find()) { //matcher.find() 模糊查询，matcher.matches()精确匹配
                results.add(roo.get(i));
            }
        }
        return results;
    }
}
