package com.crazyhuskar.basesdk.util.province;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2021/1/14  13:40
 */
public class ProvinceEntity implements IPickerViewData {
    private String name;
    private List<CityEntity> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityEntity> getCity() {
        return city;
    }

    public void setCity(List<CityEntity> city) {
        this.city = city;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
