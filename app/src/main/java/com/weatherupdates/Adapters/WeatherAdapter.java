package com.weatherupdates.Adapters;

import android.app.Activity;
import android.graphics.ColorSpace;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherupdates.BaseActivity;
import com.weatherupdates.MainActivity;
import com.weatherupdates.Pojo.Weather;
import com.weatherupdates.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by VPotadar on 25/09/17..
 */

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Weather> mDataset;

    public static class GridViewObjectHolder extends RecyclerView.ViewHolder {
        TextView cityName, minMaxText;
        ImageView seasonTypeImage;

        public GridViewObjectHolder(View itemView) {
            super(itemView);

            cityName = (TextView) itemView.findViewById(R.id.city_name_text);
            seasonTypeImage = (ImageView) itemView.findViewById(R.id.season_type_img);
            minMaxText = (TextView) itemView.findViewById(R.id.min_max_temp_text);

        }

    }

    public static class DetailedObjectViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView date;
        TextView currentTemp, minimummaxTemp, seasonType, humidity;
        ImageView seasonTypeImage;

        public DetailedObjectViewHolder(View itemView) {
            super(itemView);

            cityName = (TextView) itemView.findViewById(R.id.city_name);
            date = (TextView) itemView.findViewById(R.id.date_text);
            currentTemp = (TextView) itemView.findViewById(R.id.temperature_text);
            minimummaxTemp = (TextView) itemView.findViewById(R.id.min_max_temp);
            seasonType = (TextView) itemView.findViewById(R.id.season_type_text);
            seasonTypeImage = (ImageView) itemView.findViewById(R.id.season_type_img_large);
            humidity = (TextView) itemView.findViewById(R.id.humidity_per);

        }

    }

    public WeatherAdapter(ArrayList<Weather> list) {
        mDataset = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Weather.MAIN_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_large_item, parent, false);
                return new DetailedObjectViewHolder(view);
            case Weather.SMALL_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_small_item, parent, false);
                return new GridViewObjectHolder(view);
        }
        return null;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Weather weather = mDataset.get(position);
        if (weather != null) {
            switch (weather.type) {
                case Weather.MAIN_VIEW:
                    ((DetailedObjectViewHolder) holder).cityName.setText(weather.getCityName().toUpperCase());

                    Typeface face = Typeface.createFromAsset(BaseActivity.mCurrentActivity.getAssets(),
                            "OpenSans-CondBold.ttf");
                    ((DetailedObjectViewHolder) holder).cityName.setTypeface(face);

                    Typeface faceLight = Typeface.createFromAsset(BaseActivity.mCurrentActivity.getAssets(),
                            "OpenSans-CondLight.ttf");

                    ((DetailedObjectViewHolder) holder).currentTemp.setText(weather.getCurrentTemp() + "\u00b0");
                    ((DetailedObjectViewHolder) holder).currentTemp.setTypeface(face);

                    ((DetailedObjectViewHolder) holder).date.setText("" + new SimpleDateFormat("EEE, MMM d").format(new Date()));
                    ((DetailedObjectViewHolder) holder).date.setTypeface(faceLight);

                    ((DetailedObjectViewHolder) holder).minimummaxTemp.setText(weather.getMinimumTemp() + "\u00b0" + " / " + weather.getMaxTemp() + "\u00b0");
                    ((DetailedObjectViewHolder) holder).humidity.setText(weather.getHumidity() + "%");

                    ((DetailedObjectViewHolder) holder).seasonType.setText(weather.getSeasonType());
                    ((DetailedObjectViewHolder) holder).seasonType.setTypeface(faceLight);

                    if (weather.getSeasonType().equalsIgnoreCase("Clouds")) {
                        ((DetailedObjectViewHolder) holder).seasonTypeImage.setImageResource(R.drawable.cloudone);
                    } else if (weather.getSeasonType().equalsIgnoreCase("Rain")) {
                        ((DetailedObjectViewHolder) holder).seasonTypeImage.setImageResource(R.drawable.rainy);
                    } else {
                        ((DetailedObjectViewHolder) holder).seasonTypeImage.setImageResource(R.drawable.cloudtwo);
                    }

                    break;
                case Weather.SMALL_VIEW:
                    ((GridViewObjectHolder) holder).cityName.setText(weather.getCityName());
                    ((GridViewObjectHolder) holder).minMaxText.setText(weather.getMinimumTemp() + "\u00b0" + " / " + weather.getMaxTemp() + "\u00b0");
                    if (weather.getSeasonType().equalsIgnoreCase("Clouds")) {
                        ((GridViewObjectHolder) holder).seasonTypeImage.setImageResource(R.drawable.cloudone);
                    } else if (weather.getSeasonType().equalsIgnoreCase("Rain")) {
                        ((GridViewObjectHolder) holder).seasonTypeImage.setImageResource(R.drawable.rainy);
                    } else {
                        ((GridViewObjectHolder) holder).seasonTypeImage.setImageResource(R.drawable.cloudtwo);
                    }
                    break;

            }

        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (mDataset.get(position).type) {
            case 0:
                return Weather.MAIN_VIEW;
            case 1:
                return Weather.SMALL_VIEW;
            default:
                return -1;
        }


    }

}
