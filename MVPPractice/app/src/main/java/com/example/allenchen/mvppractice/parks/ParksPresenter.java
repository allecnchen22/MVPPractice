package com.example.allenchen.mvppractice.parks;

import android.support.annotation.NonNull;

import com.example.allenchen.mvppractice.data.Park;
import com.example.allenchen.mvppractice.data.ParksDataSource;
import com.example.allenchen.mvppractice.data.ParksRemoteRepository;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALLENCHEN on 2017/9/3.
 */

public class ParksPresenter implements ParksContract.Presenter{

    private final ParksRemoteRepository mParksRemoteRepository;

    private final ParksContract.View mParksView;

    public ParksPresenter(@NonNull ParksRemoteRepository parksRemoteRepository,@NonNull ParksContract.View parksView) {

        Logger.addLogAdapter(new AndroidLogAdapter());

        mParksRemoteRepository=parksRemoteRepository;
        mParksView =parksView;

        mParksView.setPresenter(this);

    }

    @Override
    public void start() {
        loadParks();
    }

    @Override
    public void loadParks() {

        mParksView.setLoadingIndicator(true);

        mParksRemoteRepository.getParks(new ParksDataSource.LoadParksCallback() {
            @Override
            public void onParksLoaded(List<Park> parks) {

                if (!mParksView.isActive()){
                    return;
                }

                mParksView.setLoadingIndicator(false);

                mParksView.showParks(parks);

            }

            @Override
            public void onDataNotAvailable() {
                if(!mParksView.isActive()){
                    return;
                }
                mParksView.setLoadingIndicator(false);
                mParksView.showLoadingError();
            }
        });

    }
}
