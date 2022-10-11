package com.example.block.MainGames.logic.run;

import android.util.Log;

import com.example.block.MainGames.gameBases.PoinfoNode;

public class ViewsMove {
    public static void DynamicViewsMove(PoinfoNode mario,PoinfoNode allView)
    {
        //左侧动态元素移动
        PoinfoNode moveNode = mario;
        while (moveNode != null&&mario.poInfo.position.x - moveNode.poInfo.position.x <= 1280)
        {
            moveNode.poInfo.move();
            if (moveNode.poInfo.stateType.Type >= 0)      //如果是动态元素则需要调整位置
            {
                adjustmentPoNo(moveNode,allView);
            }
            moveNode = moveNode.PrePoNo;
        }


        //右侧动态元素移动
        moveNode = mario.NextPoNo;
        while (moveNode != null&&moveNode.poInfo.position.x - mario.poInfo.position.x <= 1280)
        {
            moveNode.poInfo.move();
            if (moveNode.poInfo.stateType.Type >= 0)      ///如果是动态元素则需要调整位置
            {
                adjustmentPoNo(moveNode,allView);
            }
            moveNode = moveNode.NextPoNo;
        }
    }

    /*
   根据x轴坐标调整链表位置
    */
    private static void adjustmentPoNo(PoinfoNode poinfoNode,PoinfoNode allView)
    {
        //Log.e("TGA","调整");
        if (poinfoNode.PrePoNo != null)                                                       //如果不是第一个元素
        {
            //Log.e("TGA","前面有节点");
            if (poinfoNode.poInfo.position.x < poinfoNode.PrePoNo.poInfo.position.x)         //如果比前一节点位置的x值还小，则前移
            {
                //Log.e("TGA","x轴坐标比前面节点的小");
                while (poinfoNode.poInfo.position.x < poinfoNode.PrePoNo.poInfo.position.x)
                {
                    //Log.e("TGA","前移");

                    try {
                        if (poinfoNode.PrePoNo.PrePoNo != null)                  //如果前面存在两个节点
                        {
                            //Log.e("TGA","前面有两个节点");
                            PoinfoNode fist = poinfoNode.PrePoNo.PrePoNo;        //节点前面第二个节点

                            PoinfoNode second = poinfoNode.PrePoNo;              //节点前面第一个节点

                            second.NextPoNo = poinfoNode.NextPoNo;               //将节点前面第一个节点的后一节点等于自己的后一节点
                            if (second.NextPoNo != null)
                                second.NextPoNo.PrePoNo = second;

                            poinfoNode.PrePoNo = fist;                           //自己的前一节点等于前面第二个节点
                            fist.NextPoNo = poinfoNode;                          //前面第二节点的后一节点等于自己

                            poinfoNode.NextPoNo = second;                        //自己的前一节点等于前面第一个节点
                            second.PrePoNo = poinfoNode;                         //前面第一节点的前一节点等于自己

                        }
                        else                                                     //如果前面只有一个节点
                        {
                            //Log.e("TGA","前面有一个节点");
                            PoinfoNode second = poinfoNode.PrePoNo;

                            second.NextPoNo = poinfoNode.NextPoNo;
                            if (second.NextPoNo != null)       //如果它的后一节点不为空
                                second.NextPoNo.PrePoNo = second;

                            poinfoNode.PrePoNo = null;

                            poinfoNode.NextPoNo = second;
                            second.PrePoNo = poinfoNode;

                            allView = poinfoNode;                            //重置头节点
                            break;
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }



                }
            }
        }


        if (poinfoNode.NextPoNo != null)                                                    //如果不是最后一个元素
        {
            if (poinfoNode.poInfo.position.x > poinfoNode.NextPoNo.poInfo.position.x)       //如果比后一节点位置的x值还大，则后移
            {
                while (poinfoNode.poInfo.position.x > poinfoNode.NextPoNo.poInfo.position.x)
                {
                    try {
                        if (poinfoNode.NextPoNo.NextPoNo != null)                              //如果后面有两个节点
                        {
                            PoinfoNode first = poinfoNode.NextPoNo;

                            PoinfoNode second = poinfoNode.NextPoNo.NextPoNo;

                            if (poinfoNode.PrePoNo != null)
                                poinfoNode.PrePoNo.NextPoNo = first;
                            first.PrePoNo = poinfoNode.PrePoNo;



                            first.NextPoNo = poinfoNode;
                            poinfoNode.PrePoNo = first;

                            second.PrePoNo = poinfoNode;
                            poinfoNode.NextPoNo = second;

                            if (first.PrePoNo == null)                                         //如果第一个节点移到了后面
                                allView = first;                                               //重置第一个节点
                        }
                        else                                                                   //如果后面只有一个节点
                        {
                            Log.e("TGA","后面只有一个节点");
                            PoinfoNode second = poinfoNode.NextPoNo;

                            if ( poinfoNode.PrePoNo != null)
                                poinfoNode.PrePoNo.NextPoNo = second;
                            second.PrePoNo = poinfoNode.PrePoNo;

                            poinfoNode.PrePoNo = second;
                            second.NextPoNo = poinfoNode;

                            poinfoNode.NextPoNo = null;
                            break;
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }


//
    }
}
