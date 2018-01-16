package com.weatherupdates;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by VPotadar on 26/09/17.
 */

public class Helper {

    public static Toast toast = null;


    //to check is device connected to WiFi
    static public boolean isWifiConnected(Activity mActivity) {
        ConnectivityManager connMgr = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        return isWifiConn;
    }

    //to check is device connected to mobile data
    static public boolean isMobileConnected(Activity mActivity) {
        ConnectivityManager connMgr = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        return isMobileConn;
    }

    static public boolean isNetworkingOn(Activity mActivity) {
        return isWifiConnected(mActivity) || isMobileConnected(mActivity);
    }

    static public boolean isNetworkingOnAndShowToast(final Activity mActivity) {
        if (!isNetworkingOn(mActivity)) {
            runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    displayToast(mActivity, "Internet Disconnected!");
                }
            });
            return false;
        }
        return true;
    }

    //Run code in UI thread
    static public void runOnUIThread(Runnable runnable) {
        new android.os.Handler(Looper.getMainLooper()).post(runnable);
    }

    public static void displayToast(Activity mCurrentActivity, String message) {

        if (toast != null) {
            toast.cancel();
            toast = Toast.makeText(mCurrentActivity, message, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(mCurrentActivity, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
