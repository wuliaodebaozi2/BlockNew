package com.example.block.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.block.R;

import java.util.List;

public class UserCheckpointInfoAdapter extends ArrayAdapter<UserCheckpointInfo> {

    private int resourceId;

    public UserCheckpointInfoAdapter(Context context, int resource, List<UserCheckpointInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserCheckpointInfo cat = getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //((ImageView) view.findViewById(R.id.image)).setImageResource(cat.getImageId());
        //((TextView) view.findViewById(R.id.name)).setText(cat.getName());
        ((TextView)view.findViewById(R.id.user_checkpoint_name)).setText(cat.getCheckpointName());
        ((TextView)view.findViewById(R.id.user_name)).setText(cat.getAuthor());
        return view;
    }
}