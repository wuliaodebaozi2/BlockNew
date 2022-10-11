package com.example.block.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;

import com.example.block.MainGames.gameView.GameSurface;
import com.example.block.MainGames.gameView.MainActivity;
import com.example.block.MainGames.logic.LoadGame;
import com.example.block.R;

import static com.example.block.menu.MainMenu.scaleX;
import static com.example.block.menu.MainMenu.scaleY;

public class ChooseCheckPoint extends Activity implements View.OnTouchListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//消除屏幕状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.choose_checkpoint);

        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init()
    {
        officialCheckpoint = (Button) findViewById(R.id.official_checkpoint);
        selfDefineCheckPoint = (Button) findViewById(R.id.selfDefinecheckPoint);
        officialCheckpoint.setOnTouchListener(this);
        selfDefineCheckPoint.setOnTouchListener(this);

        setLayout(officialCheckpoint);
        setLayout(selfDefineCheckPoint);
    }
    private void setLayout(View v)
    {
        AbsoluteLayout.LayoutParams layoutParams;
        switch (v.getId())
        {
            case R.id.official_checkpoint:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(300* scaleX);
                layoutParams.y = (int)(280*scaleY);
                layoutParams.width = (int)(240*scaleX);
                layoutParams.height = (int)(160*scaleY);
                v.setLayoutParams(layoutParams);
                v.setBackgroundResource(R.drawable.official_checkpoint);
                break;
            case R.id.selfDefinecheckPoint:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(740* scaleX);
                layoutParams.y = (int)(280*scaleY);
                layoutParams.width = (int)(240*scaleX);
                layoutParams.height = (int)(160*scaleY);
                v.setLayoutParams(layoutParams);
                v.setBackgroundResource(R.drawable.user_checkpoint);
                break;
        }
    }
    private Button officialCheckpoint,selfDefineCheckPoint;
    private void openGame()
    {
        Intent intent = new Intent();
        intent.setClass(ChooseCheckPoint.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.official_checkpoint:


                break;
//            case R.id.checkPoint2:
//
//                GameSurface.viewPos = viewPos2;
//                GameSurface.length = 104;
//                openGame();
//                break;
            case R.id.selfDefinecheckPoint:

//                if (editText.getText().toString().length() > 0)
//                {
//                    GetCheck getCheck = new GetCheck(mHandler,editText.getText().toString());
//                    getCheck.start();
//
//                }
//                else
//                {
//                    Toast.makeText(ChooseCheckPoint.this,"请输入关卡名字",Toast.LENGTH_SHORT).show();
//                }



                break;
        }
    }





    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.official_checkpoint:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        LoadGame.viewPos = viewPos2;
                        LoadGame.length = 104;
                        openGame();
                        break;
                }
                break;
            case R.id.selfDefinecheckPoint:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        Intent intent = new Intent();
                        intent.setClass(ChooseCheckPoint.this, ChooseUserCheckpoint.class);
                        startActivity(intent);
                        break;
                }
                break;
        }
        return false;
    }
    private  int[][] viewPos2  = {
            {-1  , 770 ,  440},
            {-2  , 824 ,  440},
            {-1  , 878 ,  440},
            {-1  , 1030  , 590},
            {-1  , 1084  , 590},
            {-1  , 1084  , 537},
            {-1 ,  1138  , 483},
            {-1  , 1138  , 590},
            {-1  , 1138  , 537},
            {-1 ,  1192  , 429},
            {-1 ,  1192  , 483},
            {-1  , 1192  , 590},
            {-1  , 1192  , 537},
            {-1 ,  1246  , 375},
            {-1 ,  1246  , 429},
            {-1 ,  1246  , 483},
            {-1  , 1246  , 590},
            {-1  , 1246  , 537},
            {-1 ,  1300  , 375},
            {-1 ,  1300  , 429},
            {-1 ,  1300  , 483},
            {-1  , 1300  , 590},
            {-1  , 1300  , 537},
            {1  , 1434 ,  591},
            {-2 ,  1434 + 50 , 440},
            {-2 , 1434 + 50  ,  230},
            {1  , 1514 ,  591},
            {-1 ,  1488 + 50 , 230},
            {-1 ,  1542 + 50, 230},
            {-1 ,  1596 + 50 , 230},
            {-1 ,  1746  , 591},
            {-1 ,  1746  , 537},
            {-1 ,  1746  , 483},
            {-1 ,  1746  , 429},
            {-1 ,  1746  , 375},
            {-1 ,  1800  , 591},
            {-1 ,  1800  , 537},
            {1 ,  1860 ,  591},
            {1  , 1930 ,  591},
            {-4  , 2367,   440},
            {1  , 2416 ,  591},
            {1  , 2476 ,  591},
            {-2,   2726,   440},
            {-2,   2726,   230},
            {-2,   2780,   440},
            {-2,   2780,   230},
            {-2,   2834,   440},
            {-2,   2834,   230},
            {-2,   2888,   440},
            {-2,   2888,   230},
            {1  , 2936 ,  591},
            {-2,   2942,   440},
            {-2,   2942,   230},
            {1  , 2996 ,  591},
            {-1  , 3494 ,  440},
            {-1  , 3548 ,  230},
            {-1  , 3602 ,  230},
            {-1  , 3656 ,  230},
            {-1  , 3710 ,  230},
            {-4  , 3803,   440},
            {-2,   4064,   440},
            {-2,   4118,   440},
            {-2,   4172,   440},
            {-2,   4226,   440},
            {1  , 4301 ,  591},
            {-2,   4370,   440},
            {-3  , 4423  , 644},
            {-2,   4424,   440},
            {-2,   4478,   440},
            {-2,   4532,   440},
            {-4  , 4670,   480},
            {1  , 4901 ,  591},
            {1  , 5136 ,  591},
            {-4  , 5300,   440 },
            {-3  , 5450  , 644 },
            {1  , 5713 ,  591},
            {-4  , 5919,   540},
            {1  , 6100 ,  591},
            {-4  , 6270,   440},
            {-1  , 6426, 230},
            {-1  , 6480 , 230},
            {-1  , 6534 , 230},
            {-1  , 6588 , 230},
            {-4  , 6684,   380},
            {-3  , 7144  , 644 },
            {-3  , 7820  , 644 },
            {-1  , 7920  ,591},
            {-1  , 7920  ,537},
            {-1  , 7920  ,483},
            {1  , 8178 ,  591},
            {1  , 8238 ,  591},
            {-1  ,8278  ,440},
            {-1  ,8404  ,230},
            {-1  ,8458  ,230},
            {-1  ,8512  ,230},
            {-1  ,8566  ,230},
            {-1  ,8620  ,230},
            {-2  ,8845  ,230},
            {-2  ,8744  ,440},
            {-4  , 9050,   480},
            {1  , 9456 ,  591},
            {-1  , 9512  , 440},
            {-1  , 9566  , 440},
            {-2  , 9566  , 230}
//            {8  , 10500 , 145},
//
//            {9,0,0,100,0}


    };
}
