package com.weatherupdates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.weatherupdates.Pojo.Weather;

import java.util.ArrayList;

/**
 * Created by VPotadar on 25/09/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Weather";
    private static final String TABLE_WEATHER = "tableweather";
    private SQLiteDatabase dbase;

    private static final String KEY_ID = "id";
    private static final String KEY_CITY_ID = "cityid";
    private static final String KEY_CITY_NAME = "cityname";
    private static final String KEY_CURRENT_TEMP = "currenttemp";
    private static final String KEY_MINIMUM_TEMP = "maxtemp";
    private static final String KEY_MAX_TEMP = "minimumtemp";
    private static final String KEY_HUMIDTY = "humidity";
    private static final String KEY_SEASON_TYPE = "seasontype";
    private static final String KEY_TYPE = "keytype";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        dbase = sqLiteDatabase;
        String sqlTableWeather = "CREATE TABLE IF NOT EXISTS " + TABLE_WEATHER + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CITY_ID
                + " TEXT, " + KEY_CITY_NAME + " TEXT, " + KEY_CURRENT_TEMP + " TEXT, "
                + KEY_MINIMUM_TEMP + " TEXT, " + KEY_MAX_TEMP + " TEXT, " + KEY_HUMIDTY  +
                " TEXT, " + KEY_SEASON_TYPE  + " TEXT, " + KEY_TYPE  + " INTEGER)";

        sqLiteDatabase.execSQL(sqlTableWeather);
        populateWeatherData();
    }


    public void addWeatherDetails(Weather weather) {

        ContentValues values = new ContentValues();
        values.put(KEY_CITY_ID, weather.getCityID());
        values.put(KEY_CITY_NAME, weather.getCityName());
        values.put(KEY_CURRENT_TEMP, weather.getCurrentTemp());
        values.put(KEY_MINIMUM_TEMP, weather.getMinimumTemp());
        values.put(KEY_MAX_TEMP, weather.getMaxTemp());
        values.put(KEY_HUMIDTY, weather.getHumidity());
        values.put(KEY_SEASON_TYPE, weather.getSeasonType());
        values.put(KEY_TYPE,weather.getType());
        // Inserting Row
        dbase.insert(TABLE_WEATHER, null, values);
    }

    public void populateWeatherData() {

        if(BackendServices.getInstance().mGlobalWeatherDetails == null)
            return;

        for (Weather weather: BackendServices.getInstance().mGlobalWeatherDetails.values()) {
            addWeatherDetails(weather);
        }
    }


    public boolean updateWeatherData (Integer id ,Weather weather){


        if(weather == null)
            return false;

        if(dbase == null)
            return false;

        ContentValues values = new ContentValues();
        values.put(KEY_CITY_ID, weather.getCityID());
        values.put(KEY_CITY_NAME, weather.getCityName());
        values.put(KEY_CURRENT_TEMP, weather.getCurrentTemp());
        values.put(KEY_MINIMUM_TEMP, weather.getMinimumTemp());
        values.put(KEY_MAX_TEMP, weather.getMaxTemp());
        values.put(KEY_HUMIDTY, weather.getHumidity());
        values.put(KEY_SEASON_TYPE, weather.getSeasonType());
        values.put(KEY_TYPE,weather.getType());
        dbase.update(TABLE_WEATHER, values, "id = ? ", new String[]{Integer.toString(id)});

        return true;
    }


    public ArrayList<Weather> getAllWeatherDetails() {
        ArrayList<Weather> weatherDetails = new ArrayList<Weather>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);

        if (cursor == null)
            return null;

        if (cursor.moveToFirst()) {
            do {

                Weather details = new Weather();
                details.setId(cursor.getInt(0));
                details.setCityID(cursor.getString(1).trim());
                details.setCityName(cursor.getString(2).trim());
                details.setCurrentTemp(cursor.getString(3).trim());
                details.setMinimumTemp(cursor.getString(4).trim());
                details.setMaxTemp(cursor.getString(5).trim());
                details.setHumidity(cursor.getString(6).trim());
                details.setSeasonType(cursor.getString(7).trim());
                details.setType(cursor.getInt(8));
                weatherDetails.add(details);

            } while (cursor.moveToNext());
        }
        // return notepad  list
        return weatherDetails;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
