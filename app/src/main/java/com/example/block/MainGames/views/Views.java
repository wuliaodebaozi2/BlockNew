package com.example.block.MainGames.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.chillingvan.canvasgl.ICanvasGL;
import com.example.block.R;
import com.example.block.menu.MainMenu;
import com.example.block.tools.dataBaseTools.LoadUnits;


public class Views {
    public static Context context;

    protected Bitmap[] bitmap;

    protected Matrix matrix;

    private int width,height;

    public int Type;

    public Views()
    {
        matrix = new Matrix();
    }
    public Views(int Type,int WIDTH)
    {
        matrix = new Matrix();
        this.Type = Type;
        switch (Type)
        {
            case 0:
                bitmap = new Bitmap[1];
                bitmap[0] = getBitmap(R.drawable.mario);
                break;
            case -1:
                bitmap = new Bitmap[1];
                bitmap[0] = getBitmap(R.drawable.block);
                break;
            case -2:
                bitmap = new Bitmap[1];
                bitmap[0] = getBitmap(R.drawable.moneyblock);
                break;
            case -3:
                bitmap = new Bitmap[1];
                bitmap[0] = getBitmap(R.drawable.fire);
                break;
            case -4:
                bitmap = new Bitmap[1];
                bitmap[0] = getBitmap(R.drawable.sewerpipe);
                break;
            case -5:
                bitmap = new Bitmap[1];
                bitmap[0] = getBitmap(R.drawable.moneyblock2);
                break;
            case 1:
                bitmap = new Bitmap[1];
                bitmap[0] = getBitmap(R.drawable.mushroom);
                break;
            default:
                bitmap = LoadUnits.getViewsBitmap(Type);
                break;
        }


        width = (int)(WIDTH* MainMenu.scaleX);
        height = (int)(WIDTH* MainMenu.scaleY);
    }


    public void draw(ICanvasGL canvas, float x, float y , int index)
    {
        canvas.drawBitmap(bitmap[index],(int)(x* MainMenu.scaleX),(int)(y*MainMenu.scaleY),width,height);
    }
    protected Bitmap getBitmap(int url)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), url);

        return bitmap;
    }

}
