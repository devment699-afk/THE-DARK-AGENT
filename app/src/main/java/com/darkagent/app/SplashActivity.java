package com.darkagent.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_splash);

        TextView logo = findViewById(R.id.splashLogo);
        TextView tg = findViewById(R.id.splashTg);

        AlphaAnimation in = new AlphaAnimation(0f, 1f);
        in.setDuration(1200);
        logo.startAnimation(in);

        AlphaAnimation in2 = new AlphaAnimation(0f, 1f);
        in2.setDuration(800);
        in2.setStartOffset(900);
        tg.startAnimation(in2);

        new Handler().postDelayed(() ->
                startActivity(new Intent(this, MainActivity.class)), 2200);
    }
}
