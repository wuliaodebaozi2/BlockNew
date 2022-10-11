package com.example.block.MainGames.logic;

import com.chillingvan.canvasgl.ICanvasGL;
import com.example.block.MainGames.gameBases.CollisionNode;
import com.example.block.MainGames.gameBases.PoInfo;
import com.example.block.MainGames.gameBases.PoinfoNode;
import com.example.block.MainGames.logic.run.collision.CollisionCheckAndDeal;

import static com.example.block.MainGames.logic.run.ViewsMove.DynamicViewsMove;

/*
逻辑处理类
 */
public class Logic {
    private PoinfoNode allViewsPoinfoNode;            //所有元素的链表集合

    private PoinfoNode marioPoinfoNode;             //mario的PoinfoNode

    private CollisionNode allCo;           //所有的碰撞信息链表集合

    private CollisionNode collisionNode;

    /*
    主角位置信息
     */
    public static PoInfo marioPo;


    public Logic()
    {
        LoadGame loadGame = new LoadGame();
        allViewsPoinfoNode = loadGame.loadgame();
        marioPoinfoNode = allViewsPoinfoNode;
    }

    /*
    动态元素移动
     */
    public void DyViMove()
    {
        DynamicViewsMove(marioPoinfoNode, allViewsPoinfoNode);
    }

//    /*
//    根据x轴坐标调整链表位置
//     */
//    private void adjustmentPoNo(PoinfoNode poinfoNode)
//    {
//        //Log.e("TGA","调整");
//        if (poinfoNode.PrePoNo != null)                                                       //如果不是第一个元素
//        {
//            //Log.e("TGA","前面有节点");
//            if (poinfoNode.poInfo.position.x < poinfoNode.PrePoNo.poInfo.position.x)         //如果比前一节点位置的x值还小，则前移
//            {
//                //Log.e("TGA","x轴坐标比前面节点的小");
//                while (poinfoNode.poInfo.position.x < poinfoNode.PrePoNo.poInfo.position.x)
//                {
//                    //Log.e("TGA","前移");
//
//                    try {
//                        if (poinfoNode.PrePoNo.PrePoNo != null)                  //如果前面存在两个节点
//                        {
//                            //Log.e("TGA","前面有两个节点");
//                            PoinfoNode fist = poinfoNode.PrePoNo.PrePoNo;        //节点前面第二个节点
//
//                            PoinfoNode second = poinfoNode.PrePoNo;              //节点前面第一个节点
//
//                            second.NextPoNo = poinfoNode.NextPoNo;               //将节点前面第一个节点的后一节点等于自己的后一节点
//                            if (second.NextPoNo != null)
//                            second.NextPoNo.PrePoNo = second;
//
//                            poinfoNode.PrePoNo = fist;                           //自己的前一节点等于前面第二个节点
//                            fist.NextPoNo = poinfoNode;                          //前面第二节点的后一节点等于自己
//
//                            poinfoNode.NextPoNo = second;                        //自己的前一节点等于前面第一个节点
//                            second.PrePoNo = poinfoNode;                         //前面第一节点的前一节点等于自己
//
//                        }
//                        else                                                     //如果前面只有一个节点
//                        {
//                            //Log.e("TGA","前面有一个节点");
//                            PoinfoNode second = poinfoNode.PrePoNo;
//
//                            second.NextPoNo = poinfoNode.NextPoNo;
//                            if (second.NextPoNo != null)       //如果它的后一节点不为空
//                            second.NextPoNo.PrePoNo = second;
//
//                            poinfoNode.PrePoNo = null;
//
//                            poinfoNode.NextPoNo = second;
//                            second.PrePoNo = poinfoNode;
//
//                            allViewsPoinfoNode = poinfoNode;                            //重置头节点
//                            break;
//                        }
//                    }catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//
//
//                }
//            }
//        }
//
//
//        if (poinfoNode.NextPoNo != null)                                                    //如果不是最后一个元素
//        {
//            if (poinfoNode.poInfo.position.x > poinfoNode.NextPoNo.poInfo.position.x)       //如果比后一节点位置的x值还大，则后移
//            {
//                while (poinfoNode.poInfo.position.x > poinfoNode.NextPoNo.poInfo.position.x)
//                {
//                    try {
//                        if (poinfoNode.NextPoNo.NextPoNo != null)                              //如果后面有两个节点
//                        {
//                            PoinfoNode first = poinfoNode.NextPoNo;
//
//                            PoinfoNode second = poinfoNode.NextPoNo.NextPoNo;
//
//                            if (poinfoNode.PrePoNo != null)
//                            poinfoNode.PrePoNo.NextPoNo = first;
//                            first.PrePoNo = poinfoNode.PrePoNo;
//
//
//
//                            first.NextPoNo = poinfoNode;
//                            poinfoNode.PrePoNo = first;
//
//                            second.PrePoNo = poinfoNode;
//                            poinfoNode.NextPoNo = second;
//
//                            if (first.PrePoNo == null)                                         //如果第一个节点移到了后面
//                                allViewsPoinfoNode = first;                                               //重置第一个节点
//                        }
//                        else                                                                   //如果后面只有一个节点
//                        {
//                            Log.e("TGA","后面只有一个节点");
//                            PoinfoNode second = poinfoNode.NextPoNo;
//
//                            if ( poinfoNode.PrePoNo != null)
//                            poinfoNode.PrePoNo.NextPoNo = second;
//                            second.PrePoNo = poinfoNode.PrePoNo;
//
//                            poinfoNode.PrePoNo = second;
//                            second.NextPoNo = poinfoNode;
//
//                            poinfoNode.NextPoNo = null;
//                            break;
//                        }
//                    }catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        }
//
//
////
//    }

