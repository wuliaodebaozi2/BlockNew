package com.example.block.designCheckpoint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.block.MainGames.gameBases.CollisionNode;
import com.example.block.MainGames.gameBases.DealCollision;
import com.example.block.MainGames.gameBases.PoInfo;
import com.example.block.MainGames.gameBases.PoinfoNode;
import com.example.block.MainGames.gameBases.StateType;
import com.example.block.tools.dataBaseTools.ViewposInfos;


public class DesignCheckpointLogic {
    public static PoinfoNode allView;            //所有元素的链表集合

    private PoinfoNode mario;             //绘制时确定元素相对位置

    private CollisionNode allCo;           //所有的碰撞信息链表集合

    private CollisionNode collisionNode;

    private Bitmap bitmap;

    private Canvas canvas;

    public static PoInfo marioPo;

    public DesignViews[] views;

    public static PoinfoNode marioPoNo;



    public DesignCheckpointLogic()
    {
        marioPo = new PoInfo(new Point(200,-100),new StateType(0,50,null,new int[]{0},null,0,0,0,0));
        allView = new PoinfoNode(marioPo);

        marioPoNo = allView;

        allView.PrePoNo = null;

        mario = allView;





        bitmap = Bitmap.createBitmap(1280,720,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        views = new DesignViews[100];

        views[60] = new DesignViews(0,50);
        views[59] = new DesignViews(-1,50);
        views[58] = new DesignViews(-2,50);
        views[57] = new DesignViews(-3,100);
        views[56] = new DesignViews(-4,100);
        views[61] = new DesignViews(1,50);


        background = new DesignBackground();


    }






    /*
    检测所有元素碰撞
     */
    public void checkCollision()
    {
        PoinfoNode checkPoNo = allView;

        //每次检测都重置碰撞链表

        allCo = new CollisionNode();
        collisionNode = allCo;
        collisionNode.PreCoNo = null;

        //Log.e("COL","开始");
        while (checkPoNo != null)
        {
            //Log.e("COL",checkPoNo.poInfo.position.x+"");
            checkPpNoCollision(checkPoNo);
            collisionNode.NextCoNo = new CollisionNode();
            collisionNode = collisionNode.NextCoNo;

            checkPoNo = checkPoNo.NextPoNo;


        }
        //Log.e("COL","结束");
    }


    /*
    检测单个元素碰撞，并调整位置
     */
    private void checkPpNoCollision(PoinfoNode p)
    {
        PoinfoNode all = allView;

        while (all != null)
        {
            //是自身直接跳过
            if (all == p)
            {
                all = all.NextPoNo;
                continue;
            }
            int horizontal = Math.abs(all.poInfo.getCore().x - p.poInfo.getCore().x) - (all.poInfo.stateType.width/2 + p.poInfo.stateType.width/2);

            int vertical = Math.abs(all.poInfo.getCore().y - p.poInfo.getCore().y)  - (all.poInfo.stateType.width/2 + p.poInfo.stateType.width/2);

            if (horizontal<0 && vertical < 0)       //碰撞
            {
                if (horizontal > vertical)      //水平碰撞
                {
                    if (p == poinfoNode)
                    {
                        //Log.e("COL","水平碰撞");
                        if (p.poInfo.getCore().x < all.poInfo.getCore().x)
                        {
                            p.poInfo.position.x -= 50;
                        }
                        else
                        {
                            p.poInfo.position.x += 50;
                        }
                    }

                }
                else                          //垂直碰撞
                {
                    if (p == poinfoNode)
                    {
                        //Log.e("COL","垂直碰撞");
                        if (p.poInfo.getCore().y < all.poInfo.getCore().y)
                        {
                            p.poInfo.position.y -= 50;
                        }
                        else
                        {
                            p.poInfo.position.y += 50;
                        }
                    }

                }
            }
            all = all.NextPoNo;
        }

    }









    /*
    绘图
     */
    public Bitmap draw()
    {

        //background.draw(canvas,0,0,0);

        background.draw(canvas, -((int)(mario.poInfo.position.x*5 / 10)  % 1280) ,0,0);

        drawGrid();

        PoinfoNode drawViews = mario;

        //mario左侧绘图  只绘制在屏幕上的元素
        while (drawViews != null&&(drawViews.poInfo.position.x + drawViews.poInfo.stateType.width - mario.poInfo.position.x) + 575 >= 0)
        {
            drawViews(drawViews);
            drawViews = drawViews.PrePoNo;
        }

        //mario右侧侧绘图  只绘制在屏幕上的元素
        drawViews = mario.NextPoNo;
        while (drawViews != null&&(drawViews.poInfo.position.x - mario.poInfo.position.x) + 575 <= 1280)
        {
            drawViews(drawViews);
            drawViews = drawViews.NextPoNo;
        }

        return bitmap;
    }

    public void drawGrid()
    {
        Paint p = new Paint();
        //p.setColor(Color.RED);// 设置红色
        //p.setARGB(255,181,230,29);
        p.setColor(Color.WHITE);
        for (int i=0;i<13;i++)
        {
            canvas.drawLine(0,(i * 50),1280,(i * 50),p);
        }
        for (int i =0;i<25;i++)
        {
            canvas.drawLine(25 + (50 * i),0,25 + (50 * i),650,p);
        }

    }

    /*
       根据元素种类绘图
     */
    private void drawViews(PoinfoNode drawViews)
    {


        if (drawViews.poInfo.stateType.Type != 0)
        {
            //Log.e("CH",drawViews.poInfo.stateType.Type+"");
            views[drawViews.poInfo.stateType.Type + 60].draw(canvas,(drawViews.poInfo.position.x - mario.poInfo.position.x) + 575, drawViews.poInfo.position.y,
                    drawViews.poInfo.index);
            //Log.e("COL","绘图位置"+drawViews.poInfo.position.x+"");
        }


    }

    /*
    删除链表中某个节点
     */
    public static void DeleteNode(PoinfoNode poinfoNode)
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


    public static PoinfoNode poinfoNode;
    public static void AddNode()
    {

        if (allView.NextPoNo == null)
        {
            allView.NextPoNo = poinfoNode;
            allView.NextPoNo.PrePoNo = allView;
        }
        else
        {
            poinfoNode.NextPoNo = allView.NextPoNo;
            allView.NextPoNo.PrePoNo = poinfoNode;

            allView.NextPoNo = poinfoNode;
            poinfoNode.PrePoNo = allView;
        }
    }


    public void loadPoinfos(ViewposInfos viewposInfos)
    {
        int [][] viewPos = viewposInfos.getViewPos();

        PoInfo poInfos;
        for (int i=0;i<viewposInfos.getLength();i++)
        {
            switch (viewPos[i][0])
            {
                case 1:

                    poInfos = new PoInfo(new Point(viewPos[i][1],viewPos[i][2]),new StateType(1,50,null,new int[]{0},
                            new DealCollision[]{
                                    new DealCollision(false,0,0,true,null,null),
                                    new DealCollision(false,0,0,true,null,null),
                                    new DealCollision(true,0,0,false,null,null),
                                    new DealCollision(false,0,0,true,null,null)
                            },-30,0,0,200));
                    poinfoNode = new PoinfoNode(poInfos);
                    AddNode();
                    break;
                case 2:
                    poInfos = new PoInfo(new Point(viewPos[i][1],viewPos[i][2]),new StateType(2,50,null,new int[]{0},
                            new DealCollision[]{
                                    new DealCollision(false,0,0,true,null,null),
                                    new DealCollision(false,0,0,true,null,null),
                                    new DealCollision(false,0,0,false,
                                            null,null),
                                    new DealCollision(false,0,0,true,null,null)
                            },0,0,0,200));
                    poinfoNode = new PoinfoNode(poInfos);
                    AddNode();
                    break;
                case -1:
                    poInfos = new PoInfo(new Point(viewPos[i][1],viewPos[i][2]),new StateType(-1,50,null,new int[]{0},
                            null,0,0,0,0));
                    poinfoNode = new PoinfoNode(poInfos);
                    AddNode();
                    break;
                case -2:
                    poInfos = new PoInfo(new Point(viewPos[i][1],viewPos[i][2]),new StateType(-2,50,new int[]{-5},new int[]{0},
                            new DealCollision[]{
                                    new DealCollision(false,-5,1,false,null,null),
                                    new DealCollision(false,0,0,false,null,null),
                                    new DealCollision(false,0,0,false,null,null),
                                    new DealCollision(false,0,0,false,null,null)
                            },0,0,0,0));
                    poinfoNode = new PoinfoNode(poInfos);
                    AddNode();
                    break;
                case -3:
                    poInfos = new PoInfo(new Point(viewPos[i][1],viewPos[i][2]),new StateType(-3,100,null,new int[]{0},
                            new DealCollision[]{
                                    new DealCollision(false,0,0,true,null,null),
                                    new DealCollision(false,0,0,true,null,null),
                                    new DealCollision(false,0,0,true,null,null),
                                    new DealCollision(false,0,0,true,null,null)
                            },0,0,0,0));
                    poinfoNode = new PoinfoNode(poInfos);
                    AddNode();
                    break;
                case -4:
                    poInfos = new PoInfo(new Point(viewPos[i][1],viewPos[i][2]),new StateType(-4,100,null,new int[]{0},
                            null,0,200,0,0));
                    poinfoNode = new PoinfoNode(poInfos);
                    AddNode();
                    break;
            }
        }
        poinfoNode = marioPoNo.NextPoNo;
    }


    private static DesignBackground background;

    public int[][] export()
    {
        //order();
        PoinfoNode all = allView;
        int length = 0;
        while (all != null)
        {
            length++;
            //Log.e("COL",all.poInfo.position.x + "");
            all = all.NextPoNo;
        }
        int [][] Checkpoint;
        Checkpoint = new int[length - 1][3];
        all = allView;
        int i = 0;
        while (all != null)
        {
            if (all.poInfo.stateType.Type!= 0)   //去除Mario
            {
                Checkpoint[i][0] = all.poInfo.stateType.Type;
                Checkpoint[i][1] = all.poInfo.position.x;
                Checkpoint[i][2] = all.poInfo.position.y;
                i++;
            }

            all = all.NextPoNo;
        }
        return Checkpoint;

    }

}
