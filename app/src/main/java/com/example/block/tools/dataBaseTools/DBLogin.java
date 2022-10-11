package com.example.block.tools.dataBaseTools;

import android.os.Handler;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBLogin extends Thread{

    private Handler mHandler;
    private String userid,password;
    public DBLogin(Handler mHandler,String userid,String password)
    {
        this.mHandler = mHandler;
        this.userid = userid;
        this.password = password;
    }
    @Override
    public void run() {

        Log.e("ABCD","开始链接");
        try {
            Connection cn = ConDatabase.getConnection();
            String sql = "SELECT * FROM users WHERE ID = '"+userid+"' AND pw = '"+password+"'";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            if (set.next())
            {
                mHandler.sendEmptyMessage(0x1234);
            }
            else
            {
                mHandler.sendEmptyMessage(0x1235);
            }
            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (ClassNotFoundException e) {
            Log.e("ABCD","链接失败");
            e.printStackTrace();
        }catch (SQLException e) {
            Log.e("ABCD","链接失败");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
