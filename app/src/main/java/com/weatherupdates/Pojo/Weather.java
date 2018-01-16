package com.weatherupdates.Pojo;

/**
 * Created by VPotadar on 25/09/17.
 * Pojo details of weather object
 */

public class Weather {


    private int id;
    public String cityID;
    private String cityName;
    private String todayDate;
    private String CurrentTemp;
    private String minimumTemp;
    private String maxTemp;
    private String humidity;
    private String seasonType;
    public static final int MAIN_VIEW = 0;
    public static final int SMALL_VIEW = 1;


    public int type = 0;

    public Weather(){
        this.id = 0;
        this.cityID = "";
        this.cityName = "";
        this.todayDate = "";
        CurrentTemp = "";
        this.minimumTemp = "";
        this.maxTemp = "";
        this.humidity = "";
        this.seasonType = "";
        this.type = SMALL_VIEW;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getCurrentTemp() {
        return CurrentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        CurrentTemp = currentTemp;
    }

    public String getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(String minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSeasonType() {
        return seasonType;
    }

    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", cityID='" + cityID + '\'' +
                ", cityName='" + cityName + '\'' +
                ", todayDate='" + todayDate + '\'' +
                ", CurrentTemp='" + CurrentTemp + '\'' +
                ", minimumTemp='" + minimumTemp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", seasonType='" + seasonType + '\'' +
                ", type=" + type +
                '}';
    }
}
