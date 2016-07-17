package com.template;

import android.app.Application;

import timber.log.Timber;

public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        if (BuildConfig.REPORT_CRASHES) {
            final Crashlytics crashlytics = new Crashlytics();
            Fabric.with(getApplicationContext(), crashlytics);
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
