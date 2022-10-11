package com.example.block.MainGames.gameView;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.example.block.MainGames.logic.Logic;
import com.example.block.R;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//消除屏幕状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  //屏幕常亮
        //GameSurface mGameView = new GameSurface(this);


        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        SCREENWIDTH = dm.widthPixels;         // 屏幕宽度（像素）
        SCREENHEIGHT = dm.heightPixels;       // 屏幕高度（像素）

        float scaleX = SCREENWIDTH / (float)1280;
        float scaleY = SCREENHEIGHT / (float)720;
        Log.e("TAG",SCREENWIDTH +"  "+ SCREENHEIGHT + "屏幕");
        setContentView(R.layout.activity_main);



        left = (Button) findViewById(R.id.leftBt);
        right = (Button)findViewById(R.id.rightBt);
        up = (Button)findViewById(R.id.UPBt);
        gameSurface = (GameSurface)findViewById(R.id.GameSurface);

        left.setOnTouchListener(this);
        right.setOnTouchListener(this);
        up.setOnTouchListener(this);

        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) right.getLayoutParams();
        layoutParams.x = (int)(180*scaleX);
        layoutParams.y = (int)(500*scaleY);
        layoutParams.width = (int)(160*scaleX);
        layoutParams.height = (int)(160*scaleY);
        right.setLayoutParams(layoutParams);

        AbsoluteLayout.LayoutParams layoutParams1 = (AbsoluteLayout.LayoutParams) left.getLayoutParams();
        layoutParams1.x = (int)(0*scaleX);
        layoutParams1.y = (int)(500*scaleY);
        layoutParams1.width = (int)(160*scaleX);
        layoutParams1.height = (int)(160*scaleY);
        left.setLayoutParams(layoutParams1);

        AbsoluteLayout.LayoutParams layoutParams2 = (AbsoluteLayout.LayoutParams) up.getLayoutParams();
        layoutParams2.x = (int)(1100*scaleX);
        layoutParams2.y = (int)(500*scaleY);
        layoutParams2.width = (int)(160*scaleX);
        layoutParams2.height = (int)(160*scaleY);
        up.setLayoutParams(layoutParams2);



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();//注释掉这行,back键不退出activity
        gameSurface.surfaceDestroyed(null);
        finish();

    }
    private GameSurface gameSurface;
    private Button left,right,up;

    private Timer timerLeft,timerRight;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.leftBt:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    Logic.marioPo.xSpeed = -305;
                    left.setBackgroundResource(R.drawable.left_press);              //按下后修改背景图
                    timerLeft = new Timer();
                    timerLeft.schedule(new TimerTask() {
                        @Override
                        public void run() {
                           // Logic.marioPo.xSpeed = -405;
                        }
                    }, 2500); // 按下时长设置

                }else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    Logic.marioPo.xSpeed = 0;
                    left.setBackgroundResource(R.drawable.left);
                    timerLeft.cancel();
                    timerLeft = null;
                }
                break;
            case R.id.rightBt:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    Logic.marioPo.xSpeed = 305;
                    right.setBackgroundResource(R.drawable.right_press);

                    timerRight = new Timer();
                    timerRight.schedule(new TimerTask() {
                        @Override
                        public void run() {
                           // Logic.marioPo.xSpeed = 405;
                        }
                    }, 2500); // 按下时长设置
                }else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    Logic.marioPo.xSpeed = 0;
                    right.setBackgroundResource(R.drawable.right);

                    timerRight.cancel();
                    timerRight = null;
                }
                break;
            case R.id.UPBt:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (Logic.marioPo.ySpeed == 0 || Logic.marioPo.ySpeed == 80)
                    {
                        Logic.marioPo.ySpeed = -805;
                        up.setBackgroundResource(R.drawable.jump_press);
                    }

                    //Logic.marioPo.yA = 3000;
                }else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    up.setBackgroundResource(R.drawable.jump);
                }
                break;
        }
        return true;
    }

    public static int SCREENWIDTH,SCREENHEIGHT;
}


