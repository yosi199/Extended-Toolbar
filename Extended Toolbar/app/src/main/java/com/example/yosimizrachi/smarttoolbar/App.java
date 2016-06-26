package com.example.yosimizrachi.smarttoolbar;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by yosimizrachi on 29/03/2016.
 */
public class App extends Application {

    public static Context sContext;

    public static Context getAppContext() {
        return sContext;
    }

    public static void toast(String text) {
        Toast.makeText(sContext, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
