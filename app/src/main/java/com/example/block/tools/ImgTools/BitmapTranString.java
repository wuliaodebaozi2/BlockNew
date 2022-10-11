package com.example.block.tools.ImgTools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class BitmapTranString {
    public static Bitmap stringToBitmap(String string) {
        if (string != null)
        {
            // 将字符串转换成Bitmap类型
            Bitmap bitmap = null;
            try {
                byte[] bitmapArray;
                bitmapArray = Base64.decode(string, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                        bitmapArray.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        else
        {
            return null;
        }

    }


    public static String bitmapToString(Bitmap bitmap){
        if (bitmap != null)
        {
            //将Bitmap转换成字符串
            String string=null;
            ByteArrayOutputStream bStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
            byte[]bytes=bStream.toByteArray();
            string=Base64.encodeToString(bytes,Base64.DEFAULT);
            return string;
        }
        else
        {
            return null;
        }

    }
}
