package com.sahil.calltheme;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle extras = intent.getExtras();

final int theme=Integer.getInteger(thisapp.theme.trim());
    if (extras != null) {
            final String state = extras.getString(TelephonyManager.EXTRA_STATE);


            Handler callActionHandler = new Handler();
            Runnable runRingingActivity = new Runnable()
            {
                @Override
                public void run()
                {
                    Intent intentPhoneCall ;

                    switch (theme){
                        case 0:intentPhoneCall = new Intent(context, theme1.class);
                            break;

                        case 1:intentPhoneCall = new Intent(context, theme2.class);
                            break;
                            default:intentPhoneCall = new Intent(context, theme1.class);intentPhoneCall = new Intent(context, theme1.class);
                    }
//                        intentPhoneCall.putExtra("incomingnumber", ("tel:"+incomingNumber));
                        intentPhoneCall.putExtra("state", state);

                        intentPhoneCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentPhoneCall.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        context.startActivity(intentPhoneCall);
                }

            };

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                callActionHandler.postDelayed(runRingingActivity, 0);

            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                callActionHandler.removeCallbacks(runRingingActivity);
                new IncomingCallScreen().rejectCall();

                // setResultCode(Activity.RESULT_CANCELED);
            }

        }
    }
}
