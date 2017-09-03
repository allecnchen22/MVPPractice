package com.example.allenchen.mvppractice.data;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.orhanobut.logger.*;
import com.alibaba.fastjson.JSON;

/**
 * Created by ALLENCHEN on 2017/9/3.
 */

public class ParksRemoteRepository implements ParksDataSource {

    private static ParksRemoteRepository INSTANCE;

    private static final String DATASOURCEURL ="http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=bf073841-c734-49bf-a97f-3757a6013812";

    final OkHttpClient client = new OkHttpClient();

    public static ParksRemoteRepository getInstance() {

        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter());

        if (INSTANCE == null) {
            INSTANCE = new ParksRemoteRepository();
        }
        return INSTANCE;
    }

    private ParksRemoteRepository(){};

    @Override
    public void getParks(@NonNull final LoadParksCallback callback) {
        //get data
        Request request= new Request.Builder()
                .url(DATASOURCEURL)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                com.orhanobut.logger.Logger.e(e.getMessage());
                callback.onDataNotAvailable();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){

                    String jsonResult =response.body().string();
                    SourceData responseData = JSON.parseObject(jsonResult,SourceData.class);
                    Logger.d(responseData.getResult().getLimit());
                    callback.onParksLoaded(responseData.getResult().getResults());
                }
                else
                {
                    com.orhanobut.logger.Logger.e(response.message());
                    callback.onDataNotAvailable();
                }
            }
        });

    }

}
