package com.example.block.tools.dataBaseTools;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.block.MainGames.gameBases.DealCollision;
import com.example.block.MainGames.gameBases.StateType;
import com.example.block.MainGames.logic.LoadGame;
import com.example.block.tools.ArrayAndString;
import com.example.block.tools.ImgTools.BitmapTranString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LoadUnits {

    /*
    从数据库中获取状态信息
     */
    public static StateType GetStateTypefromDB(int Type)
    {
        int type;        //状态码

        int width;       //宽、高

        int [] Relevant;  //相关状态码

        int backImage[];  //背景图片集

        DealCollision dealCollisions[] = new DealCollision[4];   //碰撞结果

        float xSpeed,ySpeed,xA,yA;  //初始速度


        Log.e("ABCD","开始链接");

        try {
            Connection cn = ConDatabase.getConnection();

            String sql = "SELECT * FROM StateType WHERE type = "+Type;       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            if (set.next())
            {

                type = set.getInt("type");
                width = set.getInt("width");
                Relevant = ArrayAndString.StringtoArray(set.getString("relevant"));
                backImage = ArrayAndString.StringtoArray(set.getString("backImage"));

                dealCollisions[0] = ArrayAndString.StringtoDealCollision(set.getString("dealCollisiontop"));

                dealCollisions[1] = ArrayAndString.StringtoDealCollision(set.getString("dealCollisionright"));

                dealCollisions[2] = ArrayAndString.StringtoDealCollision(set.getString("dealCollisionbottom"));

                dealCollisions[3] = ArrayAndString.StringtoDealCollision(set.getString("dealCollisionleft"));

                xSpeed = (float) set.getDouble("xSpeed");

                ySpeed = (float) set.getDouble("ySpeed");

                xA = (float) set.getDouble("xA");

                yA = (float) set.getDouble("yA");

                StateType stateType = new StateType(type,width,Relevant,backImage,dealCollisions,xSpeed,ySpeed,xA,yA);

                return stateType;
            }
            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
    上传状态信息到数据库
     */
    public static void UploadStateType(StateType stateType)
    {

        Log.e("上传","开始上传StateType");

        try {

            Connection cn = ConDatabase.getConnection();
            String sql = "INSERT INTO StateType VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(0 + 1,stateType.Type);
            ps.setInt(1 + 1,stateType.width);
            ps.setString(2 + 1,ArrayAndString.ArraytoString(stateType.Relevant));
            ps.setString(3 + 1,ArrayAndString.ArraytoString(stateType.backImage));

            if (stateType.dealCollisions != null)
            {
                ps.setString(4 + 1,ArrayAndString.DealCollisiontoString(stateType.dealCollisions[0]));
                ps.setString(5 + 1,ArrayAndString.DealCollisiontoString(stateType.dealCollisions[1]));
                ps.setString(6 + 1,ArrayAndString.DealCollisiontoString(stateType.dealCollisions[2]));
                ps.setString(7 + 1,ArrayAndString.DealCollisiontoString(stateType.dealCollisions[0]));
            }
            else
            {
                ps.setString(4 + 1,ArrayAndString.DealCollisiontoString(null));
                ps.setString(5 + 1,ArrayAndString.DealCollisiontoString(null));
                ps.setString(6 + 1,ArrayAndString.DealCollisiontoString(null));
                ps.setString(7 + 1,ArrayAndString.DealCollisiontoString(null));
            }


            ps.setDouble(8 + 1,(double) stateType.xSpeed);
            ps.setDouble(9 + 1,(double) stateType.ySpeed);
            ps.setDouble(10 + 1,(double) stateType.xA);
            ps.setDouble(11 + 1,(double) stateType.yA);

            ps.executeUpdate();

            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap[] getViewsBitmap(int Type)
    {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        try {
            Connection cn = ConDatabase.getConnection();

            String sql = "SELECT * FROM ViewsBitmap WHERE type = "+Type;       //sql 向数据库中插入数据
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet set = ps.executeQuery();
            Log.e("下载",set.getRow() + " ");
            while (set.next())
            {
                Bitmap bitmap = BitmapTranString.stringToBitmap(set.getString("bitmap"));
                bitmaps.add(bitmap);
            }
            if (ps!=null) {
                ps.close();
            }
            if (cn!=null) {
                cn.close();
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (Bitmap[]) bitmaps.toArray(new Bitmap[bitmaps.size()]);
    }

    public static void upLoadViewsBitmap(int Type,Bitmap [] bitmaps)
    {
        Log.e("图片","开始上传");
        if (bitmaps != null)
        {
            for (int i = 0;i<bitmaps.length;i++)
            {
                try {

                    Connection cn = ConDatabase.getConnection();
                    String sql = "INSERT INTO ViewsBitmap VALUES(?,?);";       //sql 向数据库中插入数据
                    PreparedStatement ps = cn.prepareStatement(sql);
                    ps.setInt(1,Type);
                    ps.setString(2,BitmapTranString.bitmapToString(bitmaps[i]));
                    ps.executeUpdate();

                    if (ps!=null) {
                        ps.close();
                    }
                    if (cn!=null) {
                        cn.close();
                    }
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
