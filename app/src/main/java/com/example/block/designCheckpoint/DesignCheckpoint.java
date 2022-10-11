package com.example.block.designCheckpoint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.block.R;
import com.example.block.MainGames.gameBases.DealCollision;
import com.example.block.MainGames.gameBases.PoInfo;
import com.example.block.MainGames.gameBases.PoinfoNode;
import com.example.block.MainGames.gameBases.StateType;
import com.example.block.login.Login;
import com.example.block.tools.dataBaseTools.CreateCheckNameTable;
import com.example.block.tools.dataBaseTools.ExportCheckPoint;
import com.example.block.tools.dataBaseTools.GetDesignCheck;
import com.example.block.tools.dataBaseTools.ViewposInfos;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.block.designCheckpoint.DesignCheckpointLogic.AddNode;
import static com.example.block.designCheckpoint.DesignCheckpointLogic.marioPoNo;
import static com.example.block.designCheckpoint.DesignCheckpointLogic.poinfoNode;

import androidx.annotation.RequiresApi;


public class DesignCheckpoint extends Activity implements View.OnTouchListener{



    private  float scaleX , scaleY;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        SCREENWIDTH = dm.widthPixels;         // 屏幕宽度（像素）
        SCREENHEIGHT = dm.heightPixels;       // 屏幕高度（像素）

        scaleX = SCREENWIDTH / (float)1280;
        scaleY = SCREENHEIGHT / (float)720;

        setContentView(R.layout.design_checkpoint);
        DesignCheckpointSurface.PAUSE = false;


