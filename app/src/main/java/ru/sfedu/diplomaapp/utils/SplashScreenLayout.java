package ru.sfedu.diplomaapp.utils;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.sfedu.diplomaapp.MainActivity;
import ru.sfedu.diplomaapp.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenLayout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreenLayout.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundResource(R.drawable.agile2);
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
