package com.weatherupdates;

import android.annotation.SuppressLint;
import android.util.Log;

import com.weatherupdates.Pojo.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by VPotadar on 25/09/17.
 */

public class BackendServices implements Serializable {


    private static BackendServices instance;
    public static String BASE_SERVICE;
    public static final String API_KEY = "&APPID=42922110dcbea2d0bfcf8c6a94c9c819";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static OkHttpClient client;
    public String[] cityIdList = {"1256214", "1277333", "1262321", "1270448", "1275701", "1253993"};
    public static Map<String, Weather> mGlobalWeatherDetails;
    public DBHelper dbHelper;

    public static BackendServices getInstance() {
        if (instance == null)
            instance = new BackendServices();
        return instance;
    }

    public BackendServices() {
        client = new OkHttpClient();
        BASE_SERVICE = "http://api.openweathermap.org/data/2.5/weather?id=";
        mGlobalWeatherDetails = new HashMap<String, Weather>();
        dbHelper = new DBHelper(BaseActivity.mCurrentActivity);

    }

    @SuppressLint("NewApi")
    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @SuppressLint("NewApi")
    public static String fetch(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    OnFeatchingReport mOnFeatchingReport = null;

    //Listener
    public void setOnFeatchingQuestions(OnFeatchingReport listener) {
        mOnFeatchingReport = listener;
    }

    public interface OnFeatchingReport {
        void onFeatchingSuccess(boolean success);
    }

    public void featchWeatherReportFromServer() {

        if (mOnFeatchingReport != null) {
            mOnFeatchingReport.onFeatchingSuccess(true);
            mOnFeatchingReport = null;
        }

        if (!Helper.isNetworkingOnAndShowToast(BaseActivity.mCurrentActivity))
            return;

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = false;
                try {
                    for (int i = 0; i < cityIdList.length - 1; i++) {

                        String response = fetch(BASE_SERVICE + cityIdList[i] + API_KEY);
                        Log.d("vallabh",BASE_SERVICE+cityIdList[i]);
                        JSONObject jsonArray = new JSONObject(response);

                        Weather weather = new Weather();
                        weather.setCityName(jsonArray.getString("name"));
                        weather.setCityID(jsonArray.getString("id"));

                        weather.setCurrentTemp("" + farehenHitToDegreeConversion(jsonArray.getJSONObject("main").getInt("temp")));
                        weather.setMinimumTemp("" + farehenHitToDegreeConversion(jsonArray.getJSONObject("main").getInt("temp_min")));
                        weather.setMaxTemp("" + farehenHitToDegreeConversion(jsonArray.getJSONObject("main").getInt("temp_max")));
                        weather.setHumidity("" + jsonArray.getJSONObject("main").getInt("humidity"));
                        weather.setSeasonType(jsonArray.getJSONArray("weather").getJSONObject(0).getString("main"));
                        if (i == 0) {
                            weather.setType(Weather.MAIN_VIEW);
                        } else {
                            weather.setType(Weather.SMALL_VIEW);
                        }


                        mGlobalWeatherDetails.put(weather.getCityID(), weather);

                    }
                    for (Weather weather : mGlobalWeatherDetails.values()) {

                        if (weather == null)
                            continue;

                        dbHelper.updateWeatherData(weather.getId(), weather);
                        Log.d("vallabh_from", dbHelper.getAllWeatherDetails().toString());
                    }
                    Helper.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (((MainActivity) BaseActivity.mCurrentActivity).adapter != null)
                                ((MainActivity) BaseActivity.mCurrentActivity).adapter.notifyDataSetChanged();

                        }
                    });
                    success = true;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mOnFeatchingReport != null) {
                    mOnFeatchingReport.onFeatchingSuccess(success);
                    mOnFeatchingReport = null;
                }

            }
        }).start();

    }

    public int farehenHitToDegreeConversion(int integer) {
        return (int) ((integer - 32) * (0.5556));
    }

}
