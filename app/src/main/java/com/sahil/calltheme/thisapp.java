package com.sahil.calltheme;

import android.app.Application;
import android.content.SharedPreferences;

public class thisapp extends Application {
    SharedPreferences pref;

  public static String theme="0";

    @Override
    public void onCreate() {
        super.onCreate();
        pref= getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

         theme = pref.getString("theme", "0");

    }

}
