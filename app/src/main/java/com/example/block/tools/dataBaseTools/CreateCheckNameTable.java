package com.example.block.tools.dataBaseTools;

import android.os.Handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateCheckNameTable extends Thread {
    private Handler mHandler;
    private String tableName;
    private String username;
    public CreateCheckNameTable(Handler mHandler ,String tableName,String username)
    {
        this.mHandler = mHandler;
        this.tableName = tableName;
        this.username = username;
    }
    @Override
    public void run()
    {
        boolean createTableSucces = true;

        try {
            Connection cn =  ConDatabase.getConnection();

            String sql = "SELECT checkpointName FROM usercheckPoint WHERE userid = '"+username+"';";           //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            while (set.next())
            {
                if (set.getString("checkpointName") == tableName)
                {
                    createTableSucces = false;
                    break;
                }
            }




            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (ClassNotFoundException e) {
            createTableSucces = false;
            e.printStackTrace();
        }catch (Exception e) {
            createTableSucces = false;
            e.printStackTrace();
        }

        if (createTableSucces)
        {
            mHandler.sendEmptyMessage(0x21);
        }
        else
        {
            mHandler.sendEmptyMessage(0x22);
        }
    }
}
