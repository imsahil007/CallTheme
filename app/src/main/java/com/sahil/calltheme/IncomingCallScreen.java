package com.sahil.calltheme;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.ncorti.slidetoact.SlideToActView;

import java.lang.reflect.Method;
import java.util.List;

public class IncomingCallScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.theme1);
        super.onCreate(savedInstanceState);

    }

    public void rejectCall() {
        TelecomManager tm = (TelecomManager) getApplicationContext().getSystemService(Context.TELECOM_SERVICE);

        if (tm != null) {
            tm.endCall();
            // success == true if call was terminated.
        }
    }
    public String getContactDisplayNameByNumber(String number,Context context) {
        String name;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        name = "Incoming call from";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, null, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                // this.id =
                // contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                // String contactId =
                // contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
            }else{
                name = "Unknown number";
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name+"\n"+number;
    }
    public void answerCall() {
        if(Build.VERSION.SDK_INT >= 26) { // Pris en charge Android >= 8.0
            if(checkSelfPermission("android.permission.ANSWER_PHONE_CALLS") == PackageManager.PERMISSION_GRANTED) {
                TelecomManager tm = (TelecomManager) this.getSystemService(Context.TELECOM_SERVICE);
                if(tm != null)
                    tm.acceptRingingCall();
            }
        }
        if(Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT < 26) { // Hangup in Android 6.x and 7.x
            MediaSessionManager mediaSessionManager =  (MediaSessionManager) getApplicationContext().getSystemService(Context.MEDIA_SESSION_SERVICE);
            if(mediaSessionManager != null) {
                try {
                    List<MediaController> mediaControllerList = mediaSessionManager.getActiveSessions
                            (new ComponentName(getApplicationContext(), NotificationReceiverService.class));

                    for (android.media.session.MediaController m : mediaControllerList) {
                        if ("com.android.server.telecom".equals(m.getPackageName())) {
                            m.dispatchMediaButtonEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
                            m.dispatchMediaButtonEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
                            break;
                        }
                    }
                } catch (Exception e) { /* Do Nothing */ }
            }
        }
        if(Build.VERSION.SDK_INT < 23) { // Prend en charge jusqu'à Android 5.1
            try {
                if(Build.MANUFACTURER.equalsIgnoreCase("HTC")) { // Uniquement pour HTC
                    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    if(audioManager!=null && !audioManager.isWiredHeadsetOn()) {
                        Intent i = new Intent(Intent.ACTION_HEADSET_PLUG);
                        i.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
                        i.putExtra("state", 0);
                        i.putExtra("name", "Orasi");
                        try {
                            sendOrderedBroadcast(i, null);
                        } catch (Exception e) { /* Do Nothing */ }
                    }
                }
                Runtime.getRuntime().exec("input keyevent " +
                        Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
            } catch (Exception e) {
                // Runtime.exec(String) had an I/O problem, try to fall back
                String enforcedPerm = "android.permission.CALL_PRIVILEGED";
                Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                        Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_HEADSETHOOK));
                Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                        Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                                KeyEvent.KEYCODE_HEADSETHOOK));

                this.sendOrderedBroadcast(btnDown, enforcedPerm);
                this.sendOrderedBroadcast(btnUp, enforcedPerm);
            }
        }


    }

}