package com.example.block.tools.dataBaseTools;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.example.block.tools.ImgTools.BitmapTranString;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DBRegister extends Thread{

    private Handler mHandler;

    private String userid,password;

    private Bitmap img;

    public DBRegister(Handler mHandler,String userid,String password,Bitmap img)
    {
        this.mHandler = mHandler;
        this.userid = userid;
        this.password = password;
        this.img = img;
    }

    @Override
    public void run() {

        Log.e("ABCD","开始链接");

        try {

            Connection cn = ConDatabase.getConnection();
            String sql = "CALL createuser ('"+userid+"','"+password+"','"+ BitmapTranString.bitmapToString(img) +"')";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.executeUpdate();

            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
            mHandler.sendEmptyMessage(0x1234);
        }catch (ClassNotFoundException e) {
            mHandler.sendEmptyMessage(0x1235);
            e.printStackTrace();
        }catch (Exception e) {
            mHandler.sendEmptyMessage(0x1235);
            e.printStackTrace();
        }
    }
}