package com.example.block.designCheckpoint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.block.R;

public class DesignViews {
    public static Context context;

    protected Bitmap[] bitmap;

    protected Matrix matrix;

    public DesignViews()
    {
        matrix = new Matrix();
    }
    public DesignViews(int Type, int WIDTH)
    {
        matrix = new Matrix();
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
        }
        for (int i=0;i<1;i++)
        {
            //改变宽高
            int width = bitmap[i].getWidth();
            int height = bitmap[i].getHeight();
            // 计算缩放比例.
            float scaleWidth = ((float) WIDTH) / width;
            float scaleHeight = ((float) WIDTH) / height;
            // 取得想要缩放的matrix参数.
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片.
            bitmap[i] = Bitmap.createBitmap(bitmap[i], 0, 0, width, height, matrix, true);
        }
    }


    public void draw(Canvas canvas, float x, float y , int index)
    {
        matrix.reset();
        matrix.setScale(1, 1);
        matrix.postTranslate(x, y);
        canvas.drawBitmap(bitmap[index], matrix, new Paint());
    }
    protected Bitmap getBitmap(int url)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), url);

        return bitmap;
    }

}