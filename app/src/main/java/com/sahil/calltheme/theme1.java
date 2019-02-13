package com.sahil.calltheme;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.ncorti.slidetoact.SlideToActView;


public class theme1 extends IncomingCallScreen {
    private static int getSystemUiFlags() {
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility(getSystemUiFlags());

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(R.layout.theme1);



        MyPhoneStateListener phoneListener=new MyPhoneStateListener();
        TelephonyManager telephony = (TelephonyManager)
                getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        TextView name=findViewById(R.id.contact_name);
        name.setText(getContactDisplayNameByNumber(MyPhoneStateListener.incomingNumber,getBaseContext()));

        SlideToActView accept = (SlideToActView) findViewById(R.id.accept_call);
        final SlideToActView reject = (SlideToActView) findViewById(R.id.reject_call);
        accept.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete( SlideToActView slideToActView) {
                slideToActView.setVisibility(View.GONE);
                answerCall();                //call accepted
                reject.setText("End call");
            }
        });
        reject.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete( SlideToActView slideToActView) {

                rejectCall();
                finishAffinity();
            }
        });
    }

}
