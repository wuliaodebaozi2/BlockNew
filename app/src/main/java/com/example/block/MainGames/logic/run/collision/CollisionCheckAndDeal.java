package com.example.block.MainGames.logic.run.collision;

import android.graphics.Point;
import android.util.Log;

import com.example.block.MainGames.gameBases.Collision;
import com.example.block.MainGames.gameBases.CollisionNode;
import com.example.block.MainGames.gameBases.PoInfo;
import com.example.block.MainGames.gameBases.PoinfoNode;

import java.util.ArrayList;

public class CollisionCheckAndDeal {

    //最远需要检测的碰撞距离
    private static int MAXCOLLISIONDISTANCE = 400;


    /*
    allViewsPoinfoNode:所有元素链表的头节点
    marioPoinfoNode:主角元素的节点
     */
    public static void checkCollision(PoinfoNode allViewsPoinfoNode, PoinfoNode marioPoinfoNode)
    {

        int count = 0;
        //主角碰撞检测和处理
        while (marioCollision(marioPoinfoNode,allViewsPoinfoNode))
        {
            Log.e("碰撞"," "+count++);
        }

        //其他元素碰撞检测和处理
        othersCollision(marioPoinfoNode,allViewsPoinfoNode);



//        try {
//            CollisionNode allCo;           //所有的碰撞信息链表集合
//
//            CollisionNode collisionNode;
//
//            PoinfoNode checkPoNo = allViewsPoinfoNode;
//
//            //每次检测都重置碰撞链表
//
//            allCo = new CollisionNode();
//            collisionNode = allCo;
//            collisionNode.PreCoNo = null;
//
//            while (checkPoNo != null)
//            {
//                if (checkPoNo.poInfo.stateType.Type >= 0)         //如果是动态元素则检测碰撞
//                {
//                    checkPpNoCollision(checkPoNo,collisionNode.collisionArrayList,marioPoinfoNode);
//                    collisionNode.NextCoNo = new CollisionNode();
//                    collisionNode = collisionNode.NextCoNo;
//                }
//
//                checkPoNo = checkPoNo.NextPoNo;
//            }
//
//            dealCollision(allCo, allViewsPoinfoNode, marioPoinfoNode);   //碰撞处理
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

    }




    private static boolean marioCollision(PoinfoNode marioPoinfoNode,PoinfoNode allViewsPoinfoNode)
    {
            //主角不在空中
            if (marioPoinfoNode.poInfo.ySpeed == 0 || marioPoinfoNode.poInfo.ySpeed == 80)
            {
                PoinfoNode leftPoNo = marioPoinfoNode.PrePoNo;
                while (leftPoNo != null && marioPoinfoNode.poInfo.position.x - leftPoNo.poInfo.position.x <= MAXCOLLISIONDISTANCE)
                {
                    if (isCollision(marioPoinfoNode,leftPoNo))
                    {
                        switch (getCollisionDirection(marioPoinfoNode,leftPoNo))
                        {
                            case right:
                            case Left:
                                //如果主角速度大于被撞物体速度
                                if (getAbsolutValue(marioPoinfoNode.poInfo.xSpeed) > getAbsolutValue(leftPoNo.poInfo.xSpeed))
                                {
                                    if (marioPoinfoNode.poInfo.xSpeed > 0)
                                    {

//                                        collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,leftPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                        leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.right);
                                        marioPoinfoNode.poInfo.position.x = leftPoNo.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width - 1;
                                    }
                                    else
                                    {
//                                        collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,leftPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                        leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.Left);
                                        marioPoinfoNode.poInfo.position.x = leftPoNo.poInfo.position.x + leftPoNo.poInfo.stateType.width + 1;
                                    }
                                }
                                else
                                {
                                    if (leftPoNo.poInfo.xSpeed > 0)
                                    {
//                                        collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,leftPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                        leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.Left);
                                        marioPoinfoNode.poInfo.position.x = leftPoNo.poInfo.position.x + leftPoNo.poInfo.stateType.width + 1;
                                    }
                                    else if (leftPoNo.poInfo.xSpeed < 0)
                                    {
//                                        collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,leftPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                        leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.right);
                                        marioPoinfoNode.poInfo.position.x = leftPoNo.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width - 1;
                                    }
                                }
                                break;
                            case top:
//                                collision = new Collision(Collision.CollisionDirection.top,marioPoinfoNode,leftPoNo);
//                                dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.top);
                                marioPoinfoNode.poInfo.position.y = leftPoNo.poInfo.position.y + leftPoNo.poInfo.stateType.width + 1;

                                break;
                            case bottom:
//                                collision = new Collision(Collision.CollisionDirection.bottom,marioPoinfoNode,leftPoNo);
//                                dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.bottom);
                                marioPoinfoNode.poInfo.position.y = leftPoNo.poInfo.position.y - marioPoinfoNode.poInfo.stateType.width;
                                break;
                        }
                    }
                    leftPoNo = leftPoNo.PrePoNo;
                }


                PoinfoNode rightPoNo = marioPoinfoNode.NextPoNo;
                while (rightPoNo != null && rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.position.x <= MAXCOLLISIONDISTANCE)
                {
                    if (isCollision(marioPoinfoNode,rightPoNo))
                    {
                        switch (getCollisionDirection(marioPoinfoNode,rightPoNo))
                        {
                            case right:
                            case Left:
                                //如果主角速度大于被撞物体速度
                                if (getAbsolutValue(marioPoinfoNode.poInfo.xSpeed) > getAbsolutValue(rightPoNo.poInfo.xSpeed))
                                {
                                    if (marioPoinfoNode.poInfo.xSpeed > 0)
                                    {
//                                        Collision collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,rightPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                        rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.right);
                                        marioPoinfoNode.poInfo.position.x = rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width - 1;
                                    }
                                    else
                                    {
//                                        Collision collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,rightPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                        rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.Left);
                                        marioPoinfoNode.poInfo.position.x = rightPoNo.poInfo.position.x + rightPoNo.poInfo.stateType.width + 1;

                                    }
                                }
                                else
                                {
                                    if (rightPoNo.poInfo.xSpeed > 0)
                                    {
//                                        Collision collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,rightPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                        rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.Left);
                                        marioPoinfoNode.poInfo.position.x = rightPoNo.poInfo.position.x + rightPoNo.poInfo.stateType.width + 1;
                                    }
                                    else
                                    {
//                                        Collision collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,rightPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                        rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.right);
                                        marioPoinfoNode.poInfo.position.x = rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width -1;
                                    }
                                }
                                break;
                            case top:
//                                Collision collision1 = new Collision(Collision.CollisionDirection.bottom,marioPoinfoNode,rightPoNo);
//                                dealSingleCollision(collision1,allViewsPoinfoNode,marioPoinfoNode);
                                rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.top);
                                marioPoinfoNode.poInfo.position.y = rightPoNo.poInfo.position.y + rightPoNo.poInfo.stateType.width + 1;
                                break;
                            case bottom:
//                                Collision collision = new Collision(Collision.CollisionDirection.bottom,marioPoinfoNode,rightPoNo);
//                                dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.bottom);
                                marioPoinfoNode.poInfo.position.y = rightPoNo.poInfo.position.y - marioPoinfoNode.poInfo.stateType.width - 1;
                                break;
                        }
                    }
                    rightPoNo = rightPoNo.NextPoNo;
                }
//                if (marioPoinfoNode.poInfo.ySpeed == 80)
//                {
//                    marioPoinfoNode.poInfo.ySpeed = 0;
//                    marioPoinfoNode.poInfo.position.y -= 2;
//                }
            }
            else    //主角在空中
            {
                PoinfoNode leftPoNo = marioPoinfoNode.PrePoNo;
                while (leftPoNo != null && marioPoinfoNode.poInfo.position.x - leftPoNo.poInfo.position.x <= MAXCOLLISIONDISTANCE)
                {

                    if (isCollision(marioPoinfoNode,leftPoNo))
                    {
                        switch (getCollisionDirection(marioPoinfoNode,leftPoNo))
                        {
                            case right:
                                if (marioPoinfoNode.poInfo.xSpeed < 0 && marioPoinfoNode.poInfo.lastxSpeed < 0)
                                {
                                    Log.e("碰撞","主角在空中水平右碰撞里的左碰撞 X " + leftPoNo.poInfo.position.x + " Y "+ leftPoNo.poInfo.position.y);
                                    marioDealCollision(marioPoinfoNode,leftPoNo, Collision.CollisionDirection.Left,allViewsPoinfoNode);
                                    break;
                                }
                                Log.e("碰撞","主角在空中水平有碰撞 X " + leftPoNo.poInfo.position.x + " Y "+ leftPoNo.poInfo.position.y);
                                marioDealCollision(marioPoinfoNode,leftPoNo, Collision.CollisionDirection.right,allViewsPoinfoNode);
                                break;
                            case Left://水平碰撞
                                if (marioPoinfoNode.poInfo.xSpeed > 0 && marioPoinfoNode.poInfo.lastxSpeed > 0)
                                {
                                    Log.e("碰撞","主角在空中水平左碰撞里的右碰撞" + marioPoinfoNode.poInfo.xSpeed);
                                    marioDealCollision(marioPoinfoNode,leftPoNo, Collision.CollisionDirection.right,allViewsPoinfoNode);
                                    break;
                                }
                                Log.e("碰撞","主角在空中水平左碰撞" + marioPoinfoNode.poInfo.xSpeed);
                                marioDealCollision(marioPoinfoNode,leftPoNo, Collision.CollisionDirection.Left,allViewsPoinfoNode);
                                break;
                            case top:
                                Log.e("碰撞","主角在空中垂直上碰撞");
                                marioDealCollision(marioPoinfoNode,leftPoNo, Collision.CollisionDirection.top,allViewsPoinfoNode);
                                break;
                            case bottom://垂直碰撞

                                Log.e("碰撞","主角在空中垂直下碰撞 X " + leftPoNo.poInfo.position.x + " Y "+ leftPoNo.poInfo.position.y);
                                marioDealCollision(marioPoinfoNode,leftPoNo, Collision.CollisionDirection.bottom,allViewsPoinfoNode);
//                            {
//
//                                if (marioPoinfoNode.poInfo.ySpeed > 0)  //下碰撞
//                                {
//
//                                }
//                                else   //上碰撞
//                                {
//
//                                }
//                            }
                                break;
                        }




                    }
                    leftPoNo = leftPoNo.PrePoNo;
                }


                PoinfoNode rightPoNo = marioPoinfoNode.NextPoNo;
                while (rightPoNo != null && rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.position.x <= MAXCOLLISIONDISTANCE)
                {
                    if (isCollision(marioPoinfoNode,rightPoNo))
                    {
                        switch (getCollisionDirection(marioPoinfoNode,rightPoNo))
                        {
                            case Left:
                                if (marioPoinfoNode.poInfo.xSpeed > 0 && marioPoinfoNode.poInfo.lastxSpeed > 0)
                                {
                                    Log.e("碰撞","主角在空中水平左碰撞里的右碰撞" + marioPoinfoNode.poInfo.xSpeed);
                                    marioDealCollision(marioPoinfoNode,rightPoNo, Collision.CollisionDirection.right,allViewsPoinfoNode);
                                    break;
                                }
                                Log.e("碰撞","主角在空中水平左碰撞" + marioPoinfoNode.poInfo.xSpeed);
                                marioDealCollision(marioPoinfoNode,rightPoNo, Collision.CollisionDirection.Left,allViewsPoinfoNode);
                                break;
                            case right:
                                if (marioPoinfoNode.poInfo.xSpeed < 0 && marioPoinfoNode.poInfo.lastxSpeed < 0)
                                {
                                    Log.e("碰撞","主角在空中水平右碰撞里的左碰撞 X " + rightPoNo.poInfo.position.x + " Y "+ rightPoNo.poInfo.position.y);
                                    marioDealCollision(marioPoinfoNode,rightPoNo, Collision.CollisionDirection.Left,allViewsPoinfoNode);
                                    break;
                                }
                                Log.e("碰撞","主角在空中水平右碰撞 X " + rightPoNo.poInfo.position.x + " Y "+ rightPoNo.poInfo.position.y);
                                marioDealCollision(marioPoinfoNode,rightPoNo, Collision.CollisionDirection.right,allViewsPoinfoNode);
                                break;
                            case bottom:
                                Log.e("碰撞","主角在空中垂直下碰撞 X " + rightPoNo.poInfo.position.x + " Y "+ rightPoNo.poInfo.position.y);
                                marioDealCollision(marioPoinfoNode,rightPoNo, Collision.CollisionDirection.bottom,allViewsPoinfoNode);
                                break;
                            case top:
                                Log.e("碰撞","主角在空中垂直上碰撞");
                                marioDealCollision(marioPoinfoNode,rightPoNo, Collision.CollisionDirection.top,allViewsPoinfoNode);
                                break;
                        }
                    }
                    rightPoNo = rightPoNo.NextPoNo;
                }
            }


