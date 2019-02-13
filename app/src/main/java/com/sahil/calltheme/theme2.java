package com.sahil.calltheme;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import io.saeid.fabloading.LoadingView;

public class theme2 extends IncomingCallScreen {
    private static int getSystemUiFlags() {
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }

    private LoadingView mLoadingView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility(getSystemUiFlags());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(R.layout.activity_main);

        mLoadingView = (LoadingView) findViewById(R.id.accept_call);
        boolean isLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        int marvel_1 = R.drawable.accept;
        int marvel_0 = isLollipop ? R.drawable.marvel_1_lollipop : R.drawable.marvel_1;
        int marvel_2 = isLollipop ? R.drawable.marvel_2_lollipop : R.drawable.marvel_2;
        int marvel_3 = isLollipop ? R.drawable.marvel_3_lollipop : R.drawable.marvel_3;
        int marvel_4 = isLollipop ? R.drawable.marvel_4_lollipop : R.drawable.marvel_4;
        mLoadingView.addAnimation(Color.parseColor("#4CAF50"), marvel_1,
                LoadingView.FROM_BOTTOM);
        mLoadingView.addAnimation(Color.parseColor("#FFD200"), marvel_0,
                LoadingView.FROM_LEFT);
        mLoadingView.addAnimation(Color.parseColor("#2F5DA9"), marvel_2,
                LoadingView.FROM_TOP);
        mLoadingView.addAnimation(Color.parseColor("#FF4218"), marvel_3,
                LoadingView.FROM_RIGHT);
        mLoadingView.addAnimation(Color.parseColor("#C7E7FB"), marvel_4,
                LoadingView.FROM_BOTTOM);
        mLoadingView.addListener(new LoadingView.LoadingListener() {
            @Override
            public void onAnimationStart(int currentItemPosition) {

            }

            @Override
            public void onAnimationRepeat(int nextItemPosition) {

            }

            @Override
            public void onAnimationEnd(int nextItemPosition) {

            }
        });
        FloatingActionButton reject=findViewById(R.id.reject_call);
        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingView.setVisibility(View.GONE);
                answerCall();                //call accepted
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectCall();
                finishAffinity();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void pause(View v) {
        mLoadingView.pauseAnimation();

    }

    public void start(View v) {
        mLoadingView.startAnimation();

    }

    public void resume(View v) {
        mLoadingView.resumeAnimation();

    }
}