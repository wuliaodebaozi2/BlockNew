package com.example.block.tools.ImgTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;

public class GifUnits {
    public static ArrayList<Bitmap> getBitmapArrayByGif(Context context, String assertPath) {
        try {
            ArrayList<Bitmap> list = new ArrayList<>();
            GifDrawable gifFromAssets = new GifDrawable(context.getAssets(), assertPath);//代表android中assert的gif文件名
            int totalCount = gifFromAssets.getNumberOfFrames();
            Log.e("时间",totalCount + " ");
            for (int i = 0; i< totalCount ;i++)
            {
                list.add(gifFromAssets.seekToFrameAndGet(i));
            }

            return list;
        } catch (Exception e) {
            return null;
        }
    }
    private static void getTime(Context context,String assertPath)
    {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(assertPath);
            byte[] bytes= new byte[inputStream.available()];
            inputStream.read(bytes);
            Movie movie = Movie.decodeByteArray(bytes, 0, bytes.length);
            int time = movie.duration();
            Log.e("时间",time + "  ");
        } catch (IOException e) {
            Log.e("时间","错误  ");
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }
}
