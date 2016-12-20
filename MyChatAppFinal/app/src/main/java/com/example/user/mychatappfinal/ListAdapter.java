package com.example.user.mychatappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 12/6/2016.
 */

public class ListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Record> recordArrayList;

    TextView senderName, senderMsg;
    ImageView senderPic;

    public ListAdapter(ArrayList<Record> recordArrayList, Context context) {
        this.recordArrayList = recordArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recordArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_layout, parent , false);

        senderName = (TextView) view.findViewById(R.id.senderNameid);
        senderMsg = (TextView) view.findViewById(R.id.senderMsgId);
        senderPic = (ImageView) view.findViewById(R.id.senderPicId);


        senderName.setText(recordArrayList.get(position).getName());
        senderMsg.setText(recordArrayList.get(position).getMsg());
        senderPic.setImageResource(recordArrayList.get(position).getPic());

        return view;
    }
}
