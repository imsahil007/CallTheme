package com.sahil.calltheme;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        isPhonePermissionGranted();
        NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);




    }
    public  boolean isPhonePermissionGranted() {
        if(Build.VERSION.SDK_INT >= 26) { // Permission necessaire
            if(checkSelfPermission("android.permission.ANSWER_PHONE_CALLS") != PackageManager.PERMISSION_GRANTED) {
                String szPermissions[] = {"android.permission.ANSWER_PHONE_CALLS"};
                requestPermissions(szPermissions, 0);
            }
        }
        if(Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) { // Permission necessaire
            if(checkSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") != PackageManager.PERMISSION_GRANTED) {
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));

                startActivity(myIntent);
            }
            if(Build.VERSION.SDK_INT < 26) { // Permission pour Android 6.x et 7.x
                ContentResolver contentResolver = getContentResolver();
                String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
                String packageName = getPackageName();
                if (enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName)) {
                    Intent intent2 = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                    startActivity(intent2);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= 23) {

            if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED&&
                    checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS)==PackageManager.PERMISSION_GRANTED&&
                    checkSelfPermission(Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED&&
                    checkSelfPermission(Manifest.permission.ANSWER_PHONE_CALLS)==PackageManager.PERMISSION_GRANTED&&
                    checkSelfPermission(Manifest.permission.READ_CALL_LOG)==PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {


                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ANSWER_PHONE_CALLS,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_PHONE_NUMBERS
                    }, 1);

                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}

class MyPhoneStateListener extends PhoneStateListener {
    public static String incomingNumber="Unknown Contact";
    public void onCallStateChanged(int state,String incomingNumber){
        switch(state){
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("DEBUG", "IDLE");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG", "OFFHOOK");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                setincomingno(incomingNumber);
                Log.d("DEBUG", "RINGING");
                break;
        }

    }
    public static void setincomingno(String number){
         incomingNumber=number;

    }


}
