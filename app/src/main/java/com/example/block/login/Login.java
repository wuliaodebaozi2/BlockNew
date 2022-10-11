package com.example.block.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.block.R;
import com.example.block.tools.dataBaseTools.DBLogin;
import com.example.block.tools.dataBaseTools.DownloadUserImg;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.block.menu.MainMenu.scaleX;
import static com.example.block.menu.MainMenu.scaleY;

public class Login extends Activity implements View.OnTouchListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//消除屏幕状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        init();

    }
    @SuppressLint("ClickableViewAccessibility")
    private void init()
    {
        login = (Button)findViewById(R.id.Login1);
        register = (Button) findViewById(R.id.register);
        userid = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);
        userImg = (ImageView) findViewById(R.id.userImg);

        setLayout(userid);
        setLayout(password);
        setLayout(login);
        setLayout(register);
        setLayout(userImg);

        login.setOnTouchListener(this);
        register.setOnTouchListener(this);
        userid.setOnFocusChangeListener(new View.OnFocusChangeListener() {

        @Override

        public void onFocusChange(View v, boolean hasFocus) {

            if(hasFocus) {

// 此处为得到焦点时的处理内容

            } else {

// 此处为失去焦点时的处理内容
                if (userid.getText().toString().length() > 0)
                {
                    DownloadUserImg downloadUserImg = new DownloadUserImg(userid.getText().toString(),mHandler);
                    downloadUserImg.start();
                }
                else Toast.makeText(Login.this,"用户名为空",Toast.LENGTH_LONG).show();


            }

        }

    });

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.Login1:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        if (userid.getText().toString().length() > 0)
                        {
                            DBLogin loginThread = new DBLogin(mHandler,userid.getText().toString(),password.getText().toString());
                            loginThread.start();
                            alertDialog1 = new AlertDialog.Builder(Login.this)
                                    .setTitle("提示")//标题
                                    .setMessage("正在登陆...")//内容
                                    .setIcon(R.mipmap.ic_launcher)//图标
                                    .create();
                            alertDialog1.show();
                        }
                        else
                        {
                            Toast.makeText(Login.this,"用户名为空",Toast.LENGTH_LONG).show();
                        }

                        break;
                }
                break;
            case R.id.register:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        Intent intent = new Intent();
                        intent.setClass(Login.this,Register.class);
                        startActivityForResult(intent,0x123);
                        break;
                }
                break;
        }
        return false;
    }
    private AbsoluteLayout.LayoutParams layoutParams;
    private void setLayout(View button)
    {
        switch (button.getId())
        {
            case R.id.userImg:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(590* scaleX);
                layoutParams.y = (int)(30*scaleY);
                layoutParams.width = (int)(100*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                //button.setBackgroundResource(R.drawable.user_img);
                break;
            case R.id.id:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(390* scaleX);
                layoutParams.y = (int)(180*scaleY);
                layoutParams.width = (int)(500*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.password:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(390* scaleX);
                layoutParams.y = (int)(380*scaleY);
                layoutParams.width = (int)(500*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.Login1:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(170* scaleX);
                layoutParams.y = (int)(550*scaleY);
                layoutParams.width = (int)(300*scaleX);
                layoutParams.height = (int)(150*scaleY);
                button.setLayoutParams(layoutParams);
                button.setBackgroundResource(R.drawable.login);
                break;
            case R.id.register:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(810* scaleX);
                layoutParams.y = (int)(550*scaleY);
                layoutParams.width = (int)(300*scaleX);
                layoutParams.height = (int)(150*scaleY);
                button.setLayoutParams(layoutParams);
                button.setBackgroundResource(R.drawable.go_register);
                break;

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        //如果data等于null返回
        if(data==null)
            return ;

        //更新编辑框内容为所选的号码
        userid.setText(data.getExtras().getString("usersid"));
        password.setText(data.getExtras().getString("password"));
    }
    public static String usersid;
    private AlertDialog alertDialog1;
    Button login,register;
    EditText userid,password;
    private ImageView userImg;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x1234)
            {
                if (alertDialog1 != null)
                {
                    alertDialog1.setMessage("登陆成功");
                    alertDialog1.dismiss();
                }

                usersid = userid.getText().toString();

                SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);

                //获得sp的编辑器
                SharedPreferences.Editor ed = sp.edit();

                //以键值对的显示将用户名和密码保存到sp中
                ed.putString("username", usersid);

                //提交用户名和密码
                ed.commit();

                Intent intent=new Intent();
                intent.putExtra("usersid", usersid);
                setResult(0x1, intent);

                finish();
            }
            else if (msg.what == 0x1235)
            {
                if (alertDialog1 != null)
                {
                    alertDialog1.setMessage("登陆失败");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            alertDialog1.dismiss();
                        }
                    },1000);//延时1s执行
                }
            }
            else if (msg.what == 0x15)
            {
                userImg.setImageBitmap((Bitmap)msg.obj);
            }
            else if (msg.what == 0x16)
            {
                Toast.makeText(Login.this,"没有此用户",Toast.LENGTH_LONG).show();
            }
        }
    };




};