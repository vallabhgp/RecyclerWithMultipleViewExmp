package com.weatherupdates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.weatherupdates.Adapters.WeatherAdapter;
import com.weatherupdates.Pojo.Weather;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends BaseActivity {

    public DBHelper dbHelper;
    public WeatherAdapter adapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(BaseActivity.mCurrentActivity);
        startService(new Intent(this, MyService.class));
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        BackendServices.getInstance().featchWeatherReportFromServer();

        BackendServices.getInstance().setOnFeatchingQuestions(new BackendServices.OnFeatchingReport() {
            @Override
            public void onFeatchingSuccess(boolean success) {
                if (success) {

                    Helper.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {

                            ArrayList<Weather> weatherList = dbHelper.getAllWeatherDetails();
                            Collections.reverse(weatherList);
                            adapter = new WeatherAdapter(weatherList);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                                @Override
                                public int getSpanSize(int position) {
                                    switch (position) {
                                        case 0:
                                            return 2;
                                        default:
                                            return 1;
                                    }
                                }
                            });

                            mRecyclerView.setLayoutManager(gridLayoutManager);
                            mRecyclerView.setAdapter(adapter);

                        }
                    });

                }
            }
        });
    }
}
