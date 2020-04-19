package com.example.masterbloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    TextView text1,text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        imageView=findViewById(R.id.image);
        text1=findViewById(R.id.top_text);
        text2=findViewById(R.id.bottom_text);

        Animation bottom_animation=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        Animation top_animation= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        imageView.setAnimation(top_animation);
        text1.setAnimation(bottom_animation);
        text2.setAnimation(bottom_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();

//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
//                {
//                    ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,imageView,"logo_image");
//                    startActivity(intent,activityOptions.toBundle());
//                }
//                else
//                {
//                    startActivity(intent);
//                }
//                Pair[] pairs=new Pair[1];
//                pairs[0]=new Pair<View,String>(imageView,"logo_image");
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);
//                    startActivity(intent,activityOptions.toBundle());
//                    finish();
//                }
//                else
//                {
//                    startActivity(intent);
//                    finish();
//
//                }

            }
        },5000);
    }
}
