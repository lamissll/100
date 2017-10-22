package com.example.wansmin.a100;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ArcProgressBar mArcProgressBar;
    public int nowGrade = 100;
    private RadioGroup speed=null;
    private RadioButton one=null;
    private RadioButton two=null;
    private RadioButton three = null;
    public static int nowSpeed = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        showgrade();
        speed=(RadioGroup) super.findViewById(R.id.speed);
        one=(RadioButton) super.findViewById(R.id.one);
        two=(RadioButton) super.findViewById(R.id.two);
        three=(RadioButton) super.findViewById(R.id.three);
        speed.setOnCheckedChangeListener(new OnCheckedChangeListenerImp());
    }
    private class OnCheckedChangeListenerImp implements RadioGroup.OnCheckedChangeListener {

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String temp=null;
            if(MainActivity.this.one.getId()==checkedId){
                nowSpeed = 2000;
            }
            else if(MainActivity.this.two.getId()==checkedId){
                nowSpeed = 1000;
            }
            else nowSpeed =  500;
        }
    }
        public void onclick(View v)
        {
            switch (v.getId())
            {
                case R.id.bt_start:
                    MainActivity.this.startActivityForResult(new Intent(MainActivity.this,
                            GamePage.class), 1);
                    break;
            }
        }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("wsm","onActivityResult is start");
        if (requestCode == 1 && resultCode == 100) {
            nowGrade = data.getIntExtra("grade",0);
            showgrade();
        }
    }
   public void showgrade()
    {
        mArcProgressBar = (ArcProgressBar) findViewById(R.id.arcProgressBar);
        mArcProgressBar.restart();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, nowGrade);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcProgressBar.setProgress((int) animation.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mArcProgressBar.setProgressDesc(String.valueOf(nowGrade)+"%");
            }
        });
        valueAnimator.setDuration(4000);
        valueAnimator.start();
    }
}
