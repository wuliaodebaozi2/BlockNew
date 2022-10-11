package com.example.block.MainGames.gameBases;

import android.graphics.Point;
import android.util.Log;

import com.example.block.MainGames.logic.Logic;

import java.util.ArrayList;

/*
元素位置信息类
 */
public class PoInfo {

    public static double TIME_INTERVAL = 0.05;


    public Point position;   //元素位置

    public float xSpeed,ySpeed,xA,yA;  //速度


    public Point lastposition;   //元素位置

    public float lastxSpeed,lastySpeed,lastxA,lastyA;  //速度

     public StateType stateType;        //状态




    public static ArrayList<StateType> stateTypes;

    public static StateType getStateType(int Type)
    {
        for (int i=0;i < stateTypes.size();i++)
        {
            if (stateTypes.get(i).Type == Type)
            {
                return stateTypes.get(i);
            }
        }
        return null;
    }

    public PoInfo(Point position, StateType stateType) {
        this.position = position;
        this.xSpeed = stateType.xSpeed;
        this.ySpeed = stateType.ySpeed;
        this.xA = stateType.xA;
        this.yA = stateType.yA;
        this.stateType = stateType;
        lastposition = new Point(position.x,position.y);
    }


    public int index = 0,count = 0;


    public Point getCore()
    {
        return new Point(position.x + stateType.width/2,position.y + stateType.width/2);
    }

    /*
    被主角碰撞处理
     */
     public boolean dealCollision(Collision.CollisionDirection direction)
     {
         if (stateType.dealCollisions != null)      //如果有碰撞处理
         {
             switch (direction)
             {
                 case top:                   //上碰撞
                     return dealEach(stateType.dealCollisions[0]);
                 case right:
                     return dealEach(stateType.dealCollisions[1]);
                 case bottom:
                     return dealEach(stateType.dealCollisions[2]);
                 case Left:
                     return dealEach(stateType.dealCollisions[3]);
             }
         }

         return  false;
     }

     /*
     处理单个方向的碰撞
      */
     private boolean dealEach(DealCollision dealCollision)
     {
         if (dealCollision != null)   //如果有碰撞处理
         {
             if (dealCollision.Gone)
             {
                 return  true;
             }
             else
             {
                 if (dealCollision.Type != 0)   //需要改变状态
                 {
                     Log.e("ABC","需改变状态" + dealCollision.Type);
                     StateType stateTypetemp = getStateType(dealCollision.Type);
                     if (stateTypetemp != null)
                     {
                         stateType = stateTypetemp;
                     }
                     else
                     {
                         Log.e("ABC","为找到状态码" + dealCollision.Type);
                     }

                 }


                 marioSpeedChange(dealCollision.MarioSpeedChange);  //对mario速度的改变



                 selfSpeedChange(dealCollision.SelfSpeedChange);   //对自身速度的改变

             }
         }

         return false;
     }
     private void marioSpeedChange(SpeedChange speedChange)
     {
         if (speedChange != null)  //对mario速度的改变
         {
             if (speedChange.xSpeedG0)
             {
                 if (Logic.marioPo.xSpeed < 0)
                 {
                     Logic.marioPo.xSpeed = -Logic.marioPo.xSpeed;
                 }
             }
             if (speedChange.xSpeedE0)
             {
                 Logic.marioPo.xSpeed = 0;
             }
             if (speedChange.xSpeedL0)
             {
                 if (Logic.marioPo.xSpeed > 0)
                 {
                     Logic.marioPo.xSpeed = - Logic.marioPo.xSpeed;
                 }
             }
             Logic.marioPo.xSpeed += speedChange.xSpeedAdd;


             if (speedChange.ySpeedG0)
             {
                 if (Logic.marioPo.ySpeed < 0)
                 {
                     Logic.marioPo.ySpeed = -Logic.marioPo.ySpeed;
                 }
             }
             if (speedChange.ySpeedE0)
             {
                 Logic.marioPo.ySpeed = 0;
             }
             if (speedChange.ySpeedL0)
             {
                 if (Logic.marioPo.ySpeed > 0)
                 {
                     Logic.marioPo.ySpeed = - Logic.marioPo.ySpeed;
                 }
             }
             Logic.marioPo.ySpeed += speedChange.ySpeedAdd;


             if (speedChange.xAG0)
             {
                 if (Logic.marioPo.xA < 0)
                 {
                     Logic.marioPo.xA = -Logic.marioPo.xA;
                 }
             }
             if (speedChange.xAE0)
             {
                 Logic.marioPo.xA = 0;
             }
             if (speedChange.xAL0)
             {
                 if (Logic.marioPo.xA > 0)
                 {
                     Logic.marioPo.xA = - Logic.marioPo.xA;
                 }
             }
             Logic.marioPo.xA += speedChange.xAAdd;



             if (speedChange.yAG0)
             {
                 if (Logic.marioPo.yA < 0)
                 {
                     Logic.marioPo.yA = -Logic.marioPo.yA;
                 }
             }
             if (speedChange.yAE0)
             {
                 Logic.marioPo.yA = 0;
             }
             if (speedChange.yAL0)
             {
                 if (Logic.marioPo.yA > 0)
                 {
                     Logic.marioPo.yA = - Logic.marioPo.yA;
                 }
             }
             Logic.marioPo.yA += speedChange.yAAdd;
         }
     }

