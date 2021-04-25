package com.crazyhuskar.basesdk.view.selector;

import java.io.Serializable;

/**
 * Created : Yx
 * Time : 2019/8/21 14:59
 * Describe :
 */
public class TxlDataBean implements Serializable {
    private String id;
    private String avatar;
    private String name;
    private String xz_classmanagement_id;//班级管理ID
    private boolean onSelect = false;//是否选中

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXz_classmanagement_id() {
        return xz_classmanagement_id;
    }

    public void setXz_classmanagement_id(String xz_classmanagement_id) {
        this.xz_classmanagement_id = xz_classmanagement_id;
    }

    public boolean getOnSelect() {
        return onSelect;
    }

    public void setOnSelect(boolean onSelect) {
        this.onSelect = onSelect;
    }
}
