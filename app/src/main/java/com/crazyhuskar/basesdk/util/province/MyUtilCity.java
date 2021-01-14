package com.crazyhuskar.basesdk.util.province;

import android.content.Context;
import android.content.res.AssetManager;

import com.crazyhuskar.basesdk.util.MyUtilJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author CrazyHuskar
 * caeat at 2021/1/14  13:41
 */
public class MyUtilCity {
    public static void getCityInfo(Context context, OnCityInfoListener onCityInfoListener) {
        Observable.create((ObservableOnSubscribe<ProvinceBean>) emitter -> {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open("province.json")));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
                List<ProvinceEntity> provinceList = MyUtilJson.parseList(stringBuilder.toString(), ProvinceEntity.class);
                List<List<CityEntity>> cityList = new ArrayList<>();
                List<List<List<String>>> areaList = new ArrayList<>();
                for (ProvinceEntity provinceEntity : provinceList) {
                    cityList.add(provinceEntity.getCity());
                    List<List<String>> areaList_t = new ArrayList<>();
                    for (CityEntity cityEntity : provinceEntity.getCity()) {
                        areaList_t.add(cityEntity.getArea());
                    }
                    areaList.add(areaList_t);
                }
                ProvinceBean provinceBean = new ProvinceBean();
                provinceBean.setProvinceList(provinceList);
                provinceBean.setCityList(cityList);
                provinceBean.setAreaList(areaList);
                emitter.onNext(provinceBean);
                emitter.onComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProvinceBean>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;

                    }

                    @Override
                    public void onNext(ProvinceBean provinceBean) {
                        onCityInfoListener.getSuccess(provinceBean.getProvinceList(), provinceBean.getCityList(), provinceBean.getAreaList());
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();

                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();

                    }
                });
    }

    static class ProvinceBean {
        private List<ProvinceEntity> provinceList;
        private List<List<CityEntity>> cityList;
        private List<List<List<String>>> areaList;

        public List<ProvinceEntity> getProvinceList() {
            return provinceList;
        }

        public void setProvinceList(List<ProvinceEntity> provinceList) {
            this.provinceList = provinceList;
        }

        public List<List<CityEntity>> getCityList() {
            return cityList;
        }

        public void setCityList(List<List<CityEntity>> cityList) {
            this.cityList = cityList;
        }

        public List<List<List<String>>> getAreaList() {
            return areaList;
        }

        public void setAreaList(List<List<List<String>>> areaList) {
            this.areaList = areaList;
        }
    }
}