        init();




    }


    private void init()
    {
        left = (Button) findViewById(R.id.leftBt);
        right = (Button)findViewById(R.id.rightBt);
        mushroom = (Button)findViewById(R.id.mushroom);

        leftAdd = (Button) findViewById(R.id.left);
        rightAdd = (Button)findViewById(R.id.right);
        upAdd = (Button)findViewById(R.id.up);
        bottomAdd = (Button)findViewById(R.id.bottom);
        fixed = (Button)findViewById(R.id.fixed);
        sewerpipe = (Button)findViewById(R.id.sewerPipe);
        fire = (Button)findViewById(R.id.fire);
        block = (Button)findViewById(R.id.block);
        moneyblock = (Button)findViewById(R.id.moneyblock);
        complet = (Button)findViewById(R.id.complet);
        designCheckpointSurface = (DesignCheckpointSurface)findViewById(R.id.DesignCheckpointSurface);
        backPosition = (TextView) findViewById(R.id.back_position);
        adjustViewPo = (TextView) findViewById(R.id.adjust_view_position);
        adjustViewBg = findViewById(R.id.adjust_view);
        delete = (Button) findViewById(R.id.delete);
        scrollX = (EditText) findViewById(R.id.scroll_x);
        adjustViewX = (EditText)findViewById(R.id.adjust_view_x);
        adjustViewY = (EditText)findViewById(R.id.adjust_view_y);
        preView = (Button) findViewById(R.id.pre_view);
        nextView = (Button) findViewById(R.id.next_view);
        instruction = (ImageView) findViewById(R.id.instructions);
        instructions_bt = (Button) findViewById(R.id.instructions_bt);
//        instruction.setVisibility(View.VISIBLE);
//        instruction.bringToFront();

        left.setOnTouchListener(this);
        right.setOnTouchListener(this);

        leftAdd.setOnTouchListener(this);
        rightAdd.setOnTouchListener(this);
        upAdd.setOnTouchListener(this);
        bottomAdd.setOnTouchListener(this);
        complet.setOnTouchListener(this);
        delete.setOnTouchListener(this);


        setLayout(leftAdd);
        setLayout(rightAdd);
        setLayout(upAdd);
        setLayout(bottomAdd);
        setLayout(fixed);
        setLayout(mushroom);
        setLayout(sewerpipe);
        setLayout(block);
        setLayout(moneyblock);
        setLayout(fire);
        setLayout(complet);
        setLayout(backPosition);
        setLayout(left);
        setLayout(right);
        setLayout(adjustViewPo);
        setLayout(delete);
        setLayout(scrollX);
        setLayout(adjustViewX);
        setLayout(adjustViewY);
        setLayout(preView);
        setLayout(nextView);
        setLayout(instructions_bt);


        mushroom.setOnTouchListener(this);
        block.setOnTouchListener(this);
        moneyblock.setOnTouchListener(this);
        fire.setOnTouchListener(this);
        sewerpipe.setOnTouchListener(this);
        preView.setOnTouchListener(this);
        nextView.setOnTouchListener(this);
        instructions_bt.setOnTouchListener(this);


        fixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DesignCheckpointSurface.PAUSE = !DesignCheckpointSurface.PAUSE;
                if (DesignCheckpointSurface.PAUSE)
                {
                    fixed.setBackgroundResource(R.drawable.fixed);
                    adjustViewCanMove = true;
                }
                else
                {
                    poinfoNode.poInfo.position.x = getNum(adjustViewX.getText().toString());
                    poinfoNode.poInfo.position.y = getNum(adjustViewY.getText().toString());
                    mHandler.sendEmptyMessage(0x31);
                    fixed.setBackgroundResource(R.drawable.unfixed);
                    adjustViewCanMove = false;
                }
            }
        });

        Intent intent=getIntent();
        // 实例化一个Bundle
        Bundle bundle=intent.getExtras();
        if (bundle.getString("checkpointName") != null)
        {
            GetDesignCheck getDesignCheck = new GetDesignCheck(mHandler,bundle.getString("checkpointName"));
            getDesignCheck.start();
            checkPointName = bundle.getString("checkpointName");
        }
    }
    private int getNum(String a)
    {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        String b = m.replaceAll("").trim();
        double num = Double.parseDouble(b);
        return (int)num;
    }
    @Override
    public void onBackPressed() {

        pDialog = new SweetAlertDialog(DesignCheckpoint.this,SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("你确定吗?").setContentText("原关卡已被删除！退出此关卡也将不会被保存！").setCancelText("取消").setConfirmText("退出").showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user state, reset them if you need
                      sDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        designCheckpointSurface.surfaceDestroyed(null);
                        finish();
                    }
                });
        try {
            pDialog.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    private Button left,right;
    private Button mushroom,fixed,delete,complet;
    private Button fire,block,moneyblock,sewerpipe;
    private Button leftAdd,rightAdd,upAdd,bottomAdd;
    private Button preView,nextView;
    private Timer timer;
    private DesignCheckpointSurface designCheckpointSurface;
    private  SweetAlertDialog pDialog;//解析关卡提示框
    private TextView backPosition,adjustViewPo;
    private boolean adjustViewCanMove = true;
    private View adjustViewBg;
    private EditText scrollX,adjustViewX,adjustViewY;
    private ImageView instruction;
    private Button instructions_bt;

    private void setLayout(View button)
    {
        //872   488
        //654   396
        AbsoluteLayout.LayoutParams layoutParams;

        switch (button.getId())
        {
            case R.id.left:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(974*scaleX);
                layoutParams.y = (int)(568*scaleY);
                layoutParams.width = (int)(100*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.right:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(1174*scaleX);
                layoutParams.y = (int)(568*scaleY);
                layoutParams.width = (int)(100*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.up:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(1074*scaleX);
                layoutParams.y = (int)(518*scaleY);
                layoutParams.width = (int)(100*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.bottom:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(1074*scaleX);
                layoutParams.y = (int)(605*scaleY);
                layoutParams.width = (int)(100*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.fixed:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(964*scaleX);
                layoutParams.y = (int)(418*scaleY);
                layoutParams.width = (int)(150*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.delete:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(1134*scaleX);
                layoutParams.y = (int)(418*scaleY);
                layoutParams.width = (int)(150*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.mushroom:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(640*scaleX);
                layoutParams.y = (int)(0*scaleY);
                layoutParams.width = (int)(50*scaleX);
                layoutParams.height = (int)(50*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.fire:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(700*scaleX);
                layoutParams.y = (int)(0*scaleY);
                layoutParams.width = (int)(50*scaleX);
                layoutParams.height = (int)(50*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.block:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(760*scaleX);
                layoutParams.y = (int)(0*scaleY);
                layoutParams.width = (int)(50*scaleX);
                layoutParams.height = (int)(50*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.moneyblock:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(820*scaleX);
                layoutParams.y = (int)(0*scaleY);
                layoutParams.width = (int)(50*scaleX);
                layoutParams.height = (int)(50*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.sewerPipe:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(880*scaleX);
                layoutParams.y = (int)(0*scaleY);
                layoutParams.width = (int)(50*scaleX);
                layoutParams.height = (int)(50*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.complet:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(540*scaleX);
                layoutParams.y = (int)(620*scaleY);
                layoutParams.width = (int)(200*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.pre_view:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(535*scaleX);
                layoutParams.y = (int)(520*scaleY);
                layoutParams.width = (int)(100*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.next_view:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(645*scaleX);
                layoutParams.y = (int)(520*scaleY);
                layoutParams.width = (int)(100*scaleX);
                layoutParams.height = (int)(100*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.leftBt:
                AbsoluteLayout.LayoutParams layoutParams1 = (AbsoluteLayout.LayoutParams) left.getLayoutParams();
                layoutParams1.x = (int)(0*scaleX);
                layoutParams1.y = (int)(500*scaleY);
                layoutParams1.width = (int)(160*scaleX);
                layoutParams1.height = (int)(160*scaleY);
                left.setLayoutParams(layoutParams1);
                break;
            case R.id.rightBt:
                AbsoluteLayout.LayoutParams layoutParams2 = (AbsoluteLayout.LayoutParams) right.getLayoutParams();
                layoutParams2.x = (int)(180*scaleX);
                layoutParams2.y = (int)(500*scaleY);
                layoutParams2.width = (int)(160*scaleX);
                layoutParams2.height = (int)(160*scaleY);
                right.setLayoutParams(layoutParams2);
                break;
            case R.id.back_position:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(0*scaleX);
                layoutParams.y = (int)(400*scaleY);
                button.setLayoutParams(layoutParams);
                ((TextView)button).setTextSize(TypedValue.COMPLEX_UNIT_PX,35*scaleX);
                break;
            case R.id.adjust_view_position:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(930*scaleX);
                layoutParams.y = (int)(320 *scaleY);
                button.setLayoutParams(layoutParams);
                ((TextView)button).setTextSize(TypedValue.COMPLEX_UNIT_PX,35*scaleX);
                break;
            case R.id.adjust_view_x:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(930*scaleX);
                layoutParams.y = (int)(368 *scaleY);
                layoutParams.width = (int)(150*scaleX);
                layoutParams.height = (int)(50*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.adjust_view_y:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(1130*scaleX);
                layoutParams.y = (int)(368 *scaleY);
                layoutParams.width = (int)(150*scaleX);
                layoutParams.height = (int)(50*scaleY);
                button.setLayoutParams(layoutParams);
                break;
            case R.id.instructions_bt:
                layoutParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();
                layoutParams.x = (int)(1230*scaleX);
                layoutParams.y = (int)(0 *scaleY);
                layoutParams.width = (int)(50*scaleX);
                layoutParams.height = (int)(50*scaleY);
                button.setLayoutParams(layoutParams);
                break;
        }

    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.leftBt:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        DesignCheckpointLogic.marioPo.position.x -= 50;

                        backPosition.setText("卷轴移动，卷轴水平坐标："+ DesignCheckpointLogic.marioPo.position.x);
                        adjustView();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                break;
            case R.id.rightBt:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        DesignCheckpointLogic.marioPo.position.x += 50;

                        backPosition.setText("卷轴移动，卷轴水平坐标："+ DesignCheckpointLogic.marioPo.position.x);
                        adjustView();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                break;
            case R.id.left:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (poinfoNode != null)
                    {
                        if (adjustViewCanMove)
                            poinfoNode.poInfo.position.offset(-50,0);
                        mHandler.sendEmptyMessage(0x31);
                    }
                }else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    mHandler.sendEmptyMessage(0x31);
                }


                break;
            case R.id.right:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if (poinfoNode != null)
                    {
                        if (adjustViewCanMove)
                            poinfoNode.poInfo.position.offset(50,0);
                        mHandler.sendEmptyMessage(0x31);
                    }

                    //Logic.marioPo.yA = 3000;
                }else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    mHandler.sendEmptyMessage(0x31);
                }
                break;
            case R.id.up:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (poinfoNode != null)
                    {
                        if (adjustViewCanMove)
                            poinfoNode.poInfo.position.offset(0,-50);
                        mHandler.sendEmptyMessage(0x31);
                    }

                    //Logic.marioPo.yA = 3000;
                }else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    mHandler.sendEmptyMessage(0x31);
                }
                break;
            case R.id.bottom:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (poinfoNode != null&& poinfoNode.poInfo.position.y + poinfoNode.poInfo.stateType.width < 630)
                    {
                        if (adjustViewCanMove)
                            poinfoNode.poInfo.position.offset(0,50);
                        mHandler.sendEmptyMessage(0x31);
                    }

                    //Logic.marioPo.yA = 3000;
                }else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    mHandler.sendEmptyMessage(0x31);
                }
                break;
            case R.id.mushroom:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!DesignCheckpointSurface.PAUSE)
                        {
                            DesignCheckpointSurface.PAUSE = true;
                            poinfoNode = new PoinfoNode(
                                    new PoInfo(new Point( DesignCheckpointLogic.marioPo.position.x,300),new StateType(1,50,null,new int[]{0},
                                            new DealCollision[]{
                                                    new DealCollision(false,0,0,true,null,null),
                                                    new DealCollision(false,0,0,true,null,null),
                                                    new DealCollision(true,0,0,false,null,null),
                                                    new DealCollision(false,0,0,true,null,null)
                                            },-30,0,0,200))

                            );

                            AddNode();
                            fixed.setBackgroundResource(R.drawable.fixed);
                            adjustViewCanMove = true;
                            adjustView();
                        }
                        else
                        {
                            Toast.makeText(DesignCheckpoint.this,"请先固定元素",Toast.LENGTH_SHORT).show();
                        }
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        break;
                }
                break;
            case R.id.block:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!DesignCheckpointSurface.PAUSE)
                        {
                            DesignCheckpointSurface.PAUSE = true;
                            poinfoNode = new PoinfoNode(
                                    new PoInfo(new Point( DesignCheckpointLogic.marioPo.position.x,300),new StateType(-1,50,null,new int[]{0},
                                            null,0,0,0,0))
                            );
                            AddNode();
                            fixed.setBackgroundResource(R.drawable.fixed);
                            adjustViewCanMove = true;
                            adjustView();
                        }
                        else
                        {
                            Toast.makeText(DesignCheckpoint.this,"请先固定元素",Toast.LENGTH_SHORT).show();
                        }
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        break;
                }

                break;
            case R.id.moneyblock:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!DesignCheckpointSurface.PAUSE)
                        {
                            DesignCheckpointSurface.PAUSE = true;
                            poinfoNode = new PoinfoNode(
                                    new PoInfo(new Point(DesignCheckpointLogic.marioPo.position.x,300),new StateType(-2,50,new int[]{-5},new int[]{0},
                                            new DealCollision[]{
                                                    new DealCollision(false,-5,1,false,null,null),
                                                    new DealCollision(false,0,0,false,null,null),
                                                    new DealCollision(false,0,0,false,null,null),
                                                    new DealCollision(false,0,0,false,null,null)
                                            },0,0,0,0))
                            );
                            AddNode();
                            fixed.setBackgroundResource(R.drawable.fixed);
                            adjustViewCanMove = true;
                            //adjustViewPo.setText("正在调整当前元素位置\n x:"+poinfoNode.poInfo.position.x+" y:"+poinfoNode.poInfo.position.y);
                            adjustView();
                        }
                        else
                        {
                            Toast.makeText(DesignCheckpoint.this,"请先固定元素",Toast.LENGTH_SHORT).show();
                        }
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        break;
                }

                break;
            case R.id.fire:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!DesignCheckpointSurface.PAUSE)
                        {
                            DesignCheckpointSurface.PAUSE = true;
                            poinfoNode = new PoinfoNode(
                                    new PoInfo(new Point(DesignCheckpointLogic.marioPo.position.x,200),new StateType(-3,100,null,new int[]{0},
                                            new DealCollision[]{
                                                    new DealCollision(false,0,0,true,null,null),
                                                    new DealCollision(false,0,0,true,null,null),
                                                    new DealCollision(false,0,0,true,null,null),
                                                    new DealCollision(false,0,0,true,null,null)
                                            },0,0,0,0))
                            );
                            AddNode();
                            fixed.setBackgroundResource(R.drawable.fixed);
                            adjustViewCanMove = true;
                            //adjustViewPo.setText("正在调整当前元素位置\n x:"+poinfoNode.poInfo.position.x+" y:"+poinfoNode.poInfo.position.y);
                            adjustView();
                        }
                        else
                        {
                            Toast.makeText(DesignCheckpoint.this,"请先固定元素",Toast.LENGTH_SHORT).show();
                        }
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        break;
                }

                break;
            case R.id.sewerPipe:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!DesignCheckpointSurface.PAUSE)
                        {
                            DesignCheckpointSurface.PAUSE = true;
                            poinfoNode = new PoinfoNode(
                                    new PoInfo(new Point(DesignCheckpointLogic.marioPo.position.x,200),new StateType(-4,100,null,new int[]{0},
                                            null,0,0,0,0))
                            );
                            AddNode();
                            fixed.setBackgroundResource(R.drawable.fixed);
                            adjustViewCanMove = true;
                            //adjustViewPo.setText("正在调整当前元素位置\n x:"+poinfoNode.poInfo.position.x+" y:"+poinfoNode.poInfo.position.y);
                            adjustView();
                        }
                        else
                        {
                            Toast.makeText(DesignCheckpoint.this,"请先固定元素",Toast.LENGTH_SHORT).show();
                        }
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        break;
                }

                break;

            case R.id.delete:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        deleteView();
                        break;
                }
                break;
            //导出表
            case R.id.complet:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("提示")
                                .setContentText("确定导出关卡吗")
                                .setCancelText("取消")
                                .setConfirmText("确定")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        exportCheckpoint();
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                        break;
                }
                break;
            case R.id.pre_view:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        toNextView();
                        break;
                }
                break;
            case R.id.next_view:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        toPreView();
                        break;
                }
                break;
            case R.id.instructions_bt:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        instruction.setVisibility(View.VISIBLE);
                        instruction.bringToFront();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        instruction.setVisibility(View.GONE);
                        break;
                }
                break;
        }
        return true;
    }

    private void toPreView()
    {
        if (poinfoNode.PrePoNo != marioPoNo)
        {
            poinfoNode = poinfoNode.PrePoNo;
            mHandler.sendEmptyMessage(0x31);
        }
    }
    private void toNextView()
    {
        if (poinfoNode.NextPoNo != null)
        {
            poinfoNode = poinfoNode.NextPoNo;
            mHandler.sendEmptyMessage(0x31);
        }
    }
    private void  deleteView()
    {
        if (poinfoNode != null)
        {
            if (poinfoNode.NextPoNo != null)
            {
                poinfoNode.PrePoNo.NextPoNo = poinfoNode.NextPoNo;
                poinfoNode.NextPoNo.PrePoNo = poinfoNode.PrePoNo;
                poinfoNode = poinfoNode.NextPoNo;
                mHandler.sendEmptyMessage(0x41);
                mHandler.sendEmptyMessage(0x31);
                return;
            }
            if (poinfoNode.PrePoNo != marioPoNo)
            {
                poinfoNode.PrePoNo.NextPoNo = poinfoNode.NextPoNo;
                if (poinfoNode.NextPoNo != null)
                {
                    poinfoNode.NextPoNo.PrePoNo = poinfoNode.PrePoNo;
                    poinfoNode.PrePoNo.NextPoNo = poinfoNode.NextPoNo;
                }
                else
                {
                    if (poinfoNode.NextPoNo != null)
                    {
                        poinfoNode.NextPoNo.PrePoNo = poinfoNode.PrePoNo;
                    }

                }
                poinfoNode = poinfoNode.PrePoNo;
                mHandler.sendEmptyMessage(0x41);
                mHandler.sendEmptyMessage(0x31);
                return;
            }
            poinfoNode.PrePoNo.NextPoNo = null;
            poinfoNode = null;
            mHandler.sendEmptyMessage(0x41);
            mHandler.sendEmptyMessage(0x31);
        }
        else
        {
            mHandler.sendEmptyMessage(0x41);
            mHandler.sendEmptyMessage(0x31);
        }


    }
    @SuppressLint("SetTextI18n")
    private void adjustView()
    {
        if (poinfoNode != null)
        {
            adjustViewPo.setText("正在调整当前元素位置");
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) adjustViewBg.getLayoutParams();
            layoutParams.x = (int)((poinfoNode.poInfo.position.x  - DesignCheckpointLogic.marioPo.position.x + 575)*scaleX / 2);
            layoutParams.y = (int)(poinfoNode.poInfo.position.y*scaleY / 2);
            layoutParams.width = (int)(poinfoNode.poInfo.stateType.width*scaleX / 2);
            layoutParams.height = (int)(poinfoNode.poInfo.stateType.width*scaleY / 2);
            adjustViewBg.setLayoutParams(layoutParams);
            adjustViewX.setText("x:" + poinfoNode.poInfo.position.x);
            adjustViewY.setText("y:" + poinfoNode.poInfo.position.y);
        }
        else
        {
            adjustViewPo.setText("正在调整当前元素位置");
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) adjustViewBg.getLayoutParams();
            layoutParams.x = 0;
            layoutParams.y = 0;
            layoutParams.width = 0;
            layoutParams.height = 0;
            adjustViewBg.setLayoutParams(layoutParams);
            adjustViewX.setText("x:");
            adjustViewY.setText("y:");
        }


    }

    public static int SCREENWIDTH,SCREENHEIGHT;



    private void exportCheckpoint()
    {
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("解析关卡...");
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            /*
            防止关卡过长，不响应
             */
            new Thread()
            {
                @Override
                public void run() {
                    super.run();
                    Checkpoint = designCheckpointSurface.export();
                    mHandler.sendEmptyMessage(0x127);
                }
            }.start();
    }





    /*
    输入弹窗,一定得到一个不为空的名字
     */
    private void alertDialog(String s)
    {
        if (pDialog != null)
        {
            pDialog.dismiss();
        }

        final EditText et = new EditText(this);

        new AlertDialog.Builder(this).setTitle(s)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        checkPointName = et.getText().toString();
                        if (checkPointName.length() > 0)   //输入了名字
                        {
                            try {
                                pDialog = new SweetAlertDialog(DesignCheckpoint.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("正在上传，请稍后...");
                                pDialog.setCancelable(false);
                                pDialog.setCanceledOnTouchOutside(false);
                                pDialog.show();

                                mHandler.sendEmptyMessage(0x123);



                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            checkPointName = null;
                            alertDialog("输入为空，请重新输入...");

                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        checkPointName = null;
                        if (exportCheckPoint != null)
                        {
                            exportCheckPoint.interrupt();
                            exportCheckPoint = null;
                        }

                    }
                })
                .show();
    }



    private int[][] Checkpoint;
    private ExportCheckPoint exportCheckPoint;
    private String checkPointName;
    @SuppressLint("HandlerLeak")



    private Handler  mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0x123:

                    CreateCheckNameTable createCheckNameTable = new CreateCheckNameTable(mHandler,checkPointName, Login.usersid);
                    createCheckNameTable.start();
                    break;
                case 0x124:
                    new SweetAlertDialog(DesignCheckpoint.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("成功!")
                            .setContentText("关卡信息已上传")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    designCheckpointSurface.surfaceDestroyed(null);
                                    finish();
                                }
                            })
                            .show();
                    break;
                case 0x125:
                    //alertDialog1.setMessage("上传失败");

                    new SweetAlertDialog(DesignCheckpoint.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("错误")
                            .setContentText("上传失败")
                            .show();
                    break;
                case 0x127:   //确定关卡不为空后，输入名字
                    if (pDialog != null)
                    pDialog.dismiss();

                    //如果关卡长度大于等于1
                    if (Checkpoint.length >= 1)
                    {
                        if (checkPointName == null || checkPointName.length() <= 0)
                        {
                            alertDialog("请输入关卡名字,请不要以数字或者符号开头！");
                        }
                        else
                        {
                            try {
                                pDialog = new SweetAlertDialog(DesignCheckpoint.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("正在上传，请稍后...");
                                pDialog.setCancelable(false);
                                pDialog.setCanceledOnTouchOutside(false);
                                pDialog.show();

                                mHandler.sendEmptyMessage(0x123);



                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {

                        new SweetAlertDialog(DesignCheckpoint.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("警告")
                                .setContentText("关卡为空")
                                .show();


                    }
                    break;
                case 0x21:   //创建表成功
                    if (exportCheckPoint == null)
                    {
                        exportCheckPoint = new ExportCheckPoint(mHandler,Checkpoint,checkPointName,Login.usersid);
                        exportCheckPoint.start();
                    }
                    else
                    {
                        exportCheckPoint.interrupt();
                        exportCheckPoint = new ExportCheckPoint(mHandler,Checkpoint,checkPointName,Login.usersid);
                        exportCheckPoint.start();
                    }
                    break;
                case 0x22:     //创建表失败
                    alertDialog("关卡名字重复或者名字以数字或符号开头，请重新输入...");
                    break;
                case 0x31:    //修改调整元素的坐标
                     adjustView();
                    //adjustViewPo.setText("正在调整当前元素位置\n x:"+poinfoNode.poInfo.position.x+" y:"+poinfoNode.poInfo.position.y);
                    break;
                case 0x41:
                    fixed.setBackgroundResource(R.drawable.fixed);
                    adjustViewCanMove = true;
                    DesignCheckpointSurface.PAUSE = false;
                    break;
                case 0x51:
                    Bundle bundle = msg.getData();
                    designCheckpointSurface.loadView((ViewposInfos) bundle.getSerializable("viewposInfos"));
                    adjustView();
                    break;
                case 0x52:

                    break;
            }

        }
    };



}



