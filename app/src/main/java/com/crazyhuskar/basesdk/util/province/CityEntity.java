package com.crazyhuskar.basesdk.util.province;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2021/1/14  13:40
 */
public class CityEntity implements IPickerViewData {
    private String name;
    private List<String> area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