            if (hasCollision(marioPoinfoNode))
            {
                Log.e("还有碰撞1"," ");
                marioCollisionSecond(marioPoinfoNode,allViewsPoinfoNode);
                Log.e("还有碰撞2"," ");
                return true;
            }
            else
            {
                return false;
            }

    }


    /*
    碰撞处理好，水平和垂直方向上的碰撞都不处理，则第二次处理
     */
    private static void marioCollisionSecond(PoinfoNode marioPoinfoNode,PoinfoNode allViewsPoinfoNode)
    {


        //主角不在空中
        if (marioPoinfoNode.poInfo.ySpeed == 0 || marioPoinfoNode.poInfo.ySpeed == 80)
        {
            PoinfoNode leftPoNo = marioPoinfoNode.PrePoNo;
            while (leftPoNo != null && marioPoinfoNode.poInfo.position.x - leftPoNo.poInfo.position.x <= MAXCOLLISIONDISTANCE)
            {
                if (isCollision(marioPoinfoNode,leftPoNo))
                {
                    switch (getCollisionDirection(marioPoinfoNode,leftPoNo))
                    {
                        case right:
                        case Left:
                            //如果主角速度大于被撞物体速度
                            if (getAbsolutValue(marioPoinfoNode.poInfo.xSpeed) > getAbsolutValue(leftPoNo.poInfo.xSpeed))
                            {
                                if (marioPoinfoNode.poInfo.xSpeed > 0)
                                {

//                                        collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,leftPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                    leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.right);
                                    marioPoinfoNode.poInfo.position.x = leftPoNo.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width - 1;
                                }
                                else
                                {
//                                        collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,leftPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                    leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.Left);
                                    marioPoinfoNode.poInfo.position.x = leftPoNo.poInfo.position.x + leftPoNo.poInfo.stateType.width + 1;
                                }
                            }
                            else
                            {
                                if (leftPoNo.poInfo.xSpeed > 0)
                                {
//                                        collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,leftPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                    leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.Left);
                                    marioPoinfoNode.poInfo.position.x = leftPoNo.poInfo.position.x + leftPoNo.poInfo.stateType.width + 1;
                                }
                                else if (leftPoNo.poInfo.xSpeed < 0)
                                {
//                                        collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,leftPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                    leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.right);
                                    marioPoinfoNode.poInfo.position.x = leftPoNo.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width - 1;
                                }
                            }
                            break;
                        case top:
//                                collision = new Collision(Collision.CollisionDirection.top,marioPoinfoNode,leftPoNo);
//                                dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                            leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.top);
                            marioPoinfoNode.poInfo.position.y = leftPoNo.poInfo.position.y + leftPoNo.poInfo.stateType.width + 1;

                            break;
                        case bottom:
//                                collision = new Collision(Collision.CollisionDirection.bottom,marioPoinfoNode,leftPoNo);
//                                dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                            leftPoNo.poInfo.dealCollision(Collision.CollisionDirection.bottom);
                            marioPoinfoNode.poInfo.position.y = leftPoNo.poInfo.position.y - marioPoinfoNode.poInfo.stateType.width;
                            break;
                    }
                }
                leftPoNo = leftPoNo.PrePoNo;
            }


            PoinfoNode rightPoNo = marioPoinfoNode.NextPoNo;
            while (rightPoNo != null && rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.position.x <= MAXCOLLISIONDISTANCE)
            {
                if (isCollision(marioPoinfoNode,rightPoNo))
                {
                    switch (getCollisionDirection(marioPoinfoNode,rightPoNo))
                    {
                        case right:
                        case Left:
                            //如果主角速度大于被撞物体速度
                            if (getAbsolutValue(marioPoinfoNode.poInfo.xSpeed) > getAbsolutValue(rightPoNo.poInfo.xSpeed))
                            {
                                if (marioPoinfoNode.poInfo.xSpeed > 0)
                                {
//                                        Collision collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,rightPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                    rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.right);
                                    marioPoinfoNode.poInfo.position.x = rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width - 1;
                                }
                                else
                                {
//                                        Collision collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,rightPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                    rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.Left);
                                    marioPoinfoNode.poInfo.position.x = rightPoNo.poInfo.position.x + rightPoNo.poInfo.stateType.width + 1;

                                }
                            }
                            else
                            {
                                if (rightPoNo.poInfo.xSpeed > 0)
                                {
//                                        Collision collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,rightPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                    rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.Left);
                                    marioPoinfoNode.poInfo.position.x = rightPoNo.poInfo.position.x + rightPoNo.poInfo.stateType.width + 1;
                                }
                                else
                                {
//                                        Collision collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,rightPoNo);
//                                        dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                                    rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.right);
                                    marioPoinfoNode.poInfo.position.x = rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width -1;
                                }
                            }
                            break;
                        case top:
//                                Collision collision1 = new Collision(Collision.CollisionDirection.bottom,marioPoinfoNode,rightPoNo);
//                                dealSingleCollision(collision1,allViewsPoinfoNode,marioPoinfoNode);
                            rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.top);
                            marioPoinfoNode.poInfo.position.y = rightPoNo.poInfo.position.y + rightPoNo.poInfo.stateType.width + 1;
                            break;
                        case bottom:
//                                Collision collision = new Collision(Collision.CollisionDirection.bottom,marioPoinfoNode,rightPoNo);
//                                dealSingleCollision(collision,allViewsPoinfoNode,marioPoinfoNode);
                            rightPoNo.poInfo.dealCollision(Collision.CollisionDirection.bottom);
                            marioPoinfoNode.poInfo.position.y = rightPoNo.poInfo.position.y - marioPoinfoNode.poInfo.stateType.width - 1;
                            break;
                    }
                }
                rightPoNo = rightPoNo.NextPoNo;
            }
//                if (marioPoinfoNode.poInfo.ySpeed == 80)
//                {
//                    marioPoinfoNode.poInfo.ySpeed = 0;
//                    marioPoinfoNode.poInfo.position.y -= 2;
//                }
        }
        else    //主角在空中
        {
            ArrayList<Collision> collisions = new ArrayList<Collision>();


            Log.e("碰撞","主角在空中");
            PoinfoNode leftPoNo = marioPoinfoNode.PrePoNo;
            while (leftPoNo != null && marioPoinfoNode.poInfo.position.x - leftPoNo.poInfo.position.x <= MAXCOLLISIONDISTANCE)
            {

                if (isCollision(marioPoinfoNode,leftPoNo))
                {
                    Collision collision;
                    switch (getCollisionDirection(marioPoinfoNode,leftPoNo))
                    {
                        case right:
                            Log.e("碰撞","主角在空中水平有碰撞 X " + leftPoNo.poInfo.position.x + " Y "+ leftPoNo.poInfo.position.y);
                            collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,leftPoNo);
                            collisions.add(collision);
                            break;
                        case Left://水平碰撞
                            Log.e("碰撞","主角在空中水平左碰撞" + marioPoinfoNode.poInfo.xSpeed);
                            collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,leftPoNo);
                            collisions.add(collision);
                            break;
                        case top:
                            Log.e("碰撞","主角在空中垂直上碰撞");
                            collision = new Collision(Collision.CollisionDirection.top,marioPoinfoNode,leftPoNo);
                            collisions.add(collision);
                            break;
                        case bottom://垂直碰撞
                            Log.e("碰撞","主角在空中垂直下碰撞 X " + leftPoNo.poInfo.position.x + " Y "+ leftPoNo.poInfo.position.y);
                            collision = new Collision(Collision.CollisionDirection.bottom,marioPoinfoNode,leftPoNo);
                            collisions.add(collision);
                            break;
                    }




                }
                leftPoNo = leftPoNo.PrePoNo;
            }


            PoinfoNode rightPoNo = marioPoinfoNode.NextPoNo;
            while (rightPoNo != null && rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.position.x <= MAXCOLLISIONDISTANCE)
            {
                if (isCollision(marioPoinfoNode,rightPoNo))
                {
                    Collision collision;
                    switch (getCollisionDirection(marioPoinfoNode,rightPoNo))
                    {
                        case Left:
                            Log.e("碰撞","主角在空中水平左碰撞" + marioPoinfoNode.poInfo.xSpeed);
                            collision = new Collision(Collision.CollisionDirection.Left,marioPoinfoNode,rightPoNo);
                            collisions.add(collision);
                            break;
                        case right:
                            Log.e("碰撞","主角在空中水平右碰撞 X " + rightPoNo.poInfo.position.x + " Y "+ rightPoNo.poInfo.position.y);
                            collision = new Collision(Collision.CollisionDirection.right,marioPoinfoNode,rightPoNo);
                            collisions.add(collision);
                            break;
                        case bottom:
                            Log.e("碰撞","主角在空中垂直下碰撞 X " + rightPoNo.poInfo.position.x + " Y "+ rightPoNo.poInfo.position.y);
                            collision = new Collision(Collision.CollisionDirection.bottom,marioPoinfoNode,rightPoNo);
                            collisions.add(collision);
                            break;
                        case top:
                            Log.e("碰撞","主角在空中垂直上碰撞");
                            collision = new Collision(Collision.CollisionDirection.top,marioPoinfoNode,rightPoNo);
                            collisions.add(collision);
                            break;
                    }
                }
                rightPoNo = rightPoNo.NextPoNo;
            }


            if (collisions.size() < 3)
            {
                for (int i=0;i<collisions.size();i++)
                {
                    dealMarioCollisionwithNoCheck(collisions.get(i));
                }
            }
            else
            {
                DealInteractCollision(collisions);
            }



        }
    }


    /*
    找出相互影响对方不让对方处理的一对碰撞
     */
    private static void DealInteractCollision(ArrayList<Collision> collisions)
    {
        Log.e("处理一对碰撞","开始处理一对碰撞");
        for (int i=0;i<collisions.size();i++)
        {
            ArrayList<Integer> indexs = getInteractCollisionIndexs(collisions,i);
            for (int j=0;j<indexs.size();j++)
            {
                if (j == i)
                    continue;
                if (getInteractCollisionIndexs(collisions,j).contains(i))
                {
                    Log.e("处理一对碰撞","处理一对碰撞");
                    dealMarioCollisionwithNoCheck(collisions.get(i));
                    dealMarioCollisionwithNoCheck(collisions.get(j));
                    Log.e("处理一对碰撞", " X " + collisions.get(i).collisedObjectNode.poInfo.position.x + " Y " + collisions.get(i).collisedObjectNode.poInfo.position.y);
                    Log.e("处理一对碰撞", " X " + collisions.get(j).collisedObjectNode.poInfo.position.x + " Y " + collisions.get(j).collisedObjectNode.poInfo.position.y);
                }
            }

        }



    }

    /*
    不检测处理好是否还有碰撞，直接处理主角的碰撞
     */
    private static void dealMarioCollisionwithNoCheck(Collision collision)
    {
        PoinfoNode marioPoinfoNode = collision.collisionObjectNode;
        PoinfoNode collisedObjectNode = collision.collisedObjectNode;
        switch (collision.collisionDirection)
        {
            case Left:
                marioPoinfoNode.poInfo.position.x = collisedObjectNode.poInfo.position.x + collisedObjectNode.poInfo.stateType.width + 1;
                break;
            case bottom:
                marioPoinfoNode.poInfo.position.y = collisedObjectNode.poInfo.position.y - marioPoinfoNode.poInfo.stateType.width - 1;
                marioPoinfoNode.poInfo.ySpeed = 0;
                break;
            case top:
                marioPoinfoNode.poInfo.position.y = collisedObjectNode.poInfo.position.y + collisedObjectNode.poInfo.stateType.width+ 1;
                if (marioPoinfoNode.poInfo.ySpeed < 0)
                    marioPoinfoNode.poInfo.ySpeed = -marioPoinfoNode.poInfo.ySpeed;
                break;
            case right:
                marioPoinfoNode.poInfo.position.x = collisedObjectNode.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width - 1;
                break;

        }
    }

    private static ArrayList<Integer> getInteractCollisionIndexs(ArrayList<Collision> collisions,int index)
    {
        ArrayList<Integer> indexs = new ArrayList<Integer>();
        int old = 0;
        PoinfoNode marioPoinfoNode = collisions.get(0).collisionObjectNode;
        switch (collisions.get(index).collisionDirection)     //处理碰撞
        {
            case Left:
                old = marioPoinfoNode.poInfo.position.x;
                marioPoinfoNode.poInfo.position.x = collisions.get(index).collisedObjectNode.poInfo.position.x + collisions.get(index).collisedObjectNode.poInfo.stateType.width + 1;
                break;
            case bottom:
                old = marioPoinfoNode.poInfo.position.y;
                marioPoinfoNode.poInfo.position.y = collisions.get(index).collisedObjectNode.poInfo.position.y - marioPoinfoNode.poInfo.stateType.width - 1;
                break;
            case top:
                old = marioPoinfoNode.poInfo.position.y;
                marioPoinfoNode.poInfo.position.y = collisions.get(index).collisedObjectNode.poInfo.position.y + collisions.get(index).collisedObjectNode.poInfo.stateType.width+ 1;
                break;
            case right:
                old = marioPoinfoNode.poInfo.position.x;
                marioPoinfoNode.poInfo.position.x = collisions.get(index).collisedObjectNode.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width - 1;
                break;

        }
        for (int i=0;i<collisions.size();i++)
        {
            if (isCollision(marioPoinfoNode,collisions.get(i).collisedObjectNode))
            {
                if (i == index)
                    continue;
                indexs.add(i);
            }
        }
        switch (collisions.get(index).collisionDirection)
        {
            case top:
            case bottom:
                marioPoinfoNode.poInfo.position.y = old;
                break;
            case Left:
            case right:
                marioPoinfoNode.poInfo.position.x = old;
                break;
        }

        return indexs;
    }


    private static boolean hasCollision(PoinfoNode marioPoinfoNode)
    {
        PoinfoNode leftPoNo = marioPoinfoNode.PrePoNo;
        while (leftPoNo != null && marioPoinfoNode.poInfo.position.x - leftPoNo.poInfo.position.x <= MAXCOLLISIONDISTANCE)
        {
            if (isCollision(marioPoinfoNode,leftPoNo))
            {
                return true;
            }
            leftPoNo = leftPoNo.PrePoNo;
        }

        PoinfoNode rightPoNo = marioPoinfoNode.NextPoNo;
        while (rightPoNo != null && rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.position.x <= MAXCOLLISIONDISTANCE)
        {
            if (isCollision(marioPoinfoNode,rightPoNo))
            {
                return true;
            }
            rightPoNo = rightPoNo.NextPoNo;
        }
        return false;
    }


    private static Collision.CollisionDirection getCollisionDirection(PoinfoNode collisionNode,PoinfoNode collisionedNode)
    {
        //Log.e("碰撞","开始处理碰撞");
        Box collisionBox = new Box(
                collisionNode.poInfo.lastposition.x,
                collisionNode.poInfo.lastposition.y,
                collisionNode.poInfo.stateType.width,
                collisionNode.poInfo.lastxA,
                collisionNode.poInfo.lastyA,
                collisionNode.poInfo.lastxSpeed,
                collisionNode.poInfo.lastySpeed
        );
        Box collisionedBox = new Box(
                collisionedNode.poInfo.lastposition.x,
                collisionedNode.poInfo.lastposition.y,
                collisionedNode.poInfo.stateType.width,
                collisionedNode.poInfo.lastxA,
                collisionedNode.poInfo.lastyA,
                collisionedNode.poInfo.lastxSpeed,
                collisionedNode.poInfo.lastySpeed
        );

        for (int i = 0;i < 10;i++)
        {
            collisionBox.move();
            collisionedBox.move();
            Collision.CollisionDirection collisionDirection = collisionBox.checkCollisionDirection(collisionedBox);
            if (collisionDirection != null)
            {
                return collisionDirection;
            }

        }

        //主角在地面或者墙上时，速度为零
        float horizontal = getAbsolutValue(collisionNode.poInfo.getCore().x - collisionedNode.poInfo.getCore().x);
        float vertical = getAbsolutValue(collisionNode.poInfo.getCore().y - collisionedNode.poInfo.getCore().y);
        if (horizontal > vertical)
        {
            if (collisionNode.poInfo.getCore().x < collisionedNode.poInfo.getCore().x)
            {
                return Collision.CollisionDirection.Left;
            }
            else
            {
                return Collision.CollisionDirection.right;
            }

        }
        else
        {
            if (collisionNode.poInfo.getCore().y < collisionedNode.poInfo.getCore().y)
            {
                return Collision.CollisionDirection.bottom;
            }
            else
            {
                return Collision.CollisionDirection.top;
            }

        }

    }

    //主角的碰撞处理
    private static void marioDealCollision(PoinfoNode marioPoinfoNode, PoinfoNode collisedPoinfoNode, Collision.CollisionDirection collisionDirection,PoinfoNode allViewsPoinfoNode)
    {
        int old;
        switch (collisionDirection)
        {
            case Left:
                old = marioPoinfoNode.poInfo.position.x;
                marioPoinfoNode.poInfo.position.x = collisedPoinfoNode.poInfo.position.x + collisedPoinfoNode.poInfo.stateType.width;
                if (!isCollisioninMAXCOLLISIONDISTANCE(marioPoinfoNode))
                {
                    Log.e("碰撞","左碰撞后位置调整成功");
                    //碰撞处理
                    if (collisedPoinfoNode.poInfo.dealCollision(collisionDirection))
                    {
                        //删除被撞物
                        DeleteNode(collisedPoinfoNode, allViewsPoinfoNode);
                    }

                    //dealSingleCollision()
                }
                else
                {
                    Log.e("碰撞","处理取消");
                    marioPoinfoNode.poInfo.position.x = old;
                }
                break;
            case bottom:
                old = marioPoinfoNode.poInfo.position.y;
                marioPoinfoNode.poInfo.position.y = collisedPoinfoNode.poInfo.position.y - marioPoinfoNode.poInfo.stateType.width;
                if (!isCollisioninMAXCOLLISIONDISTANCE(marioPoinfoNode))
                {
                    Log.e("碰撞","下碰撞后位置调整成功");
                    marioPoinfoNode.poInfo.ySpeed = 0;
                    //碰撞处理
                    if (collisedPoinfoNode.poInfo.dealCollision(collisionDirection))
                    {
                        //删除被撞物
                        DeleteNode(collisedPoinfoNode, allViewsPoinfoNode);
                    }

                }
                else
                {
                    Log.e("碰撞","处理取消");
                    marioPoinfoNode.poInfo.position.y = old;
                }

                break;
            case top:
                old = marioPoinfoNode.poInfo.position.y;
                marioPoinfoNode.poInfo.position.y = collisedPoinfoNode.poInfo.position.y + collisedPoinfoNode.poInfo.stateType.width;
                if (!isCollisioninMAXCOLLISIONDISTANCE(marioPoinfoNode))
                {
                    Log.e("碰撞","上碰撞后位置调整成功");
                    if (marioPoinfoNode.poInfo.ySpeed < 0)
                        marioPoinfoNode.poInfo.ySpeed = -marioPoinfoNode.poInfo.ySpeed;

                    //碰撞处理
                    if (collisedPoinfoNode.poInfo.dealCollision(collisionDirection))
                    {
                        //删除被撞物
                        DeleteNode(collisedPoinfoNode, allViewsPoinfoNode);
                    }
                }
                else
                {
                    Log.e("碰撞","处理取消");
                    marioPoinfoNode.poInfo.position.y = old;
                }
                break;
            case right:
                old = marioPoinfoNode.poInfo.position.x;
                marioPoinfoNode.poInfo.position.x = collisedPoinfoNode.poInfo.position.x - marioPoinfoNode.poInfo.stateType.width;
                if (!isCollisioninMAXCOLLISIONDISTANCE(marioPoinfoNode))
                {
                    Log.e("碰撞","右碰撞后位置调整成功");
                    //碰撞处理
                    if (collisedPoinfoNode.poInfo.dealCollision(collisionDirection))
                    {
                        //删除被撞物
                        DeleteNode(collisedPoinfoNode, allViewsPoinfoNode);
                    }
                }
                else
                {
                    Log.e("碰撞","处理取消");
                    marioPoinfoNode.poInfo.position.x = old;
                }
                break;

        }
    }

    /*
    判断碰撞 是水平碰撞还是竖直碰撞
    返回true 水平碰撞
    返回false 垂直碰撞
     */
    private static boolean isHorizontalCollision(PoinfoNode collsionPoinfoNode,PoinfoNode collisionedPoinfoNode)
    {
//        float horizontal = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().x - collisionedPoinfoNode.poInfo.getCore().x) -
//                (collsionPoinfoNode.poInfo.stateType.width / 2 + collisionedPoinfoNode.poInfo.stateType.width / 2);
//        float vertical = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().y - collisionedPoinfoNode.poInfo.getCore().y) -
//                (collsionPoinfoNode.poInfo.stateType.width / 2 + collisionedPoinfoNode.poInfo.stateType.width / 2);
//
//        if (horizontal > vertical)
//            return true;
//        else return false;
        float horizontal = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().x - collisionedPoinfoNode.poInfo.getCore().x);
        float vertical = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().y - collisionedPoinfoNode.poInfo.getCore().y);
        if (horizontal > vertical)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    查看如果对位置进行调整，调整后是否与其他元素碰撞
    返回true 相撞
    返回false 不相撞
     */
    public static boolean isCollisioninMAXCOLLISIONDISTANCE(PoinfoNode marioPoinfoNode)
    {
        PoinfoNode leftPoNo = marioPoinfoNode.PrePoNo;
        while (leftPoNo != null && getAbsolutValue(marioPoinfoNode.poInfo.position.x - leftPoNo.poInfo.position.x) < MAXCOLLISIONDISTANCE)
        {
            if (isCollision(marioPoinfoNode,leftPoNo))
            {
                return true;
            }
            leftPoNo = leftPoNo.PrePoNo;
        }
        PoinfoNode rightPoNo = marioPoinfoNode.NextPoNo;
        while (rightPoNo != null && getAbsolutValue(marioPoinfoNode.poInfo.position.x - rightPoNo.poInfo.position.x) < MAXCOLLISIONDISTANCE)
        {
            if (isCollision(marioPoinfoNode,rightPoNo))
            {
                return true;
            }
            rightPoNo = rightPoNo.NextPoNo;
        }
        return false;
    }
    /*
    检测两个元素节点是否相撞
     */
    private static boolean isCollision(PoinfoNode collsionPoinfoNode,PoinfoNode collisionedPoinfoNode)
    {
        float horizontal = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().x - collisionedPoinfoNode.poInfo.getCore().x) -
                (collsionPoinfoNode.poInfo.stateType.width / 2 + collisionedPoinfoNode.poInfo.stateType.width / 2);
        float vertical = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().y - collisionedPoinfoNode.poInfo.getCore().y) -
                (collsionPoinfoNode.poInfo.stateType.width / 2 + collisionedPoinfoNode.poInfo.stateType.width / 2);
        if (horizontal < 0 && vertical < 0)
            return true;
        else return false;
    }

    //得到一个数的绝对值
    private static float getAbsolutValue(float f)
    {
        if (f>0)
        {
            return f;
        }
        else
            return -f;
    }


    //主角水平碰撞后的处理
    private static void dealSingleCollision(Collision collision,PoinfoNode allViewsPoinfoHeadNode,PoinfoNode marioPoinfoNode)
    {
        switch (collision.collisionObjectNode.poInfo.stateType.Type)
        {
            case 0:                                                                             //碰撞物为mario
                switch (collision.collisionDirection)
                {
                    case top:                                                                   //上碰撞
                        //Log.e("TAG","mario上碰撞");
                        if (( Math.abs((collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.x + collision.collisionObjectNode.poInfo.stateType.width/2))
                                - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < -1)
                        {
                            if (collision.collisionObjectNode.poInfo.ySpeed < 0)             //调整mario竖直方向的速度
                            {
                                collision.collisionObjectNode.poInfo.ySpeed = - collision.collisionObjectNode.poInfo.ySpeed;

                                //碰撞处理
                                collision.collisedObjectNode.poInfo.dealCollision(Collision.CollisionDirection.top);
                            }
                        }


                        break;
                    case right:                                                                 //左、右碰撞
                    case Left:

                        break;
                    case bottom:                                                                //下碰撞
                        //Log.e("TAG","mario下碰撞");
                        if (( Math.abs((collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.x + collision.collisionObjectNode.poInfo.stateType.width/2))
                                - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < -1)
                        {
                            if (collision.collisionObjectNode.poInfo.ySpeed >= 0)
                            {
                                collision.collisionObjectNode.poInfo.ySpeed = 0;       //竖直方向置0
                                //碰撞处理
                                if (collision.collisedObjectNode.poInfo.dealCollision(Collision.CollisionDirection.bottom))
                                {
                                    //删除被撞物
                                    DeleteNode(collision.collisedObjectNode, allViewsPoinfoHeadNode);
                                }
                            }
                        }


                        break;

                }
                break;
            case 1:                                                                             //碰撞物为蘑菇
                switch (collision.collisionDirection)
                {
                    case top:                                                                   //上碰撞
                        break;
                    case right:                                                                 //左、右碰撞
                        if (collision.collisionObjectNode.poInfo.xSpeed > 0)
                        {
                            collision.collisionObjectNode.poInfo.xSpeed = - collision.collisionObjectNode.poInfo.xSpeed;                 //左、右碰撞水平方速度相反
                        }
                        break;
                    case Left:
                        //Log.e("TAG","mario左、右碰撞");
                        if (collision.collisionObjectNode.poInfo.xSpeed < 0)
                        {
                            collision.collisionObjectNode.poInfo.xSpeed = - collision.collisionObjectNode.poInfo.xSpeed;                 //左、右碰撞水平方速度相反
                        }
                        break;
                    case bottom:                                                                //下碰撞
                        //Log.e("TAG","mario下碰撞");
                        if (collision.collisionObjectNode.poInfo.ySpeed > 0)
                            collision.collisionObjectNode.poInfo.ySpeed = -collision.collisionObjectNode.poInfo.ySpeed;                 //竖直方向置0
                        break;

                }
                break;
            case 2:
                switch (collision.collisionDirection)
                {
                    case bottom:                                                                //下碰撞
                        //Log.e("TAG","mario下碰撞");
                        if (collision.collisionObjectNode.poInfo.ySpeed > 0)
                            collision.collisionObjectNode.poInfo.ySpeed = - collision.collisionObjectNode.poInfo.ySpeed;                 //竖直方向置0
                        break;

                }
                break;
            case 3:
                break;
        }


        adjustmentPo(collision, marioPoinfoNode);

    }




    //其他元素的碰撞检测和处理
    private static void othersCollision(PoinfoNode marioPoinfoNode,PoinfoNode allViewsPoinfoNode)
    {
        PoinfoNode leftPoNo = marioPoinfoNode.PrePoNo;
        while (leftPoNo != null && marioPoinfoNode.poInfo.position.x - leftPoNo.poInfo.position.x < 1000)
        {
            //是动态元素
            if(leftPoNo != marioPoinfoNode && leftPoNo.poInfo.stateType.Type > 0)
            {
                otherCollisionCheckAndDeal(leftPoNo,marioPoinfoNode);
            }
            leftPoNo = leftPoNo.PrePoNo;
        }

        PoinfoNode rightPoNo = marioPoinfoNode.NextPoNo;
        while (rightPoNo != null && rightPoNo.poInfo.position.x - marioPoinfoNode.poInfo.position.x < 1000)
        {
            //是动态元素
            if(rightPoNo != marioPoinfoNode && rightPoNo.poInfo.stateType.Type > 0)
            {
                otherCollisionCheckAndDeal(rightPoNo,marioPoinfoNode);
            }
            rightPoNo = rightPoNo.NextPoNo;
        }
    }

    private static void otherCollisionCheckAndDeal(PoinfoNode A,PoinfoNode marioPoinfoNode)
    {
        PoinfoNode leftPoNo = A.PrePoNo;
        while (leftPoNo != null && A.poInfo.position.x - leftPoNo.poInfo.position.x <= MAXCOLLISIONDISTANCE)
        {
            if (leftPoNo != marioPoinfoNode)
            {
                if (isCollision(A,leftPoNo))
                {
                    otherCollisionDeal(A,leftPoNo,marioPoinfoNode);
                }
            }

            leftPoNo = leftPoNo.PrePoNo;
        }

        PoinfoNode rightPoNo = A.NextPoNo;
        while (rightPoNo != null && rightPoNo.poInfo.position.x - A.poInfo.position.x <= MAXCOLLISIONDISTANCE)
        {
            if (rightPoNo != marioPoinfoNode)
            {
                if (isCollision(A,rightPoNo))
                {
                    otherCollisionDeal(A,rightPoNo,marioPoinfoNode);
                }
            }

            rightPoNo = rightPoNo.NextPoNo;
        }
    }


    public static void otherCollisionDeal(PoinfoNode collsionPoinfoNode,PoinfoNode collisionedPoinfoNode,PoinfoNode marioPoinfoNode)
    {
        float horizontal = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().x - collisionedPoinfoNode.poInfo.getCore().x);
        float vertical = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().y - collisionedPoinfoNode.poInfo.getCore().y);

        int old;
       if (horizontal > vertical)   //水平碰撞
       {
           //水平右侧碰撞
           if (collsionPoinfoNode.poInfo.getCore().x < collisionedPoinfoNode.poInfo.getCore().x)
           {
               old = collsionPoinfoNode.poInfo.position.x;
               collsionPoinfoNode.poInfo.position.x = collisionedPoinfoNode.poInfo.position.x - collsionPoinfoNode.poInfo.stateType.width - 1;
               if (otherCanAdjust(collsionPoinfoNode,marioPoinfoNode))
               {
                   if (collsionPoinfoNode.poInfo.xSpeed > 0)
                       collsionPoinfoNode.poInfo.xSpeed = -collsionPoinfoNode.poInfo.xSpeed;
               }
               else
               {
                   collsionPoinfoNode.poInfo.position.x = old;
                   collsionPoinfoNode.poInfo.position.x = collsionPoinfoNode.poInfo.lastposition.x;

               }

           }
           else   //水平左侧碰撞
           {
               old = collsionPoinfoNode.poInfo.position.x;
               collsionPoinfoNode.poInfo.position.x = collisionedPoinfoNode.poInfo.position.x + collisionedPoinfoNode.poInfo.stateType.width + 1;
               if (otherCanAdjust(collsionPoinfoNode,marioPoinfoNode))
               {
                   if (collsionPoinfoNode.poInfo.xSpeed < 0)
                       collsionPoinfoNode.poInfo.xSpeed = -collsionPoinfoNode.poInfo.xSpeed;
               }
               else
               {
                   collsionPoinfoNode.poInfo.position.x = old;
                   collsionPoinfoNode.poInfo.position.x = collsionPoinfoNode.poInfo.lastposition.x;
               }



           }
       }
       else     //垂直碰撞
       {
                if (collsionPoinfoNode.poInfo.getCore().y <= collisionedPoinfoNode.poInfo.getCore().y)    //下碰撞
                {
                    old = collsionPoinfoNode.poInfo.position.y;
                    collsionPoinfoNode.poInfo.position.y = collisionedPoinfoNode.poInfo.position.y - collsionPoinfoNode.poInfo.stateType.width - 1;
                    if (otherCanAdjust(collsionPoinfoNode,marioPoinfoNode))
                    {
                        if (collsionPoinfoNode.poInfo.ySpeed > 0)
                            collsionPoinfoNode.poInfo.ySpeed = -collsionPoinfoNode.poInfo.ySpeed;
                    }
                    else
                    {
                        collsionPoinfoNode.poInfo.position.y = old;
                        collsionPoinfoNode.poInfo.position.y = collsionPoinfoNode.poInfo.lastposition.y;
                    }


                }
                else //上碰撞
                {
                    old = collsionPoinfoNode.poInfo.position.y;
                    collsionPoinfoNode.poInfo.position.y = collisionedPoinfoNode.poInfo.position.y + collisionedPoinfoNode.poInfo.stateType.width + 1;

                    if (otherCanAdjust(collsionPoinfoNode,marioPoinfoNode))
                    {
                        if (collsionPoinfoNode.poInfo.ySpeed < 0)
                        collsionPoinfoNode.poInfo.ySpeed = -collsionPoinfoNode.poInfo.ySpeed;
                    }
                    else
                    {
                        collsionPoinfoNode.poInfo.position.y = old;
                        collsionPoinfoNode.poInfo.position.y = collsionPoinfoNode.poInfo.lastposition.y;
                    }
//
                }
       }
    }




    /*
    如果小怪调整后 到静态元素里面则不可调整
     */
    private static boolean otherCanAdjust(PoinfoNode A,PoinfoNode marioPoinfoNode)
    {
        PoinfoNode left = A.PrePoNo;
        while (left != null && A.poInfo.position.x - left.poInfo.position.x <= MAXCOLLISIONDISTANCE)
        {
            if (left != marioPoinfoNode)
            {
                if (isCollision(A,left))
                {
                    return false;
                }
            }

            left = left.PrePoNo;
        }

        PoinfoNode right = A.NextPoNo;
        while (right != null && right.poInfo.position.x - right.poInfo.position.x <= MAXCOLLISIONDISTANCE)
        {
            if (right != marioPoinfoNode)
            {
                if (isCollision(A,right))
                {
                    return false;
                }
            }
            right = right.NextPoNo;
        }

        return true;
    }




















    /*
   检测单个元素碰撞,将一个动态元素的碰撞放入一个collisionArrayList
    */
    private static void checkPpNoCollision(PoinfoNode p , ArrayList<Collision> collisionArrayList, PoinfoNode marioPoNode)
    {
        try {
            PoinfoNode leftCheckPoNo = p.PrePoNo;
            PoinfoNode rightCheckPoNo = p.NextPoNo;

            //左侧碰撞
            while (leftCheckPoNo != null && (p.poInfo.position.x - leftCheckPoNo.poInfo.position.x) <=300 )
            {
                int  horizontal  =	Math.abs ((p.poInfo.position.x  + p.poInfo.stateType.width/2)  -( leftCheckPoNo.poInfo.position.x + leftCheckPoNo.poInfo.stateType.width/2))
                        - (p.poInfo.stateType.width/2 + leftCheckPoNo.poInfo.stateType.width/2);

                int  vertical    =  Math.abs((p.poInfo.position.y + p.poInfo.stateType.width/2) - (leftCheckPoNo.poInfo.position.y + leftCheckPoNo.poInfo.stateType.width/2))
                        - (p.poInfo.stateType.width/2 + leftCheckPoNo.poInfo.stateType.width/2);

                if (horizontal<=0 && vertical <= 0)       //碰撞
                {
                    Collision collision = null;
                    collision = new Collision(Collision.CollisionDirection.Left,p,leftCheckPoNo);
                    collisionArrayList.add(collision);
                }
                leftCheckPoNo = leftCheckPoNo.PrePoNo;
            }




            //右侧检测
            while (rightCheckPoNo != null && (rightCheckPoNo.poInfo.position.x - p.poInfo.position.x) <=300)
            {
                int  horizontal  =	Math.abs ((p.poInfo.position.x  + p.poInfo.stateType.width/2)  -( rightCheckPoNo.poInfo.position.x + rightCheckPoNo.poInfo.stateType.width/2))
                        - (p.poInfo.stateType.width/2 + rightCheckPoNo.poInfo.stateType.width/2);

                int  vertical    =  Math.abs((p.poInfo.position.y + p.poInfo.stateType.width/2) - (rightCheckPoNo.poInfo.position.y + rightCheckPoNo.poInfo.stateType.width/2))
                        - (p.poInfo.stateType.width/2 + rightCheckPoNo.poInfo.stateType.width/2);

                if (horizontal <=0 && vertical <= 0)       //碰撞
                {
                    Collision collision = null;
                    collision = new Collision(Collision.CollisionDirection.right,p,rightCheckPoNo);
                    collisionArrayList.add(collision);
                }
                rightCheckPoNo = rightCheckPoNo.NextPoNo;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void getMinCollision(ArrayList<Collision> collisionArrayList,PoinfoNode allViewsPoinfoHeadNode,PoinfoNode marioPoinfoNode)
    {
        PoinfoNode collisionPoinfoNode = collisionArrayList.get(0).collisionObjectNode;
        collisionPoinfoNode.poInfo.back();
        for (int i=0;i<collisionArrayList.size();i++)
        {
            collisionArrayList.get(i).collisedObjectNode.poInfo.back();
        }

        for (int i = 0;i<10;i++)
        {
            collisionPoinfoNode.poInfo.moveSlow();
            for (int j = 0;j < collisionArrayList.size();j++)
            {
                collisionArrayList.get(i).collisedObjectNode.poInfo.moveSlow();
                checkPoinfoNodeCollsiionAndDeal(collisionPoinfoNode,collisionArrayList.get(i).collisedObjectNode,allViewsPoinfoHeadNode,marioPoinfoNode);
            }
        }
    }


    private static void checkPoinfoNodeCollsiionAndDeal(PoinfoNode collsionPoinfoNode,PoinfoNode collisionedPoinfoNode,PoinfoNode allViewsPoinfoHeadNode,PoinfoNode marioPoinfoNode)
    {
        float horizontal = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().x - collisionedPoinfoNode.poInfo.getCore().x) -
                (collsionPoinfoNode.poInfo.stateType.width / 2 + collisionedPoinfoNode.poInfo.stateType.width / 2);
        float vertical = getAbsolutValue(collsionPoinfoNode.poInfo.getCore().y - collisionedPoinfoNode.poInfo.getCore().y) -
                (collsionPoinfoNode.poInfo.stateType.width / 2 + collisionedPoinfoNode.poInfo.stateType.width / 2);
        if (horizontal < 0 && vertical < 0)
        {
            if (horizontal > vertical)
            {
                if (collsionPoinfoNode.poInfo.getCore().x < collisionedPoinfoNode.poInfo.getCore().x)  //右碰撞
                {
                    Collision collision = new Collision(Collision.CollisionDirection.right,collsionPoinfoNode,collisionedPoinfoNode);
                    dealSingleCollision(collision,allViewsPoinfoHeadNode,marioPoinfoNode);
                    if(collsionPoinfoNode == marioPoinfoNode)
                    {
                        Log.e("碰撞","右碰撞");
                    }
                }
                else    //左碰撞
                {
                    Collision collision = new Collision(Collision.CollisionDirection.Left,collsionPoinfoNode,collisionedPoinfoNode);
                    dealSingleCollision(collision,allViewsPoinfoHeadNode,marioPoinfoNode);
                    if(collsionPoinfoNode == marioPoinfoNode)
                    {
                        Log.e("碰撞","左碰撞");
                    }
                }
            }
            else
            {
                if (collsionPoinfoNode.poInfo.getCore().y > collisionedPoinfoNode.poInfo.getCore().y)    //上碰撞
                {
                    Collision collision = new Collision(Collision.CollisionDirection.top,collsionPoinfoNode,collisionedPoinfoNode);
                    dealSingleCollision(collision,allViewsPoinfoHeadNode,marioPoinfoNode);
                    if(collsionPoinfoNode == marioPoinfoNode)
                    {
                        Log.e("碰撞","上碰撞");
                    }
                }
                else //下碰撞
                {
                    Collision collision = new Collision(Collision.CollisionDirection.bottom,collsionPoinfoNode,collisionedPoinfoNode);
                    dealSingleCollision(collision,allViewsPoinfoHeadNode,marioPoinfoNode);
                    if(collsionPoinfoNode == marioPoinfoNode)
                    {
                        Log.e("碰撞","下碰撞");
                    }
                }

            }
        }


    }



    public static void dealCollision(CollisionNode allCo, PoinfoNode allViewsPoinfoHeadNode, PoinfoNode marioPoinfoNode)
    {
        //先处理上下碰撞
        CollisionNode dealCo = allCo;
        try {
            //遍历所有的动态元素碰撞
            while (dealCo != null)
            {
                getMinCollision(dealCo.collisionArrayList,allViewsPoinfoHeadNode,marioPoinfoNode);
//                switch (collision.collisionObjectNode.poInfo.stateType.Type)
//                {
//                    case 0:                                                                             //碰撞物为mario
//                        switch (collision.collisionDirection)
//                        {
//                            case top:                                                                   //上碰撞
//                                //Log.e("TAG","mario上碰撞");
//                                if (( Math.abs((collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.x + collision.collisionObjectNode.poInfo.stateType.width/2))
//                                        - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < -1)
//                                {
//                                    if (collision.collisionObjectNode.poInfo.ySpeed < 0)             //调整mario竖直方向的速度
//                                    {
//                                        collision.collisionObjectNode.poInfo.ySpeed = - collision.collisionObjectNode.poInfo.ySpeed;
//
//                                        //碰撞处理
//                                        collision.collisedObjectNode.poInfo.dealCollision(com.example.block.MainGames.gameBases.Collision.CollisionDirection.top);
//                                    }
//                                }
//
//
//                                break;
//                            case right:                                                                 //左、右碰撞
//                            case Left:
//
//                                break;
//                            case bottom:                                                                //下碰撞
//                                //Log.e("TAG","mario下碰撞");
//                                if (( Math.abs((collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.x + collision.collisionObjectNode.poInfo.stateType.width/2))
//                                        - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < -1)
//                                {
//                                    if (collision.collisionObjectNode.poInfo.ySpeed >= 0)
//                                    {
//                                        collision.collisionObjectNode.poInfo.ySpeed = 0;       //竖直方向置0
//                                        //碰撞处理
//                                        if (collision.collisedObjectNode.poInfo.dealCollision(com.example.block.MainGames.gameBases.Collision.CollisionDirection.bottom))
//                                        {
//                                            //删除被撞物
//                                            DeleteNode(collision.collisedObjectNode, allViewsPoinfoHeadNode);
//                                        }
//                                    }
//                                }
//
//
//                                break;
//
//                        }
//                        break;
//                    case 1:                                                                             //碰撞物为蘑菇
//                        switch (collision.collisionDirection)
//                        {
//                            case top:                                                                   //上碰撞
//                                break;
//                            case right:                                                                 //左、右碰撞
//                                if (collision.collisionObjectNode.poInfo.xSpeed > 0)
//                                {
//                                    collision.collisionObjectNode.poInfo.xSpeed = - collision.collisionObjectNode.poInfo.xSpeed;                 //左、右碰撞水平方速度相反
//                                }
//                                break;
//                            case Left:
//                                //Log.e("TAG","mario左、右碰撞");
//                                if (collision.collisionObjectNode.poInfo.xSpeed < 0)
//                                {
//                                    collision.collisionObjectNode.poInfo.xSpeed = - collision.collisionObjectNode.poInfo.xSpeed;                 //左、右碰撞水平方速度相反
//                                }
//                                break;
//                            case bottom:                                                                //下碰撞
//                                //Log.e("TAG","mario下碰撞");
//                                if (collision.collisionObjectNode.poInfo.ySpeed > 0)
//                                    collision.collisionObjectNode.poInfo.ySpeed = -collision.collisionObjectNode.poInfo.ySpeed;                 //竖直方向置0
//                                break;
//
//                        }
//                        break;
//                    case 2:
//                        switch (collision.collisionDirection)
//                        {
//                            case bottom:                                                                //下碰撞
//                                //Log.e("TAG","mario下碰撞");
//                                if (collision.collisionObjectNode.poInfo.ySpeed > 0)
//                                    collision.collisionObjectNode.poInfo.ySpeed = - collision.collisionObjectNode.poInfo.ySpeed;                 //竖直方向置0
//                                break;
//
//                        }
//                        break;
//                    case 3:
//                        break;
//                }
//
//
//                adjustmentPo(collision, marioPoinfoNode);

                dealCo = dealCo.NextCoNo;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    /*
   获取两点间距离的平方
    */
    private static float getDistance(Point a, Point b)
    {
        return (a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y);
    }

    /*
   删除链表中某个节点
    */
    private static void DeleteNode(PoinfoNode poinfoNode,PoinfoNode allView)
    {
        PoinfoNode PrePoNo = poinfoNode.PrePoNo;
        PoinfoNode NextPoNo = poinfoNode.NextPoNo;
        //判断是否是第一个节点
        if (PrePoNo == null)
        {
            NextPoNo.PrePoNo  = null;
            allView = poinfoNode;
        }
        else  if (NextPoNo == null)    //判断是否是最后一个节点
        {
            PrePoNo.NextPoNo = null;
        }
        else                          //既不是第一个节点又不是最后一个节点
        {
            PrePoNo.NextPoNo = NextPoNo;
            NextPoNo.PrePoNo = PrePoNo;
        }
    }

    /*
   碰撞后碰撞物位置的调整
    */
    private static void adjustmentPo(Collision collision, PoinfoNode mario)
    {
        if (collision.collisedObjectNode.poInfo.stateType.Type != 0)
        {
            switch (collision.collisionDirection)
            {
                case top:          //碰撞物的位置y = 被撞物y + 被撞物width
                    if (( Math.abs((collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.x + collision.collisionObjectNode.poInfo.stateType.width/2))
                            - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < -1)
                    {
                        collision.collisionObjectNode.poInfo.position.y = collision.collisedObjectNode.poInfo.position.y + collision.collisedObjectNode.poInfo.stateType.width;
                    }

                    break;
                case bottom:      //碰撞物的位置y = 被撞物y - 碰撞物width
                    //Log.e("TAG","下碰撞位置调整" + collision.collisionObjectNode.poInfo.position.y);
                    //collision.collisionObjectNode.poInfo.position.y = 0;
                    //减2的目的是为了 使mario与物体一直碰撞 以便跳跃时检测时 y方向速度为0 可以跳起
                    if (( Math.abs((collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.x + collision.collisionObjectNode.poInfo.stateType.width/2))
                            - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < -1)
                    {
                        collision.collisionObjectNode.poInfo.position.y = collision.collisedObjectNode.poInfo.position.y - collision.collisionObjectNode.poInfo.stateType.width;
                    }

                    break;
                case Left:       //碰撞物的位置x =  被撞物x +被撞物width

                    //if语句真对既有上下碰撞 又有左右碰撞的情况 ，即处理完上下碰撞后 不需要再处理左右碰撞
                    //小于0的目的是为了上下碰撞调整位置后不再处理左右碰撞，又可以使mario跳起
                    if (( Math.abs((collision.collisedObjectNode.poInfo.position.y + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.y + collision.collisionObjectNode.poInfo.stateType.width/2))
                            - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < 0)
                    {
                        //如果是mario则，判断是否是跳跃之后踩在物体上，如果是则不需要左右调整
                        if (collision.collisionObjectNode != mario || collision.collisionObjectNode.poInfo.ySpeed < 600)
                            collision.collisionObjectNode.poInfo.position.x = collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width + 1;
                    }

                    break;
                case right:    //碰撞物的位置x = 被撞物x -碰撞物width
                    //if语句真对既有上下碰撞 又有左右碰撞的情况 ，即处理完上下碰撞后 不需要再处理左右碰撞
                    if (( Math.abs((collision.collisedObjectNode.poInfo.position.y + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.y + collision.collisionObjectNode.poInfo.stateType.width/2))
                            - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < 0)
                    {
                        //如果是mario则，判断是否是跳跃之后踩在物体上，如果是则不需要左右调整
                        if (collision.collisionObjectNode != mario || collision.collisionObjectNode.poInfo.ySpeed < 600)
                            collision.collisionObjectNode.poInfo.position.x = collision.collisedObjectNode.poInfo.position.x - collision.collisionObjectNode.poInfo.stateType.width - 1;
                    }

                    break;
            }
        }

    }


//    /*
//    获取碰撞方向
//     */
//    private static CollisonDirectionAndTime getCollisionDirection(PoinfoNode collisionNode,PoinfoNode collisionedNode,PoinfoNode marioPoNo)
//    {
//        //Log.e("碰撞","开始处理碰撞");
//       Box collisionBox = new Box(
//               collisionNode.poInfo.lastposition.x,
//               collisionNode.poInfo.lastposition.y,
//               collisionNode.poInfo.stateType.width,
//               collisionNode.poInfo.lastxA,
//               collisionNode.poInfo.lastyA,
//               collisionNode.poInfo.lastxSpeed,
//               collisionNode.poInfo.lastySpeed
//       );
//       Box collisionedBox = new Box(
//               collisionedNode.poInfo.lastposition.x,
//               collisionedNode.poInfo.lastposition.y,
//               collisionedNode.poInfo.stateType.width,
//               collisionedNode.poInfo.lastxA,
//               collisionedNode.poInfo.lastyA,
//               collisionedNode.poInfo.lastxSpeed,
//               collisionedNode.poInfo.lastySpeed
//       );
//
//       for (int i=0;i<10;i++)
//       {
//           collisionBox.move();
//           collisionedBox.move();
//           Collision.CollisionDirection collisionDirection = collisionBox.checkCollisionDirection(collisionedBox);
//
//           if (collisionDirection != null)
//           {
//               float distance = getDistance(collisionBox.getCore(),collisionedBox.getCore());
//               //if (collisionNode == marioPoNo)
//               //Log.e("碰撞",collisionDirection + "  ");
//               return new CollisonDirectionAndTime(collisionDirection,i,new Point((int)collisionBox.x,(int)collisionBox.y)
//               ,collisionBox.xSpeed,collisionBox.ySpeed,collisionBox.xA,collisionBox.yA,distance);
//           }
//       }
//
//
//        return null;
//    }
}
