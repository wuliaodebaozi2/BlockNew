package com.example.block.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.block.R;
import com.example.block.login.Login;
import com.example.block.login.LoginDialog;
import com.example.block.tools.dataBaseTools.DownloadUserImg;
import com.example.block.MainGames.views.Views;

import cn.pedant.SweetAlert.SweetAlertDialog;
import top.wuliaodebaozi2.blockgame.loginlibrary.ui.LoginActivity;


public class MainMenu extends Activity implements View.OnTouchListener{
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//消除屏幕状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);

        Views.context = MainMenu.this;
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);



        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        scaleX =  screenWidth/(float)1280;
        scaleY =  screenHeight/(float)720;







        init();



        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent();
//                intent.setClass(MainMenu.this, Login.class);
//                startActivityForResult(intent,0x1);
                Intent intent = new Intent();
                intent.setClass(MainMenu.this, LoginActivity.class);
                startActivityForResult(intent,0x1);
            }
        });
        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent();
//                intent.setClass(MainMenu.this, Login.class);
//                startActivityForResult(intent,0x1);
                Intent intent = new Intent();
                intent.setClass(MainMenu.this, LoginActivity.class);
                startActivityForResult(intent,0x1);
            }
        });



        autologon();
    }

    private void autologon()
    {
        //创建SharedPreferences对象
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);

        if (sp != null)
        {
            //获得保存在SharedPredPreferences中的用户名和密码
            String username = sp.getString("username", "");
            if (username.length() > 0)
            {
                Login.usersid = username;
                LoginText.setText(Login.usersid);
                DownloadUserImg downloadUserImg = new DownloadUserImg(Login.usersid,mHandler);
                downloadUserImg.start();
            }
        }


    }
    @SuppressLint("ClickableViewAccessibility")
    private void init()
    {
        start = (Button) findViewById(R.id.start);
        set = (Button)findViewById(R.id.set);
        imageView = (ImageView) findViewById(R.id.image);
        design_checkpoint = (Button)findViewById(R.id.design_checkpoint);
        missionText = (TextView) findViewById(R.id.missionText);
        LoginText = (TextView) findViewById(R.id.logintext);
        userImg = (ImageView) findViewById(R.id.userImg);



        set.setOnTouchListener(this);
        start.setOnTouchListener(this);
        design_checkpoint.setOnTouchListener(this);


        //布局
        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) start.getLayoutParams();
        layoutParams.x = (int)(layoutParams.x*scaleX);
        layoutParams.y = (int)(layoutParams.y*scaleX);
        layoutParams.width = (int)(layoutParams.width*scaleX);
        layoutParams.height = (int)(layoutParams.height*scaleY);
        start.setLayoutParams(layoutParams);


        layoutParams = (AbsoluteLayout.LayoutParams) set.getLayoutParams();
        layoutParams.x = (int)(layoutParams.x*scaleX);
        layoutParams.y = (int)(layoutParams.y*scaleX);
        layoutParams.width = (int)(layoutParams.width*scaleX);
        layoutParams.height = (int)(layoutParams.height*scaleY);
        set.setLayoutParams(layoutParams);

        layoutParams = (AbsoluteLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.x = (int)(layoutParams.x*scaleX);
        layoutParams.y = (int)(layoutParams.y*scaleX);
        layoutParams.width = (int)(layoutParams.width*scaleX);
        layoutParams.height = (int)(layoutParams.height*scaleY);
        imageView.setLayoutParams(layoutParams);


        layoutParams = (AbsoluteLayout.LayoutParams) design_checkpoint.getLayoutParams();
        layoutParams.x = (int)(layoutParams.x*scaleX);
        layoutParams.y = (int)(layoutParams.y*scaleX);
        layoutParams.width = (int)(layoutParams.width*scaleX);
        layoutParams.height = (int)(layoutParams.height*scaleY);
        design_checkpoint.setLayoutParams(layoutParams);

        layoutParams = (AbsoluteLayout.LayoutParams) missionText.getLayoutParams();
        layoutParams.x = (int)(layoutParams.x*scaleX);
        layoutParams.y = (int)(layoutParams.y*scaleX);
        missionText.setLayoutParams(layoutParams);
        missionText.setTextSize(12*scaleX);

        layoutParams = (AbsoluteLayout.LayoutParams) userImg.getLayoutParams();
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = (int)(100*scaleX);
        layoutParams.height = (int)(100*scaleY);
        userImg.setLayoutParams(layoutParams);

        layoutParams = (AbsoluteLayout.LayoutParams) LoginText.getLayoutParams();
        layoutParams.x = (int)(100*scaleX);
        layoutParams.y = 0;
        LoginText.setLayoutParams(layoutParams);
        LoginText.setTextSize(12*scaleX);

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {

        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0x15:
                    userImg.setImageBitmap((Bitmap)msg.obj);
                    break;
                case 0x16:
                    Toast.makeText(MainMenu.this,"头像下载失败",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    private Button start,set,design_checkpoint;
    private static Intent intent;
    private ImageView imageView,userImg;
    public static float screenWidth,screenHeight,scaleX,scaleY;
    private TextView missionText;
    private TextView LoginText;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        //如果data等于null返回
        if(data==null)
            return ;
        String phoneNumber=data.getExtras().getString("usersid");
        //更新编辑框内容为所选的号码
        LoginText.setText(phoneNumber);
        DownloadUserImg downloadUserImg = new DownloadUserImg(Login.usersid,mHandler);
        downloadUserImg.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.start:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        intent = new Intent();
                        intent.setClass(MainMenu.this, ChooseCheckPoint.class);
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.set:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        LoginDialog loginDialog = new LoginDialog(MainMenu.this);
                        loginDialog.show();
//                        Intent intent = new Intent(MainMenu.this,SetMenu.class);
//                        startActivity(intent);
                        break;
                }
                break;
            case R.id.design_checkpoint:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        if (Login.usersid != null)
                        {
                            intent = new Intent();
                            intent.setClass(MainMenu.this, EditorNewCheckpoint.class);
                            startActivity(intent);
                        }
                        else
                        {
                            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("提示")
                                    .setContentText("你还没有登陆！")
                                    .setCancelText("取消")
                                    .setConfirmText("登陆")
                                    .showCancelButton(true)
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();
                                        }
                                    })
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            Intent intent = new Intent();
                                            intent.setClass(MainMenu.this, Login.class);
                                            startActivityForResult(intent,0x1);
                                            sDialog.dismiss();
                                        }
                                    })
                                    .show();
                        }

                        break;
                }
                break;
        }
        return false;
    }
}
