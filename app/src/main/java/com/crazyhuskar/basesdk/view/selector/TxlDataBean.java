package com.crazyhuskar.basesdk.view.selector;

import java.io.Serializable;

/**
 * Created : Yx
 * Time : 2019/8/21 14:59
 * Describe :
 */
public class TxlDataBean implements Serializable {
    private String id;
    private String name;
    private String data;
    private boolean onSelect = false;//是否选中

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean getOnSelect() {
        return onSelect;
    }

    public void setOnSelect(boolean onSelect) {
        this.onSelect = onSelect;
    }
}
