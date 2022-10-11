package com.example.block.MainGames.gameView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glview.texture.GLContinuousTextureView;
import com.example.block.MainGames.logic.Logic;

import com.example.block.MainGames.views.Views;
import com.example.block.R;

public class GameSurface extends GLContinuousTextureView {


    private Logic logic;
    private boolean isRunning = true;
    private Context context;

    public GameSurface(Context context) {
        super(context);
        Views.context = this.getContext();               //给Views设置content
        this.context = context;
        //logic = new Logic();
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        Views.context = this.getContext();               //给Views设置content
        this.context = context;
        //logic = new Logic();
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Views.context = this.getContext();               //给Views设置content
        //logic = new Logic();
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
            Log.e("TGA","surfaceDestroyed");
            isRunning = false;           //通知关闭线程
    }


    @Override
    protected void onGLDraw(ICanvasGL canvas) {
        if (logic == null)
        {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.loading);
            canvas.drawBitmap(bitmap,0,0,MainActivity.SCREENWIDTH,MainActivity.SCREENHEIGHT);
            logic = new Logic();
        }
        if (isRunning)
        {
            long start = System.currentTimeMillis();
            logic.DyViMove();                         //移动

            logic.checkCollision();                   //碰撞检测
            logic.draw(canvas);
            //logic.dealCollision();                    //碰撞处理



            long end = System.currentTimeMillis();
            //Log.e("碰撞","绘制所有时间" + (end - start));
        }

    }
}
