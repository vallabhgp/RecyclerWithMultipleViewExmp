package com.weatherupdates;

/**
 * Created by VPotadar on 25/09/17..
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {
    static public BaseActivity mPreviousAcitvity = null;
    static public BaseActivity mCurrentActivity = null;
    static public Timer mProgressDialogTimer;

    static private Timer mActivityTransitionTimer;
    static private TimerTask mActivityTransitionTimerTask;

    static private boolean mShowUserPresence = false;
    static private Timer mUserPresenceTimer = null;

    public boolean wasInBackground;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPreviousAcitvity = this;
        super.onCreate(savedInstanceState);
        mCurrentActivity = this;
        String packageName = this.getPackageName();
    }


    @Override
    protected void onResume() {

        mCurrentActivity = this;

        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (wasInBackground) {
            onBackFromBackground();
        }
        stopActivityTransitionTimer();
    }

    protected void onBackFromBackground() {
    }

    protected void onGoIntoBackground() {

    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        startActivityTransitionTimer();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void startActivityTransitionTimer() {
        mActivityTransitionTimer = new Timer();
        mActivityTransitionTimerTask = new TimerTask() {
            public void run() {

                wasInBackground = true;
                onGoIntoBackground();

                if (mUserPresenceTimer != null)
                    mUserPresenceTimer.cancel();
                mUserPresenceTimer = new Timer();
                mUserPresenceTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mShowUserPresence = true;
                    }
                }, 1000);


            }
        };

        mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                MAX_ACTIVITY_TRANSITION_TIME_MS);


    }

    public void stopActivityTransitionTimer() {
        if (mActivityTransitionTimerTask != null) {
            mActivityTransitionTimerTask.cancel();
        }

        if (mActivityTransitionTimer != null) {
            mActivityTransitionTimer.cancel();
        }

        this.wasInBackground = false;
    }

    public boolean isInBackground() {
        return wasInBackground;
    }


}

