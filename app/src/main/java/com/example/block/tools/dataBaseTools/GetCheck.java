package com.example.block.tools.dataBaseTools;

import android.os.Handler;
import android.util.Log;

import com.example.block.MainGames.logic.LoadGame;
import com.example.block.login.Login;
import com.example.block.tools.ArrayAndString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetCheck extends Thread {

    private Handler mHandler;
    private String checkPointName;

    public GetCheck(Handler mHandler,String checkPointName)
    {
        this.mHandler = mHandler;
        this.checkPointName = checkPointName;
    }

    public void run() {

        int i = 0;
        LoadGame.length = 0;


//        LoadGame.viewPos = new int[1000][3];

        Log.e("ABCD","开始链接");

        try {
            Connection cn = ConDatabase.getConnection();
            String sql = "SELECT * FROM usercheckPoint WHERE userid = '"+ Login.usersid +"' AND checkpointName = '"+checkPointName+"'";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            if (set.next())
            {
                LoadGame.viewPos = ArrayAndString.stringtoCheckpoint(set.getString("checkpoint"));
                LoadGame.length = LoadGame.viewPos.length;
            }
            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
            mHandler.sendEmptyMessage(0x12);
        }catch (ClassNotFoundException e) {
            mHandler.sendEmptyMessage(0x13);
            e.printStackTrace();
        }catch (Exception e) {
            mHandler.sendEmptyMessage(0x13);
            e.printStackTrace();
        }
    }
}
