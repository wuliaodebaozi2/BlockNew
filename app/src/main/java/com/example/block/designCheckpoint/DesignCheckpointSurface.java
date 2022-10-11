package com.example.block.designCheckpoint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.block.MainGames.gameBases.PoInfo;
import com.example.block.MainGames.views.Views;
import com.example.block.tools.dataBaseTools.ViewposInfos;


public class DesignCheckpointSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    private SurfaceHolder mHolder;

    private Thread t;

    private Canvas mCanvas;

    private boolean isRunning;

    private Paint paint;

    private Bitmap BufferBitmap;

    private Canvas BufferCanvas;

    private Views marioView;

    private PoInfo[] poInfos;

    private DesignCheckpointLogic logic;

    public static boolean PAUSE = false;

    public DesignCheckpointSurface(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);







        //DesignViews.context = this.getContext();               //给Views设置content

        logic = new DesignCheckpointLogic();                         //先设置content再初始化

    }

    public DesignCheckpointSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);





        DesignViews.context = this.getContext();               //给Views设置content

        logic = new DesignCheckpointLogic();                      //先设置content再初始化


    }






    /*
    surface创建时
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("TAG","surfaceCreate");
        BufferBitmap = Bitmap.createBitmap(1300,720,Bitmap.Config.ARGB_8888);
        BufferCanvas = new Canvas(BufferBitmap);
        isRunning = true;
        t = new Thread(this);
        t.start();

    }


    public void loadView(ViewposInfos viewposInfos)
    {
        logic.loadPoinfos(viewposInfos);
    }
    /*
    屏幕高宽改变时
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("TGA","surfaceChanged");
    }


    /*
    surface销毁时
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("TGA","surfaceDestroyed");
        isRunning = false;           //通知关闭线程
    }

    @Override
    public void run() {
        while (isRunning)
        {
            long start = System.currentTimeMillis();                 //线程开始绘制的时间
            draw();
            long end = System.currentTimeMillis();                   //线程绘制结束时间

            //Log.e("TGA","绘制所需时间"+(end - start) + " ");
            try {
                if (end - start < 33)
                    Thread.sleep(33 - (end - start));
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }




    private void draw()
    {

        try {

            mCanvas = mHolder.lockCanvas();

            mCanvas.drawColor(Color.WHITE);           //清空画布

            long lg = System.currentTimeMillis();



                //logic.DyViMove();                         //移动
                logic.checkCollision();                   //碰撞检测
                //logic.dealCollision();                    //碰撞处理




            //Log.e("TAG","逻辑处理时间" + (System.currentTimeMillis() - lg));

            long st = System.currentTimeMillis();
            // 计算缩放比例.
            float scaleWidth = ((float) DesignCheckpoint.SCREENWIDTH /2) / 1280;
            float scaleHeight = ((float) DesignCheckpoint.SCREENHEIGHT /2) / 720;
            // 取得想要缩放的matrix参数.
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            matrix.postTranslate(0,0);
            mCanvas.drawBitmap(logic.draw(),matrix,new Paint());


            mHolder.unlockCanvasAndPost(mCanvas);

            //Log.e("TAG","draw时间" + (System.currentTimeMillis() - st));
        }catch (Exception e)
        {
            Log.e("TAG","失败了");
        }

    }

    public int[][] export()
    {
        return logic.export();

    }

}

