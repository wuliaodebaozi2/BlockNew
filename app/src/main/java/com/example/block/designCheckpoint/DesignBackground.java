package com.example.block.designCheckpoint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.block.R;

public class DesignBackground extends DesignViews {
    public DesignBackground()
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
    public void draw(Canvas canvas, float x, float y, int index) {
        matrix.reset();
        matrix.setScale(2, 2);
        matrix.postTranslate(x, y);
        canvas.drawBitmap(bitmap[index], matrix, new Paint());
        //Log.e("TAG","绘制背景  坐标" + x);
    }
}
