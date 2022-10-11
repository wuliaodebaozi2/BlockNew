package com.example.block.tools.dataBaseTools;

import android.os.Handler;
import android.util.Log;

import com.example.block.menu.UserCheckpointInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GetMyCheckpointInfos extends Thread {

    private Handler mHandler;
    private List<UserCheckpointInfo> userCheckpointInfos;
    private String userid;
    public GetMyCheckpointInfos(Handler mHandler, List<UserCheckpointInfo> userCheckpointInfos,String userid)
    {
        this.mHandler = mHandler;
        this.userCheckpointInfos = userCheckpointInfos;
        this.userid = userid;
        userCheckpointInfos.clear();
    }
    @Override
    public void run() {

        super.run();

        Log.e("ABCD","开始链接");
        try {
            Connection cn = ConDatabase.getConnection();
            String sql = "SELECT * FROM usercheckPoint WHERE userid = '"+userid+"';";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while (set.next())
            {
                String checkpointName = set.getString("checkpointName");
                String author = set.getString("userid");
                int downLoadTimes = set.getInt("downloadtimes");
                int likes = set.getInt("likes");
                UserCheckpointInfo userCheckpointInfo = new UserCheckpointInfo(checkpointName,author,downLoadTimes,likes);
                userCheckpointInfos.add(userCheckpointInfo);
            }
            mHandler.sendEmptyMessage(0x21);
            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (ClassNotFoundException e) {
            mHandler.sendEmptyMessage(0x22);
            e.printStackTrace();
        }catch (SQLException e) {
            mHandler.sendEmptyMessage(0x22);
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
