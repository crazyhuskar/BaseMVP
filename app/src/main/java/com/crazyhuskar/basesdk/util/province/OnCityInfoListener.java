package com.crazyhuskar.basesdk.util.province;

import java.util.List;

public interface OnCityInfoListener {
    void getSuccess(List<ProvinceEntity> provinceList, List<List<CityEntity>> cityList, List<List<List<String>>> areaList);
}
