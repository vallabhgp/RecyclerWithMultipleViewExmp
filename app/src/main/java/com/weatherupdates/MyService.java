package com.weatherupdates;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.weatherupdates.Pojo.Weather;

/**
 * Created by VPotadar on 26/09/17.
 */

public class MyService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    DBHelper dbHelper = new DBHelper(BaseActivity.mCurrentActivity);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                BackendServices.getInstance().featchWeatherReportFromServer();

                BackendServices.getInstance().setOnFeatchingQuestions(new BackendServices.OnFeatchingReport() {
                    @Override
                    public void onFeatchingSuccess(boolean success) {
                        Log.d("vallabh", BackendServices.getInstance().mGlobalWeatherDetails.toString());
                    }
                });
                handler.postDelayed(runnable, 30000);
            }
        };

        handler.postDelayed(runnable, 40000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onStart(Intent intent, int startid) {
    }
}
