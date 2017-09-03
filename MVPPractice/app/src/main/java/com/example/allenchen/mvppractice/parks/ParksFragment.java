package com.example.allenchen.mvppractice.parks;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.allenchen.mvppractice.R;
import com.example.allenchen.mvppractice.data.Park;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ALLENCHEN on 2017/9/3.
 */

public class ParksFragment extends Fragment implements ParksContract.View{

    private ParksContract.Presenter mPresenter;

    private ParksAdapter mListAdapter;

    public ParksFragment() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static ParksFragment newInstance (){
        return new ParksFragment();
    }

    @Override
    public void setPresenter(ParksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter=new ParksAdapter(new ArrayList<Park>(0));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLoadingIndicator(true);

        mPresenter.loadParks();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.parks_list_fragment,container,false);
        ListView listView = (ListView)rootView.findViewById(R.id.list_parks);
        listView.setAdapter(mListAdapter);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(listView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadParks();
            }
        });

        setHasOptionsMenu(false);

        return rootView;
    }

    @Override
    public void showParks(final List<Park> parks) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListAdapter.refreshData(parks);
            }
        });

    }


    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() ==null){
            return;
        }

        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });

    }


    @Override
    public void showNoParks()
    {
        setLoadingIndicator(false);
        Snackbar.make(getView(),R.string.snack_empty_data,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingError()
    {
        Snackbar.make(getView(),R.string.snack_loading_error,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private static class ParksAdapter extends BaseAdapter{

        private List<Park> mParks;

        public ParksAdapter(List<Park> parks) {
            mParks=checkNotNull(parks);
        }

        public void refreshData(List<Park> parks){
            mParks=checkNotNull(parks);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mParks.size();
        }

        @Override
        public Park getItem(int position) {
            return mParks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {

            View rowView = convertView;

            if (rowView==null){
                LayoutInflater inflater=LayoutInflater.from(parent.getContext());
                rowView=inflater.inflate(R.layout.parks_item_view,parent,false);
            }

            final Park mPark=mParks.get(position);

            TextView tvParkName = (TextView) rowView.findViewById(R.id.tv_parks_item_parkname);
            TextView tvName = (TextView) rowView.findViewById(R.id.tv_parks_item_name);
            TextView tvYearBuild = (TextView) rowView.findViewById(R.id.tv_parks_item_yearbuild);
            TextView tvOpenTime = (TextView) rowView.findViewById(R.id.tv_parks_item_opentime);
            ImageView imgParkImg=(ImageView) rowView.findViewById(R.id.img_parks_item);

            tvParkName.setText(mPark.getParkName());
            tvName.setText(mPark.getName());
            tvYearBuild.setText( parent.getContext().getText(R.string.item_yearbuilt) +mPark.getYearBuilt());
            tvOpenTime.setText(parent.getContext().getText(R.string.item_opentime) +mPark.getOpenTime());

            Glide.with(parent.getContext()).load(mPark.getImage()).into(imgParkImg);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog= new AlertDialog.Builder(parent.getContext())
                            .setCancelable(false)
                            .setMessage(mPark.getIntroduction())
                            .setTitle(mPark.getName())
                            .setNegativeButton(R.string.alertdialog_close, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    dialog.show();
                }
            });

            return rowView;
        }
    }


}
