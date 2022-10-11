package com.example.block.tools.dataBaseTools;
//GetDesignCheck

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.block.MainGames.logic.LoadGame;
import com.example.block.login.Login;
import com.example.block.tools.ArrayAndString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetDesignCheck extends Thread {

    private Handler mHandler;
    private String checkPointName;
    private int [][] viewPos;
    private int length = 0;

    public GetDesignCheck(Handler mHandler,String checkPointName)
    {
        this.mHandler = mHandler;
        this.checkPointName = checkPointName;
    }

    public void run() {

        int i = 0;

        Log.e("ABCD","开始链接");

        try {
            Connection cn = ConDatabase.getConnection();
            String sql = "SELECT * FROM usercheckPoint WHERE userid = '"+ Login.usersid +"' AND checkpointName = '"+checkPointName+"'";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            if (set.next())
            {
                viewPos = ArrayAndString.stringtoCheckpoint(set.getString("checkpoint"));
                length = viewPos.length;
            }
            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
            Bundle bundle = new Bundle();
            ViewposInfos viewposInfos = new ViewposInfos();
            viewposInfos.setLength(length);
            viewposInfos.setViewPos(viewPos);
            bundle.putSerializable("viewposInfos",viewposInfos);
            Message message = new Message();
            message.setData(bundle);
            message.what = 0x51;
            mHandler.sendMessage(message);
        }catch (ClassNotFoundException e) {
            mHandler.sendEmptyMessage(0x52);
            e.printStackTrace();
        }catch (Exception e) {
            mHandler.sendEmptyMessage(0x52);
            e.printStackTrace();
        }
    }
}
