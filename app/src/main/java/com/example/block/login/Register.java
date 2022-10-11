package com.example.block.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.block.R;
import com.example.block.designCheckpoint.DesignCheckpoint;
import com.example.block.tools.dataBaseTools.ConDatabase;
import com.example.block.tools.dataBaseTools.DBRegister;
import com.example.block.tools.dataBaseTools.UploadUserImg;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.block.menu.MainMenu.scaleX;
import static com.example.block.menu.MainMenu.scaleY;

public class Register extends Activity implements View.OnTouchListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//消除屏幕状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);

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

        register.setOnTouchListener(this);
        login.setOnTouchListener(this);
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
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
                        finish();
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
                        if (userid.getText().toString().length() > 0)
                        {
                            if (img != null)
                            {
                                DBRegister register = new DBRegister(mHandler,userid.getText().toString(),password.getText().toString(),img);
                                register.start();

                                alertDialog1 = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                                alertDialog1.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                alertDialog1.setTitleText("正在注册...");
                                alertDialog1.setCancelable(false);
                                alertDialog1.setCanceledOnTouchOutside(false);
                                alertDialog1.show();
                            }
                            else
                            {
                                Toast.makeText(Register.this,"请设置头像",Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(Register.this,"用户名不能为空",Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                break;
        }
        return false;
    }
    private SweetAlertDialog alertDialog1;
    Button login,register;
    EditText userid,password;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0x1234:
                    if (alertDialog1 != null)
                    {
                        alertDialog1.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        alertDialog1.setTitleText("注册成功!");
                        alertDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent=new Intent();
                                intent.putExtra("usersid", userid.getText().toString());
                                intent.putExtra("password", password.getText().toString());
                                setResult(0x123, intent);

                                finish();
                            }
                        });
                        alertDialog1.show();
                    }
                    else
                    {
                        alertDialog1 = new SweetAlertDialog(Register.this,SweetAlertDialog.SUCCESS_TYPE);
                        alertDialog1.setTitleText("注册成功!");
                        alertDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent=new Intent();
                                intent.putExtra("usersid", userid.getText().toString());
                                intent.putExtra("password", password.getText().toString());
                                setResult(0x123, intent);

                                finish();
                            }
                        });
                        alertDialog1.show();
                    }
                    break;
                case 0x1235:
                    if (alertDialog1 != null)
                    {
                        alertDialog1.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        alertDialog1.setTitleText("注册失败失败，用户名被占用");
                        alertDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                alertDialog1.dismiss();
                            }
                        });
                        alertDialog1.show();

                    }
                    else
                    {
                        alertDialog1 = new SweetAlertDialog(Register.this,SweetAlertDialog.ERROR_TYPE);
                        alertDialog1.setTitleText("注册失败失败，用户名被占用");
                        alertDialog1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                alertDialog1.dismiss();
                            }
                        });
                        alertDialog1.show();
                    }
                    break;
            }
        }
    };




    private ImageView userImg;
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
                button.setBackgroundResource(R.drawable.add_user_img);
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
                button.setBackgroundResource(R.drawable.back_login);
                break;
            case R.id.register:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(810* scaleX);
                layoutParams.y = (int)(550*scaleY);
                layoutParams.width = (int)(300*scaleX);
                layoutParams.height = (int)(150*scaleY);
                button.setLayoutParams(layoutParams);
                button.setBackgroundResource(R.drawable.register);
                break;


        }

    }

    private Bitmap img;
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();

                startPhotoCrop(uri);
            }
        }
        if (resultCode == RESULT_OK) {
            //裁切成功
            if (requestCode == UCrop.REQUEST_CROP) {
                Uri croppedFileUri = UCrop.getOutput(data);
                //获取默认的下载目录
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Register.this.getContentResolver(), croppedFileUri);
                    userImg.setImageBitmap(bitmap);
                    img = bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //裁切失败
        if (resultCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, "裁切图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void startPhotoCrop(Uri uri){
        UCrop.Options options = new UCrop.Options();
        //裁剪后图片保存在文件夹中
        Uri destinationUri = Uri.fromFile(new File(getExternalCacheDir(), "uCrop.jpg"));
        UCrop uCrop = UCrop.of(uri, destinationUri);//第一个参数是裁剪前的uri,第二个参数是裁剪后的uri
        uCrop.withAspectRatio(1,1);//设置裁剪框的宽高比例
        //下面参数分别是缩放,旋转,裁剪框的比例
        options.setAllowedGestures(UCropActivity.ALL,UCropActivity.NONE,UCropActivity.ALL);
        options.setToolbarTitle("剪裁图片");//设置标题栏文字
        options.setCropGridStrokeWidth(2);//设置裁剪网格线的宽度(我这网格设置不显示，所以没效果)
        options.setCropFrameStrokeWidth(2);//设置裁剪框的宽度
        options.setMaxScaleMultiplier(3);//设置最大缩放比例
        options.setHideBottomControls(false);//隐藏下边控制栏
        options.setShowCropGrid(true);  //设置是否显示裁剪网格
//        options.setOvalDimmedLayer(true);//设置是否为圆形裁剪框
        options.setShowCropFrame(true); //设置是否显示裁剪边框(true为方形边框)
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
        options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
        options.setCropGridColor(Color.YELLOW);//设置裁剪网格的颜色
        options.setCropFrameColor(Color.YELLOW);//设置裁剪框的颜色
        uCrop.withOptions(options);
        uCrop.start(this);
    }
};