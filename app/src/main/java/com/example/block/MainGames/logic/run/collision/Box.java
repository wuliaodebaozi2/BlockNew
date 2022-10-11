package com.example.block.MainGames.logic.run.collision;

import android.graphics.Point;

import com.example.block.MainGames.gameBases.Collision;

import static com.example.block.MainGames.gameBases.PoInfo.TIME_INTERVAL;

public class Box {
    public float x,y,width;
    public float xA,yA,xSpeed,ySpeed;

    public Box(int x, int y, int width, float xA, float yA, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.xA = xA;
        this.yA = yA;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public void move()
    {
        x +=   (int)((xSpeed + xSpeed + xA*(TIME_INTERVAL / 10)) * (TIME_INTERVAL / 20));        //相同时间间隔求 距离

        xSpeed += xA*(TIME_INTERVAL / 10);                                                 //改变速度


        y += (int)((ySpeed + ySpeed + yA*(TIME_INTERVAL / 10)) *(TIME_INTERVAL / 20));

        ySpeed += yA*(TIME_INTERVAL / 10);
    }
    public Point getCore()
    {
        return new Point((int)(x + width / 2),(int)(y + width / 2));
    }

    private float getAbsoluteValue(float f)
    {
        if (f < 0)
        {
            return -f;
        }
        else
        {
            return f;
        }
    }

    public Collision.CollisionDirection checkCollisionDirection(Box box)
    {
        float horizontal = (getAbsoluteValue((getCore().x - box.getCore().x)) - (width / 2 + box.width / 2));

        float vertical = (getAbsoluteValue(getCore().y - box.getCore().y) - (width / 2 + box.width / 2));

       if (horizontal <= 0 && vertical <= 0)
       {
           if (horizontal > vertical)   //水平碰撞
           {
               if (getCore().x < box.getCore().x)  //水平左侧碰撞
                   return  Collision.CollisionDirection.right;
               else
                   return Collision.CollisionDirection.Left;

           }
           else         //垂直碰撞
           {
               if (getCore().y < box.getCore().y)
                   return Collision.CollisionDirection.bottom;
               else
                   return Collision.CollisionDirection.top;
           }
       }
        return null;
    }
}
