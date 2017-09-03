package com.example.allenchen.mvppractice.parks;

import com.example.allenchen.mvppractice.BasePresenter;
import com.example.allenchen.mvppractice.BaseView;
import com.example.allenchen.mvppractice.data.Park;

import java.util.List;

/**
 * Created by ALLENCHEN on 2017/9/3.
 */

public interface ParksContract {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean active);

        void showParks(List<Park> parks);

        void showNoParks();

        void showLoadingError();

        boolean isActive();

    }

    interface Presenter extends BasePresenter{

        void loadParks();
    }
}
