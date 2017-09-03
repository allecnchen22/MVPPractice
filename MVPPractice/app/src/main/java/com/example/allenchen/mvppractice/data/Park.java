package com.example.allenchen.mvppractice.data;

import android.support.annotation.NonNull;

/**
 * Created by ALLENCHEN on 2017/9/2.
 */

public final class Park {

    private int _id;

    private String ParkName;

    private String Name;

    private String YearBuilt;

    private String OpenTime;

    private String Image;

    private String Introduction;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getParkName() {
        return ParkName;
    }

    public void setParkName(String parkName) {
        ParkName = parkName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getYearBuilt() {
        return YearBuilt;
    }

    public void setYearBuilt(String yearBuilt) {
        YearBuilt = yearBuilt;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }
}
