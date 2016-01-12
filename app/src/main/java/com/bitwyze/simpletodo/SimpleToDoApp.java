package com.bitwyze.simpletodo;

import android.app.Application;
import android.content.Context;

/**
 * Created by scottrichards on 1/9/16.
 */
public class SimpleToDoApp extends Application {
    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
