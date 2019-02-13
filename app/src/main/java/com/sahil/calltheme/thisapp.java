package com.sahil.calltheme;

import android.app.Application;
import android.content.SharedPreferences;

public class thisapp extends Application {
    SharedPreferences pref;

static  String theme;
    @Override
    public void onCreate() {
        super.onCreate();
        pref= getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
         theme = pref.getString("theme", "0");


    }
}