    /*
    检测所有元素碰撞
     */
    public void checkCollision()
    {
        try {
            CollisionCheckAndDeal.checkCollision(allViewsPoinfoNode,marioPoinfoNode);
            //CollisionCheckAndDeal.checkCollision(allViewsPoinfoNode,marioPoinfoNode);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
//    {
//        PoinfoNode checkPoNo = allViewsPoinfoNode;
//
//        //每次检测都重置碰撞链表
//
//        allCo = new CollisionNode();
//        collisionNode = allCo;
//        collisionNode.PreCoNo = null;
//
//        while (checkPoNo != null)
//        {
//            if (checkPoNo.poInfo.stateType.Type >= 0)         //如果是动态元素则检测碰撞
//            {
//                checkPpNoCollision(checkPoNo,collisionNode.collisionArrayList);
//                collisionNode.NextCoNo = new CollisionNode();
//                collisionNode = collisionNode.NextCoNo;
//            }
//
//            checkPoNo = checkPoNo.NextPoNo;
//        }
//    }

//    /*
//    检测单个元素碰撞,将一个动态元素的碰撞放入一个collisionArrayList
//     */
//    private void checkPpNoCollision(PoinfoNode p , ArrayList<Collision> collisionArrayList)
//    {
//        PoinfoNode leftCheckPoNo = p.PrePoNo;
//        PoinfoNode rightCheckPoNo = p.NextPoNo;
//
//        //左侧碰撞
//        while (leftCheckPoNo != null && (p.poInfo.position.x - leftCheckPoNo.poInfo.position.x) <=300 )
//        {
//            int  horizontal  =	Math.abs ((p.poInfo.position.x  + p.poInfo.stateType.width/2)  -( leftCheckPoNo.poInfo.position.x + leftCheckPoNo.poInfo.stateType.width/2))
//                    - (p.poInfo.stateType.width/2 + leftCheckPoNo.poInfo.stateType.width/2);
//
//            int  vertical    =  Math.abs((p.poInfo.position.y + p.poInfo.stateType.width/2) - (leftCheckPoNo.poInfo.position.y + leftCheckPoNo.poInfo.stateType.width/2))
//                    - (p.poInfo.stateType.width/2 + leftCheckPoNo.poInfo.stateType.width/2);
//
//            if (horizontal<=0 && vertical <= 0)       //碰撞
//            {
//                if (horizontal > vertical)
//                {
//                    //Log.e("TAG","水平左侧碰撞");
//
//                    //向碰撞链表中添加碰撞
//                    Collision collision = new Collision(Collision.CollisionDirection.Left,p,leftCheckPoNo);
//                    collisionArrayList.add(collision);
//
//
//
//                }
//                else
//                {
//                    //Log.e("TAG","垂直碰撞");
//                    if ((p.poInfo.position.y + p.poInfo.stateType.width/2) - (leftCheckPoNo.poInfo.position.y + leftCheckPoNo.poInfo.stateType.width/2) >=0)
//                    {
//                        //Log.e("TAG","上碰撞");
//                        //向碰撞链表中添加碰撞
//                        Collision collision = new Collision(Collision.CollisionDirection.top,p,leftCheckPoNo);
//                        collisionArrayList.add(collision);
//                    }
//                    else
//                    {
//                        //Log.e("TAG","下碰撞");
//                        //向碰撞链表中添加碰撞
//                        Collision collision = new Collision(Collision.CollisionDirection.bottom,p,leftCheckPoNo);
//                        collisionArrayList.add(collision);
//                    }
//                }
//            }
//            leftCheckPoNo = leftCheckPoNo.PrePoNo;
//        }
//
//
//
//
//        //右侧检测
//        while (rightCheckPoNo != null && (rightCheckPoNo.poInfo.position.x - p.poInfo.position.x) <=300)
//        {
//            int  horizontal  =	Math.abs ((p.poInfo.position.x  + p.poInfo.stateType.width/2)  -( rightCheckPoNo.poInfo.position.x + rightCheckPoNo.poInfo.stateType.width/2))
//                    - (p.poInfo.stateType.width/2 + rightCheckPoNo.poInfo.stateType.width/2);
//
//            int  vertical    =  Math.abs((p.poInfo.position.y + p.poInfo.stateType.width/2) - (rightCheckPoNo.poInfo.position.y + rightCheckPoNo.poInfo.stateType.width/2))
//                    - (p.poInfo.stateType.width/2 + rightCheckPoNo.poInfo.stateType.width/2);
//
//            if (horizontal <=0 && vertical <= 0)       //碰撞
//            {
//                if (horizontal > vertical)               //水平碰撞
//                {
//                    //Log.e("TAG","水平右侧碰撞");
//                    //向碰撞链表中添加碰撞
//                    Collision collision = new Collision(Collision.CollisionDirection.right,p,rightCheckPoNo);
//                    collisionArrayList.add(collision);
//                }
//                else                                    //垂直碰撞
//                {
//                    //Log.e("TAG","垂直碰撞");
//                    if (((p.poInfo.position.y + p.poInfo.stateType.width/2) - (rightCheckPoNo.poInfo.position.y + rightCheckPoNo.poInfo.stateType.width/2)) >= 0)
//                    {
//                        //Log.e("TAG","上碰撞");
//                        //向碰撞链表中添加碰撞
//                        Collision collision= new Collision(Collision.CollisionDirection.top,p,rightCheckPoNo);
//                        collisionArrayList.add(collision);
//                    }
//                    else
//                    {
//                        //Log.e("TAG","下碰撞");
//                        Collision collision = new Collision(Collision.CollisionDirection.bottom,p,rightCheckPoNo);
//                        collisionArrayList.add(collision);
//                    }
//
//                }
//            }
//            rightCheckPoNo = rightCheckPoNo.NextPoNo;
//        }
//    }




//    /*
//    碰撞处理
//     */
//    public void dealCollision()
//    {
//        //先处理上下碰撞
//        CollisionNode dealCo = allCo;
//        try {
//            //遍历所有的动态元素碰撞
//            while (dealCo != null)
//            {
//
//
//                //遍历单个动态元素的所有碰撞
//                while (dealCo.collisionArrayList.size() != 0)
//                {
//                    int j = 0;  //找出中心点距离最短的碰撞
//                    Log.e("TAG","list个数为："+dealCo.collisionArrayList.size());
//                    for (int i=0;i < dealCo.collisionArrayList.size();i++)
//                    {
//                        if (getDistance(dealCo.collisionArrayList.get(i).collisedObjectNode.poInfo.getCore(),dealCo.collisionArrayList.get(i).collisionObjectNode.poInfo.getCore())
//                        <= getDistance(dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.getCore(),dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.getCore()))
//                        {
//                            j = i;
//                        }
//                    }
//
//                    switch (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.stateType.Type)
//                    {
//                        case 0:                                                                             //碰撞物为mario
//                            switch (dealCo.collisionArrayList.get(j).collisionDirection)
//                            {
//                                case top:                                                                   //上碰撞
//                                    //Log.e("TAG","mario上碰撞");
//                                    if (( Math.abs((dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.position.x + dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.stateType.width/2) - (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.position.x + dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.stateType.width/2))
//                                            - (dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.stateType.width/2 + dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.stateType.width/2)) < -1)
//                                    {
//                                        if (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed < 0)             //调整mario竖直方向的速度
//                                        {
//                                            dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed = - dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed;
//
//                                            //碰撞处理
//                                            dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.dealCollision(Collision.CollisionDirection.top);
//                                        }
//                                    }
//
//
//                                    break;
//                                case right:                                                                 //左、右碰撞
//                                case Left:
//
//                                    break;
//                                case bottom:                                                                //下碰撞
//                                    //Log.e("TAG","mario下碰撞");
//                                    if (( Math.abs((dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.position.x + dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.stateType.width/2) - (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.position.x + dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.stateType.width/2))
//                                            - (dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.stateType.width/2 + dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.stateType.width/2)) < -1)
//                                    {
//                                        if (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed >= 0)
//                                        {
//                                            dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed = 0;       //竖直方向置0
//                                            //碰撞处理
//                                            if (dealCo.collisionArrayList.get(j).collisedObjectNode.poInfo.dealCollision(Collision.CollisionDirection.bottom))
//                                            {
//                                                //删除被撞物
//                                                DeleteNode(dealCo.collisionArrayList.get(j).collisedObjectNode);
//                                            }
//                                        }
//                                    }
//
//
//                                    break;
//
//                            }
//                            break;
//                        case 1:                                                                             //碰撞物为蘑菇
//                            switch (dealCo.collisionArrayList.get(j).collisionDirection)
//                            {
//                                case top:                                                                   //上碰撞
//                                    break;
//                                case right:                                                                 //左、右碰撞
//                                    if (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.xSpeed > 0)
//                                    {
//                                        dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.xSpeed = - dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.xSpeed;                 //左、右碰撞水平方速度相反
//                                    }
//                                    break;
//                                case Left:
//                                    //Log.e("TAG","mario左、右碰撞");
//                                    if (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.xSpeed < 0)
//                                    {
//                                        dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.xSpeed = - dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.xSpeed;                 //左、右碰撞水平方速度相反
//                                    }
//                                    break;
//                                case bottom:                                                                //下碰撞
//                                    //Log.e("TAG","mario下碰撞");
//                                    if (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed > 0)
//                                    dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed = -dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed;                 //竖直方向置0
//                                    break;
//
//                            }
//                            break;
//                        case 2:
//                            switch (dealCo.collisionArrayList.get(j).collisionDirection)
//                            {
//                                case bottom:                                                                //下碰撞
//                                    //Log.e("TAG","mario下碰撞");
//                                    if (dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed > 0)
//                                        dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed = -dealCo.collisionArrayList.get(j).collisionObjectNode.poInfo.ySpeed;                 //竖直方向置0
//                                    break;
//
//                            }
//                            break;
//                        case 3:
//                            break;
//                    }
//
//
//                    adjustmentPo(dealCo.collisionArrayList.get(j));
//                    dealCo.collisionArrayList.remove(j);
//                }
//
//                dealCo = dealCo.NextCoNo;
//
//            }
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }


//    /*
//    获取两点间距离的平方
//     */
//    public float getDistance(Point a,Point b)
//    {
//        return (a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y);
//    }

//    /*
//    碰撞后碰撞物位置的调整
//     */
//    private void adjustmentPo(Collision collision)
//    {
//        if (collision.collisedObjectNode.poInfo.stateType.Type != 0)
//        {
//            switch (collision.collisionDirection)
//            {
//                case top:          //碰撞物的位置y = 被撞物y + 被撞物width
//                    if (( Math.abs((collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.x + collision.collisionObjectNode.poInfo.stateType.width/2))
//                            - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < -1)
//                    {
//                        collision.collisionObjectNode.poInfo.position.y = collision.collisedObjectNode.poInfo.position.y + collision.collisedObjectNode.poInfo.stateType.width;
//                    }
//
//                    break;
//                case bottom:      //碰撞物的位置y = 被撞物y - 碰撞物width
//                    //Log.e("TAG","下碰撞位置调整" + collision.collisionObjectNode.poInfo.position.y);
//                    //collision.collisionObjectNode.poInfo.position.y = 0;
//                    //减2的目的是为了 使mario与物体一直碰撞 以便跳跃时检测时 y方向速度为0 可以跳起
//                    if (( Math.abs((collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.x + collision.collisionObjectNode.poInfo.stateType.width/2))
//                            - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < -1)
//                    {
//                        collision.collisionObjectNode.poInfo.position.y = collision.collisedObjectNode.poInfo.position.y - collision.collisionObjectNode.poInfo.stateType.width;
//                    }
//
//                    break;
//                case Left:       //碰撞物的位置x =  被撞物x +被撞物width
//
//                    //if语句真对既有上下碰撞 又有左右碰撞的情况 ，即处理完上下碰撞后 不需要再处理左右碰撞
//                    //小于0的目的是为了上下碰撞调整位置后不再处理左右碰撞，又可以使mario跳起
//                    if (( Math.abs((collision.collisedObjectNode.poInfo.position.y + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.y + collision.collisionObjectNode.poInfo.stateType.width/2))
//                            - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < 0)
//                    {
//                        //如果是mario则，判断是否是跳跃之后踩在物体上，如果是则不需要左右调整
//                        if (collision.collisionObjectNode != marioPoinfoNode || collision.collisionObjectNode.poInfo.ySpeed < 600)
//                            collision.collisionObjectNode.poInfo.position.x = collision.collisedObjectNode.poInfo.position.x + collision.collisedObjectNode.poInfo.stateType.width + 1;
//                    }
//
//                    break;
//                case right:    //碰撞物的位置x = 被撞物x -碰撞物width
//                    //if语句真对既有上下碰撞 又有左右碰撞的情况 ，即处理完上下碰撞后 不需要再处理左右碰撞
//                    if (( Math.abs((collision.collisedObjectNode.poInfo.position.y + collision.collisedObjectNode.poInfo.stateType.width/2) - (collision.collisionObjectNode.poInfo.position.y + collision.collisionObjectNode.poInfo.stateType.width/2))
//                            - (collision.collisedObjectNode.poInfo.stateType.width/2 + collision.collisionObjectNode.poInfo.stateType.width/2)) < 0)
//                    {
//                        //如果是mario则，判断是否是跳跃之后踩在物体上，如果是则不需要左右调整
//                        if (collision.collisionObjectNode != marioPoinfoNode || collision.collisionObjectNode.poInfo.ySpeed < 600)
//                            collision.collisionObjectNode.poInfo.position.x = collision.collisedObjectNode.poInfo.position.x - collision.collisionObjectNode.poInfo.stateType.width - 1;
//                    }
//
//                    break;
//            }
//        }
//
//    }
    /*
    绘图
     */

    public void draw(ICanvasGL canvas)
    {

        DrawGame.drawGame(marioPoinfoNode,canvas);
    }

    /*
    删除链表中某个节点
     */
    private void DeleteNode(PoinfoNode poinfoNode)
    {
        PoinfoNode PrePoNo = poinfoNode.PrePoNo;
        PoinfoNode NextPoNo = poinfoNode.NextPoNo;
        //判断是否是第一个节点
        if (PrePoNo == null)
        {
            NextPoNo.PrePoNo  = null;
            allViewsPoinfoNode = poinfoNode;
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


}
