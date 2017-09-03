package com.example.allenchen.mvppractice.parks;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.allenchen.mvppractice.R;
import com.example.allenchen.mvppractice.data.ParksRemoteRepository;
import com.example.allenchen.mvppractice.util.ActivityUtils;

/**
 * Created by ALLENCHEN on 2017/9/3.
 */

public class ParksActivity extends AppCompatActivity {

    private ParksPresenter mPresenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_refresh:
                    mPresenter.loadParks();
                    return true;
                case R.id.navigation_close:
                    AlertDialog.Builder builder =new AlertDialog.Builder(ParksActivity.this)
                            .setMessage(R.string.alertdialog_close_message)
                            .setCancelable(true)
                            .setPositiveButton(R.string.alertdialog_finish, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ParksActivity.this.finish();
                                }
                            })
                            .setNegativeButton(R.string.alertdialog_close, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parks_main_activity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ParksFragment parksFragment = (ParksFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (parksFragment ==null){
            parksFragment =ParksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),parksFragment,R.id.content);
        }

        mPresenter = new ParksPresenter(ParksRemoteRepository.getInstance(), parksFragment);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
