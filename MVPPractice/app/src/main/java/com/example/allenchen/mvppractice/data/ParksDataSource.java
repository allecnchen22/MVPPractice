package com.example.allenchen.mvppractice.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by ALLENCHEN on 2017/9/3.
 */

public interface ParksDataSource {

    interface LoadParksCallback{

        void onParksLoaded(List<Park> parks);
        void onDataNotAvailable();
    }

    void getParks(@NonNull LoadParksCallback callback);

}
