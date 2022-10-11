package com.example.block.MainGames.logic;

import android.graphics.Point;
import android.util.Log;

import com.example.block.MainGames.gameBases.DealCollision;
import com.example.block.MainGames.gameBases.PoInfo;
import com.example.block.MainGames.gameBases.PoinfoNode;
import com.example.block.MainGames.gameBases.StateType;
import com.example.block.MainGames.views.Views;
import com.example.block.tools.ArrayAndString;
import com.example.block.tools.dataBaseTools.LoadUnits;

import java.util.ArrayList;

import static com.example.block.MainGames.gameBases.PoInfo.getStateType;
import static com.example.block.MainGames.gameBases.PoInfo.stateTypes;
import static com.example.block.MainGames.logic.DrawGame.getViews;
import static com.example.block.MainGames.logic.DrawGame.views;
import static com.example.block.MainGames.logic.Logic.marioPo;

public class LoadGame {



    /*
    1.加载状态信息
     */
    private void loadStateTypes()
    {

        stateTypes = new ArrayList<StateType>();


        //加载关卡出现的状态信息
        for (int i=0;i<length;i++)
        {
            if (getStateType(viewPos[i][0]) == null)
            {
                loadStateTypeByType(viewPos[i][0]);
            }

        }

        //加载相关的状态信息
        for (int i=0;i<stateTypes.size();i++)
        {
            if (stateTypes.get(i).Relevant != null && stateTypes.get(i).Relevant.length > 0)
            {
                for (int j = 0;j < stateTypes.get(i).Relevant.length;j++)
                {
                    if (getStateType(stateTypes.get(i).Relevant[j]) == null)
                    {
                        loadStateTypeByType(stateTypes.get(i).Relevant[j]);
                    }

                }
            }
        }
    }


    /*
    根据类型加载StateType
     */
    private void loadStateTypeByType(int Type)
    {
        final StateType stateType;
        switch (Type)
        {
            case 1:
                stateType = new StateType(1,50,null,new int[]{0},
                        new DealCollision[]{
                                new DealCollision(false,0,0,true,null,null),
                                new DealCollision(false,0,0,true,null,null),
                                new DealCollision(true,0,0,false,null,null),
                                new DealCollision(false,0,0,true,null,null)
                        },-300,0,0,200);
                stateTypes.add(stateType);
                break;
            case 2:
                stateType =  new StateType(2,50,null,new int[]{0},
                        new DealCollision[]{
                                new DealCollision(false,0,0,true,null,null),
                                new DealCollision(false,0,0,true,null,null),
                                new DealCollision(false,0,0,false,
                                        null,null),
                                new DealCollision(false,0,0,true,null,null)
                        },0,0,0,200);
                stateTypes.add(stateType);
                break;
            case -1:
                stateType = new StateType(-1,50,null,new int[]{0},
                        null,0,0,0,0);
                stateTypes.add(stateType);
                break;
            case -2:
                stateType = new StateType(-2,54,new int[]{-5},new int[]{0},
                        new DealCollision[]{
                                new DealCollision(false,-5,1,false,null,null),
                                new DealCollision(false,0,0,false,null,null),
                                new DealCollision(false,0,0,false,null,null),
                                new DealCollision(false,0,0,false,null,null)
                        },0,0,0,0);
                stateTypes.add(stateType);


                break;
            case -3:
                stateType = new StateType(-3,100,null,new int[]{0},
                        new DealCollision[]{
                                new DealCollision(false,0,0,true,null,null),
                                new DealCollision(false,0,0,true,null,null),
                                new DealCollision(false,0,0,true,null,null),
                                new DealCollision(false,0,0,true,null,null)
                        },0,0,0,0);
                stateTypes.add(stateType);
                break;
            case -4:
                stateType = new StateType(-4,100,null,new int[]{0},
                        null,0,200,0,0);
                stateTypes.add(stateType);
                break;
            case -5:
                stateType = new StateType(-5,54,new int[]{-2},new int[]{0},
                        new DealCollision[]{
                                new DealCollision(false,0,0,false,null,null),
                                new DealCollision(false,0,0,false,null,null),
                                new DealCollision(false,0,0,false,null,null),
                                new DealCollision(false,0,0,false,null,null)
                        },0,0,0,0);
                stateTypes.add(stateType);
                break;
            default://不是基本的状态信息，从数据库中获取
                stateType = LoadUnits.GetStateTypefromDB(Type);
                stateTypes.add(stateType);
                break;
        }
    }








    /*
    加载画图所需要的元素
     */
    private void loadViews() {
        views = new ArrayList<Views>();
        Views view;
        view = new Views(0, 50);    //初始化mario
        views.add(view);
        for (int i = 0;i < stateTypes.size();i++)
        {
            if (getViews(stateTypes.get(i).Type) == null)
            {
                view = new Views(stateTypes.get(i).Type, stateTypes.get(i).width);        //初始化marioView
                views.add(view);
            }
        }
    }



    /*
   2.根据数组加载PoInfo
    */
    private void initPo()
    {
        poInfos = new PoInfo[length];

        for (int i=0;i<length;i++)
        {
            StateType stateType = getStateType(viewPos[i][0]);
            if (stateType != null)
                poInfos[i] = new PoInfo(new Point(viewPos[i][1],viewPos[i][2]),stateType);
        }


    }
    private PoinfoNode loadPoinfoNode()
    {
        initPo();  //初始化PoInfos数组



        marioPo = new PoInfo(new Point(0,0),new StateType(0,50,null,new int[]{0},null,0,0,0,1600));
        PoinfoNode allView = new PoinfoNode(marioPo);


        PoinfoNode constructionPoNo  = allView;

        allView.PrePoNo = null;


        for (PoInfo p :poInfos)
        {
            constructionPoNo.NextPoNo = new PoinfoNode(p);

            constructionPoNo.NextPoNo.PrePoNo = constructionPoNo;     //下一节点的前一节点等于当前节点

            constructionPoNo.NextPoNo.NextPoNo = null;                //下一节点的后一节点等于null

            constructionPoNo = constructionPoNo.NextPoNo;

        }

        return allView;
    }






    private static PoInfo[] poInfos;



    public PoinfoNode loadgame()
    {
       loadStateTypes();  //加载状态信息
       loadViews();       //加载绘图信息
       return loadPoinfoNode();
    }


    /*
    其他类 设置这个数组
     */
    public static int length = 0;
    public static int [][]viewPos;
}