     private void selfSpeedChange(SpeedChange speedChange)
     {
         if (speedChange != null)  //对自身速度的改变
         {
             if (speedChange.xSpeedG0)
             {
                 if (xSpeed < 0)
                 {
                     xSpeed = -xSpeed;
                 }
             }
             if (speedChange.xSpeedE0)
             {
                 xSpeed = 0;
             }
             if (speedChange.xSpeedL0)
             {
                 if (xSpeed > 0)
                 {
                     xSpeed = - xSpeed;
                 }
             }
             xSpeed += speedChange.xSpeedAdd;


             if (speedChange.ySpeedG0)
             {
                 if (ySpeed < 0)
                 {
                     ySpeed = -ySpeed;
                 }
             }
             if (speedChange.ySpeedE0)
             {
                 ySpeed = 0;
             }
             if (speedChange.ySpeedL0)
             {
                 if (ySpeed > 0)
                 {
                     ySpeed = - ySpeed;
                 }
             }
             ySpeed += speedChange.ySpeedAdd;


             if (speedChange.xAG0)
             {
                 if (xA < 0)
                 {
                     xA = -xA;
                 }
             }
             if (speedChange.xAE0)
             {
                 xA = 0;
             }
             if (speedChange.xAL0)
             {
                 if (xA > 0)
                 {
                     xA = - xA;
                 }
             }
             xA += speedChange.xAAdd;



             if (speedChange.yAG0)
             {
                 if (yA < 0)
                 {
                     yA = -yA;
                 }
             }
             if (speedChange.yAE0)
             {
                 yA = 0;
             }
             if (speedChange.yAL0)
             {
                 if (yA > 0)
                 {
                     yA = - yA;
                 }
             }
             yA += speedChange.yAAdd;
         }
     }
     /*
     移动
      */
     public void move()
     {
         //将原先的速度和位置保存
         lastposition.x = position.x;
         lastposition.y = position.y;
         lastxA = xA;
         lastyA = yA;
         lastxSpeed = xSpeed;
         lastySpeed = ySpeed;




         position.x +=   (int)((xSpeed + xSpeed + xA*TIME_INTERVAL) * (TIME_INTERVAL / 2));        //相同时间间隔求 距离

         xSpeed += xA*TIME_INTERVAL;                                                 //改变速度


         position.y += (int)((ySpeed + ySpeed + yA*TIME_INTERVAL) *(TIME_INTERVAL / 2));

         ySpeed += yA*TIME_INTERVAL;


      //   重力太低 少两个像素防止跳不起 解决因重力太大 竖直掉落 穿越物体
//         for (int i=0;i<10;i++)
//         {
//             moveSlow();
//         }
         if (position.y + stateType.width > 650)
         {
             ySpeed = 0;
             position.y = 650 - stateType.width;
         }

//        /*
//        移动时元素图片的更换
//         */
         if (count % 10 == 0)
         {

             index = stateType.backImage[((count / 10) % 10) % stateType.backImage.length];

         }
         count++;
     }

     public void back()
     {
         position.x = lastposition.x;
         position.y = lastposition.y;
         xA = lastxA;
         yA = lastyA;
         xSpeed = lastxSpeed;
         ySpeed = lastySpeed;
     }
     public void moveSlow()
     {
         //将原先的速度和位置保存
         lastposition.x = position.x;
         lastposition.y = position.y;
         lastxA = xA;
         lastyA = yA;
         lastxSpeed = xSpeed;
         lastySpeed = ySpeed;




         position.x +=   (int)((xSpeed + xSpeed + xA*(TIME_INTERVAL / 10)) * (TIME_INTERVAL / 20));        //相同时间间隔求 距离

         xSpeed += xA*(TIME_INTERVAL / 10);                                                 //改变速度


         position.y += (int)((ySpeed + ySpeed + yA*(TIME_INTERVAL / 10)) *(TIME_INTERVAL / 20));

         ySpeed += yA*(TIME_INTERVAL / 10);


         //Log.e("TAG","YA" + yA);
         //重力太低 少两个像素防止跳不起 解决因重力太大 竖直掉落 穿越物体
         if (position.y + stateType.width > 650)
         {
             ySpeed = 0;
             position.y = 650 - stateType.width;
         }
     }




}
