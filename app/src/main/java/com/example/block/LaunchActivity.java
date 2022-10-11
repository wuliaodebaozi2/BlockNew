package com.example.block;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.block.menu.MainMenu;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载启动界面

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//消除屏幕状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);

        ImageView imgCorporateName = findViewById(R.id.image);

        //淡入淡出动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgCorporateName, "alpha", 0f, 1f,1f,0f);
        animator.setDuration(3000);

        //动画监听
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //动画结束时跳转到下一Activity
                Intent intent=new Intent(LaunchActivity.this,MainMenu.class);
                startActivity(intent);
                //自定义页面跳转动画效果
                overridePendingTransition(R.anim.push_in,R.anim.push_out);
//                HawkOnLineActivity.this.finish();
                LaunchActivity.this.finish();

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

//
//        Integer time = 3500;    //设置等待时间，单位为毫秒
//        Handler handler = new Handler();
//        //当计时结束时，跳转至主界面
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(LaunchActivity.this, MainMenu.class));
//                LaunchActivity.this.finish();
//            }
//        }, time);
    }
}