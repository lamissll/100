package com.example.wansmin.a100;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by wansmin on 2016/12/17.
 */
public class GamePage extends Activity {

    public Button[] bt = new Button[50];
    public int[] id = new int[50];
    public TextView tv;
    public int nowShape = 0,cur=0,lastShape = 0;
    public boolean[] chosed  = new boolean[50];
    public boolean[] vis  = new boolean[50];
    public boolean isjudge = false;
    public int cnt = 0,nowState = 4,grade = 0;
    public SoundPool soundPool;
    public Map<Integer, Integer> soundMap;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            chgcolor();
            super.handleMessage(msg);
        }
    };
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent();
            intent.putExtra("grade",grade);
            setResult(100, intent);
            finish();
            super.handleMessage(msg);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gamepage);

        soundPool=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        soundMap=new HashMap<Integer, Integer>();
        soundMap.put(1, soundPool.load(GamePage.this, R.raw.click, 1));
        soundMap.put(2, soundPool.load(GamePage.this, R.raw.lose, 1));

        id[1] = R.id.bt_1; id[2] = R.id.bt_2;  id[3] = R.id.bt_3;   id[4] = R.id.bt_4;
        id[5] = R.id.bt_5; id[6] = R.id.bt_6;  id[7] = R.id.bt_7;   id[8] = R.id.bt_8;
        id[9] = R.id.bt_9; id[10] = R.id.bt_10;  id[11] = R.id.bt_11;   id[12] = R.id.bt_12;
        id[13] = R.id.bt_13; id[14] = R.id.bt_14;  id[15] = R.id.bt_15;   id[16] = R.id.bt_16;
        id[17] = R.id.bt_17; id[18] = R.id.bt_18;  id[19] = R.id.bt_19;   id[20] = R.id.bt_20;
        id[21] = R.id.bt_21; id[22] = R.id.bt_22;  id[23] = R.id.bt_23;   id[24] = R.id.bt_24;
        id[25] = R.id.bt_25; id[26] = R.id.bt_26;  id[27] = R.id.bt_27;   id[28] = R.id.bt_28;
        id[29] = R.id.bt_29; id[30] = R.id.bt_30;  id[31] = R.id.bt_31;   id[32] = R.id.bt_32;
        id[33] = R.id.bt_33;   id[34] = R.id.bt_34;
        id[35] = R.id.bt_35; id[36] = R.id.bt_36;  id[37] = R.id.bt_37;   id[38] = R.id.bt_38;
        id[39] = R.id.bt_39; id[40] = R.id.bt_40;  id[41] = R.id.bt_41;   id[42] = R.id.bt_42;

        tv  = (TextView) findViewById(R.id.tv_3);
        for(int i=1;i<=42;i++)  {bt[i] = (Button)findViewById(id[i]);}
        rungame();
    }

    public void rungame()
    {
        isjudge = false;cnt =0;
        for(int i=1;i<=42;i++) {paint(i,nowShape); bt[i].setText(" ");}

        cur = nowShape;
        cur = (cur+1)%6;
        lastShape = nowShape;nowShape = cur;

        for(int i=1;i<=42;i++) {chosed[i] = false ;vis[i] = false;}
        for(int i=1;i<=nowState;i++)
        {
            while (true) {
                cur = (int) (1 + Math.random() * 42 - 1 + 1);
                if (chosed[cur]) continue;
                else break;
            }
            chosed[cur] = true;
            bt[cur].setText("☆");
            paint(cur,nowShape);
        }

        handler.sendEmptyMessageDelayed(0, MainActivity.nowSpeed);
    }
    public void chgcolor()
    {
            for (int i = 1; i <= 42; i++)
                if (chosed[i]) {
                    paint(i, lastShape);
                    bt[i].setText(" ");
                }
            isjudge = true;
    }
    public void paint(int id,int Shape)
    {
        switch (Shape){
            case 0:
                bt[id].setBackgroundResource(R.drawable.shape);
                break;
            case 1:
                bt[id].setBackgroundResource(R.drawable.shape_blue);
                break;
            case 2:
                bt[id].setBackgroundResource(R.drawable.shape_green);
                break;
            case 3:
                bt[id].setBackgroundResource(R.drawable.shape_pop);
                break;
            case 4:
                bt[id].setBackgroundResource(R.drawable.shape_yellow);
                break;
            case 5:
                bt[id].setBackgroundResource(R.drawable.shape_red);
                break;

        }
    }
    public void onclick(View v)
    {
        if(isjudge==false) return;
        for(int i=1;i<=42;i++)
        {
            if(v.getId() == id[i])
            {
                if(chosed[i]==false) {
                    soundPool.play(soundMap.get(2), 1, 1, 0, 0, 1);
                    paint(i,(nowShape+1)%6);
                    bt[i].setText("×");
                    isjudge = false;
                    for(int j=1;j<=42;j++)
                    {
                        if(!vis[j] && chosed[j])
                        {
                            paint(j,nowShape);
                            bt[j].setText("☆");
                        }
                    }
                    handler2.sendEmptyMessageDelayed(0,1500);
                }
                else {
                    if(vis[i]==true) continue;
                    soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
                    paint(i,nowShape);
                    bt[i].setText("☆");
                    cnt++;grade+=2;
                    vis[i] = true;
                    tv.setText(String.valueOf(grade)+"%");
                    break;
                }
            }
        }
        if(cnt==nowState) { nowState++;rungame();}
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        soundPool.play(soundMap.get(2), 1, 1, 0, 0, 1);
        return super.onKeyDown(keyCode, event);
    }

}

