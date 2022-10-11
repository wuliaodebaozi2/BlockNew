package com.example.block.tools.dataBaseTools;

import android.graphics.Bitmap;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import android.os.Handler;

import com.example.block.tools.ImgTools.BitmapTranString;

public class UploadUserImg extends Thread {
    private Handler mHandler;
    private String userId;
    private Bitmap img;
    public UploadUserImg(Handler mHandler,String userId,Bitmap img)
    {
        this.mHandler = mHandler;
        this.userId = userId;
        this.img = img;
    }
    public void run() {

        Log.e("ABCD","开始链接");

        try {
            Connection cn = ConDatabase.getConnection();
            String sql = "INSERT INTO userImg(id,img) VALUES (?,?);";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, BitmapTranString.bitmapToString(img));
            if (ps.executeUpdate() > 0)
            {
                mHandler.sendEmptyMessage(0x25);
            }
            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (ClassNotFoundException e) {

            e.printStackTrace();
            mHandler.sendEmptyMessage(0x26);
        }catch (Exception e) {

            e.printStackTrace();
            mHandler.sendEmptyMessage(0x26);
        }
    }
}
