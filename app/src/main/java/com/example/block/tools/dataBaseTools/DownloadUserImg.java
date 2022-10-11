package com.example.block.tools.dataBaseTools;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.block.tools.ImgTools.BitmapTranString;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DownloadUserImg extends Thread {
    private String userid;
    private Bitmap userImg;
    private Handler mHandler;

    public DownloadUserImg(String userid,Handler mHandler)
    {
        this.userid = userid;
        this.mHandler = mHandler;
    }
    @Override
    public void run() {

        Log.e("ABCD","开始链接");

        try {
            Connection cn = ConDatabase.getConnection();
            String sql = "SELECT img FROM userImg WHERE id = '"+userid+"'";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            if (set.next())
            {
                userImg = BitmapTranString.stringToBitmap(set.getString("img"));
                Message message = Message.obtain();
                message.what = 0x15;
                message.obj = userImg;
                mHandler.sendMessage(message);
            }
            else
            {
                mHandler.sendEmptyMessage(0x16);
            }
            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (ClassNotFoundException e) {
            mHandler.sendEmptyMessage(0x16);
            Log.e("ABCD","链接失败");
            e.printStackTrace();
        }catch (Exception e) {
            mHandler.sendEmptyMessage(0x16);
            Log.e("ABCD","链接失败");
            e.printStackTrace();
        }
    }
}
