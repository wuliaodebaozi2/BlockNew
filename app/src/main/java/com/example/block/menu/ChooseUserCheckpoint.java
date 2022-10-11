package com.example.block.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.block.MainGames.gameView.MainActivity;
import com.example.block.MainGames.logic.LoadGame;
import com.example.block.R;
import com.example.block.designCheckpoint.DesignCheckpoint;
import com.example.block.login.Login;
import com.example.block.tools.dataBaseTools.GetCheck;
import com.example.block.tools.dataBaseTools.GetMyCheckpointInfos;
import com.example.block.tools.dataBaseTools.GetUsersCheckpointInfos;
import com.example.block.tools.dataBaseTools.GetUsersCheckpointInfosbyKeyword;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.example.block.menu.MainMenu.scaleX;
import static com.example.block.menu.MainMenu.scaleY;

public class ChooseUserCheckpoint extends Activity implements View.OnTouchListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//消除屏幕状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.choose_user_checkpoint);
        init();
    }

    private Button start,searchwithKeyword,myCheckPoint;
    private EditText userCheckpointName,searchwithKeywordEdit;
    private ListView userCheckpointNameList;
    private List<UserCheckpointInfo> userCheckpointInfos = new ArrayList<>();
    private SweetAlertDialog pDialog;
    private ImageView userCheckpointNameListviewHead;
    @SuppressLint("ClickableViewAccessibility")
    private void init()
    {
        start = (Button)findViewById(R.id.start_user_checkpoint);
        userCheckpointName = (EditText)findViewById(R.id.user_checkpoint_name);
        userCheckpointNameList = (ListView)findViewById(R.id.user_checkpoint_name_list);
        userCheckpointNameListviewHead = (ImageView)findViewById(R.id.user_checkpoint_name_listview_head);
        searchwithKeyword = (Button) findViewById(R.id.search_with_keyword);
        myCheckPoint = (Button) findViewById(R.id.my_checkpoints);
        searchwithKeywordEdit = (EditText) findViewById(R.id.search_with_keyword_edit);

        setLayou(start);
        setLayou(userCheckpointName);
        setLayou(userCheckpointNameList);
        setLayou(userCheckpointNameListviewHead);
        setLayou(searchwithKeyword);
        setLayou(searchwithKeywordEdit);
        setLayou(myCheckPoint);


        GetUsersCheckpointInfos getUsersCheckpointInfos = new GetUsersCheckpointInfos(mHandler,userCheckpointInfos);
        getUsersCheckpointInfos.start();




        SpannableString ss = new SpannableString("请输入关卡名");
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) (16*scaleX), true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        userCheckpointName.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失


        start.setOnTouchListener(this);
        searchwithKeyword.setOnTouchListener(this);
        myCheckPoint.setOnTouchListener(this);

        userCheckpointNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (userCheckpointInfos.get(position) != null)
               {
                   userCheckpointName.setText(userCheckpointInfos.get(position).getCheckpointName());
               }
            }
        });
    }

    private void setLayou(View v)
    {
        AbsoluteLayout.LayoutParams layoutParams;
        switch (v.getId())
        {
            case R.id.start_user_checkpoint:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(980* scaleX);
                layoutParams.y = (int)(420*scaleY);
                layoutParams.width = (int)(200*scaleX);
                layoutParams.height = (int)(100*scaleY);
                v.setLayoutParams(layoutParams);
                v.setBackgroundResource(R.drawable.start);
                break;
            case R.id.user_checkpoint_name:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(980* scaleX);
                layoutParams.y = (int)(200*scaleY);
                layoutParams.width = (int)(200*scaleX);
                layoutParams.height = (int)(100*scaleY);
                v.setLayoutParams(layoutParams);
                //v.setBackgroundResource(R.drawable.start);

                break;
            case R.id.user_checkpoint_name_list:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(100* scaleX);
                layoutParams.y = (int)(100*scaleY);
                layoutParams.width = (int)(680*scaleX);
                layoutParams.height = (int)(520*scaleY);
                v.setLayoutParams(layoutParams);
                //v.setBackgroundResource(R.drawable.start);
                break;
            case R.id.user_checkpoint_name_listview_head:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(100* scaleX);
                layoutParams.y = (int)(20*scaleY);
                layoutParams.width = (int)(680*scaleX);
                layoutParams.height = (int)(80*scaleY);
                v.setLayoutParams(layoutParams);
                //v.setBackgroundResource(R.drawable.start);
                break;
            case R.id.search_with_keyword_edit:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(100* scaleX);
                layoutParams.y = (int)(630*scaleY);
                layoutParams.width = (int)(200*scaleX);
                layoutParams.height = (int)(80*scaleY);
                v.setLayoutParams(layoutParams);
                SpannableString ss = new SpannableString("输入关键字搜索");
                AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) (10*scaleX), true);
                ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                // 设置hint
                ((EditText)v).setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失

                break;
            case R.id.search_with_keyword:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(320* scaleX);
                layoutParams.y = (int)(630*scaleY);
                layoutParams.width = (int)(80*scaleX);
                layoutParams.height = (int)(80*scaleY);
                v.setLayoutParams(layoutParams);
                break;
            case R.id.my_checkpoints:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(690* scaleX);
                layoutParams.y = (int)(630*scaleY);
                layoutParams.width = (int)(80*scaleX);
                layoutParams.height = (int)(80*scaleY);
                v.setLayoutParams(layoutParams);
                break;
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // process incoming messages here
            switch (msg.what)
            {
                case 0x12:
                    if (pDialog != null)
                    {
                        pDialog.dismiss();
                    }
                    if (LoadGame.length != 0)
                    {
                        openGame();
                    }
                    else Toast.makeText(ChooseUserCheckpoint.this,"关卡为空",Toast.LENGTH_SHORT).show();
                    break;
                case 0x13:
                    if (pDialog != null)
                    {
                        pDialog.dismiss();
                    }
                    Toast.makeText(ChooseUserCheckpoint.this,"找不到此关卡",Toast.LENGTH_LONG).show();
                    break;
                case 0x21:
                    UserCheckpointInfoAdapter adapter = new UserCheckpointInfoAdapter(ChooseUserCheckpoint.this, R.layout.cat_item, userCheckpointInfos);
                    userCheckpointNameList.setAdapter(adapter);
                    break;
                case 0x22:
                    Toast.makeText(ChooseUserCheckpoint.this,"获取用户关卡信息失败，请检查网络是否连接",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    private void openGame()
    {
        Intent intent = new Intent();
        intent.setClass(ChooseUserCheckpoint.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.start_user_checkpoint:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();

                        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("正在下载关卡...");
                        pDialog.setCancelable(false);
                        pDialog.setCanceledOnTouchOutside(false);
                        pDialog.show();
                        GetCheck getCheck = new GetCheck(mHandler,userCheckpointName.getText().toString());
                        getCheck.start();
                        break;
                }
                break;
            case R.id.my_checkpoints:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        if (Login.usersid != null)
                        {
                            GetMyCheckpointInfos getMyCheckpointInfos = new GetMyCheckpointInfos(mHandler,userCheckpointInfos, Login.usersid);
                            getMyCheckpointInfos.start();
                        }
                        else
                        {
                            Toasty.warning(ChooseUserCheckpoint.this,"您还没有登陆",2000).show();
                        }

                        break;
                }
                break;
            case R.id.search_with_keyword:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        GetUsersCheckpointInfosbyKeyword getUsersCheckpointInfosbyKeyword =
                                new GetUsersCheckpointInfosbyKeyword(mHandler,userCheckpointInfos,searchwithKeywordEdit.getText().toString());
                        getUsersCheckpointInfosbyKeyword.start();
                        break;
                }
                break;
        }
        return false;
    }
}
