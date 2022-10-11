package com.example.block.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.block.MainGames.gameView.MainActivity;
import com.example.block.MainGames.logic.LoadGame;
import com.example.block.R;
import com.example.block.designCheckpoint.DesignCheckpoint;
import com.example.block.login.Login;
import com.example.block.tools.dataBaseTools.GetMyCheckpointInfos;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.example.block.menu.MainMenu.scaleX;
import static com.example.block.menu.MainMenu.scaleY;

public class EditorNewCheckpoint extends Activity implements View.OnTouchListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//消除屏幕状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.edit_or_new_checkpoint);
        init();
    }

    private Button editCheckpoint,newCheckpoint;
    private ListView myCheckpoints;
    private List<UserCheckpointInfo> userCheckpointInfos = new ArrayList<>();
    private SweetAlertDialog pDialog;
    private TextView checked;
    private String checkedCheckpointname;
    private ImageView myCheckpointNameListviewHead;
    @SuppressLint("ClickableViewAccessibility")
    private void init()
    {
        editCheckpoint = (Button) findViewById(R.id.edit_checkpoint);
        newCheckpoint = (Button) findViewById(R.id.new_checkpoint);

        myCheckpoints = (ListView) findViewById(R.id.my_checkpoints);

        checked = (TextView) findViewById(R.id.checked);

        myCheckpointNameListviewHead = findViewById(R.id.my_checkpoint_name_listview_head);

        setLayout(newCheckpoint);

        setLayout(editCheckpoint);
        setLayout(myCheckpoints);

        setLayout(checked);

        setLayout(myCheckpointNameListviewHead);

        myCheckpoints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (userCheckpointInfos.get(position) != null)
                {
                    checkedCheckpointname = userCheckpointInfos.get(position).getCheckpointName();
                    checked.setText("已选中关卡：" + checkedCheckpointname);
                }
            }
        });

        GetMyCheckpointInfos getMyCheckpointInfos = new GetMyCheckpointInfos(mHandler,userCheckpointInfos, Login.usersid);
        getMyCheckpointInfos.start();

        newCheckpoint.setOnTouchListener(this);
        editCheckpoint.setOnTouchListener(this);
    }

    private void setLayout(View v)
    {
        AbsoluteLayout.LayoutParams layoutParams;
        switch (v.getId())
        {
            case R.id.edit_checkpoint:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(1060* scaleX);
                layoutParams.y = (int)(400*scaleY);
                layoutParams.width = (int)(200*scaleX);
                layoutParams.height = (int)(100*scaleY);
                v.setLayoutParams(layoutParams);
                break;
            case R.id.new_checkpoint:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(1060* scaleX);
                layoutParams.y = (int)(540*scaleY);
                layoutParams.width = (int)(200*scaleX);
                layoutParams.height = (int)(100*scaleY);
                v.setLayoutParams(layoutParams);
                break;
            case R.id.my_checkpoints:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(100* scaleX);
                layoutParams.y = (int)(100*scaleY);
                layoutParams.width = (int)(680*scaleX);
                layoutParams.height = (int)(520*scaleY);
                v.setLayoutParams(layoutParams);
                break;
            case R.id.checked:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(800* scaleX);
                layoutParams.y = (int)(300*scaleY);
                v.setLayoutParams(layoutParams);
                ((TextView)v).setText("已选中关卡：");
                break;
            case R.id.my_checkpoint_name_listview_head:
                layoutParams = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
                layoutParams.x = (int)(100* scaleX);
                layoutParams.y = (int)(20*scaleY);
                layoutParams.width = (int)(680*scaleX);
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
                case 0x21:
                    UserCheckpointInfoAdapter adapter = new UserCheckpointInfoAdapter(EditorNewCheckpoint.this, R.layout.cat_item, userCheckpointInfos);
                    myCheckpoints.setAdapter(adapter);
                    break;
                case 0x22:
                    Toast.makeText(EditorNewCheckpoint.this,"获取用户关卡信息失败，请检查网络是否连接",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId())
        {
            case R.id.new_checkpoint:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        Bundle bundle1 = new Bundle();
                        // 把Persion数据放入到bundle中
                        bundle1.putString("checkpointName",null);
                        Intent intent1=new Intent(EditorNewCheckpoint.this,DesignCheckpoint.class);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        finish();
                        break;
                }
                break;
            case R.id.edit_checkpoint:
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN://收缩到0.8(正常值是1)，速度500
                        v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(200).start();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.animate().scaleX(1).scaleY(1).setDuration(200).start();
                        if (checkedCheckpointname == null || checkedCheckpointname.length() <= 0)
                        {
                            Toasty.warning(EditorNewCheckpoint.this,"请点击已有关卡",2000).show();
                        }
                        else
                        {
                            Bundle bundle1 = new Bundle();
                            // 把Persion数据放入到bundle中
                            bundle1.putString("checkpointName",checkedCheckpointname);
                            Intent intent1=new Intent(EditorNewCheckpoint.this,DesignCheckpoint.class);
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                        }

                        break;
                }
                break;
        }
        return false;
    }
}
