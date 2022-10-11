package com.example.block.tools.dataBaseTools;

import android.os.Handler;

import com.example.block.tools.ArrayAndString;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExportCheckPoint extends Thread{

    private String Checkpoint;
    private boolean upLoadSuccess = true;
    private Handler mHandler;
    private String checkPointName,userid;
    public ExportCheckPoint(Handler mHandler ,int[][] Checkpoint,String checkPointName,String userid)
    {

        this.Checkpoint = ArrayAndString.checkPointtoString(Checkpoint);
        this.mHandler = mHandler;
        this.checkPointName = checkPointName;
        this.userid = userid;
    }

    public void run() {


        try {
            Connection cn = ConDatabase.getConnection();
            String sql = "CALL addcheckpoint('"+userid+"','"+checkPointName+"','"+Checkpoint+"')";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.executeUpdate();

            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (Exception e) {
            upLoadSuccess = false;
            e.printStackTrace();
        }





        if (upLoadSuccess)
        {
            mHandler.sendEmptyMessage(0x124);
        }
        else
        {
            mHandler.sendEmptyMessage(0x125);
        }



    }


}