package com.example.block.MainGames.logic;


import android.view.View;

import com.chillingvan.canvasgl.ICanvasGL;
import com.example.block.MainGames.gameBases.PoinfoNode;
import com.example.block.MainGames.gameView.MainActivity;
import com.example.block.MainGames.views.Background;
import com.example.block.MainGames.views.Views;

import java.util.ArrayList;

import static com.example.block.MainGames.logic.Logic.marioPo;

public class DrawGame {

    public static void drawGame(PoinfoNode mario,ICanvasGL canvas)
    {
        if (background == null)
        {
            background = new Background();
        }
        try {
            background.draw(canvas, -((int)(marioPo.position.x )  % MainActivity.SCREENWIDTH) ,0,0);

            //land.draw(canvas,0,0,0);

            PoinfoNode drawViews = mario;

            //mario左侧绘图  只绘制在屏幕上的元素
            while (drawViews != null&&(drawViews.poInfo.position.x + drawViews.poInfo.stateType.width - mario.poInfo.position.x) + 575 >= 0)
            {
                drawViews(drawViews,canvas);
                drawViews = drawViews.PrePoNo;
            }

            //mario右侧侧绘图  只绘制在屏幕上的元素
            drawViews = mario.NextPoNo;
            while (drawViews != null&&(drawViews.poInfo.position.x - mario.poInfo.position.x) + 575 <= 1280)
            {
                drawViews(drawViews,canvas);
                drawViews = drawViews.NextPoNo;
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
   根据元素种类绘图
 */
    private static void drawViews(PoinfoNode drawViews,ICanvasGL canvas)
    {
        try {
            Views views;
            views = getViews(drawViews.poInfo.stateType.Type);
            if (views != null)
            views.draw(canvas,(drawViews.poInfo.position.x - marioPo.position.x) + 575, drawViews.poInfo.position.y,
                    drawViews.poInfo.index);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static Background background;
    public static ArrayList<Views> views;

    public static Views getViews(int Type)
    {
        for (int i = 0;i<views.size();i++)
        {
            if (views.get(i).Type == Type)
            {
                return views.get(i);
            }
        }

        return null;
    }
}
