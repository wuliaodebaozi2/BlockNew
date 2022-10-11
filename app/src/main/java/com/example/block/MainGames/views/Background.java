package com.example.block.MainGames.views;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.chillingvan.canvasgl.ICanvasGL;
import com.example.block.MainGames.gameView.MainActivity;
import com.example.block.R;

public class Background extends Views {
    public Background()
    {
        super();
        bitmap = new Bitmap[1];
        bitmap[0] = getBitmap(R.drawable.background);


        //改变宽高
        int width = bitmap[0].getWidth();
        int height = bitmap[0].getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) 1280) / width;
        float scaleHeight = ((float) 360) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        bitmap[0] = Bitmap.createBitmap(bitmap[0], 0, 0, width, height, matrix, true);
    }
    @Override
    public void draw(ICanvasGL canvas, float x, float y, int index) {
        canvas.drawBitmap(bitmap[index], (int)x,(int)y, MainActivity.SCREENWIDTH*2,MainActivity.SCREENHEIGHT);
        //Log.e("TAG","绘制背景  坐标" + x);
    }
}
