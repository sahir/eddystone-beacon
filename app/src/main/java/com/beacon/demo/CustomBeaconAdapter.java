package com.beacon.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomBeaconAdapter extends BaseAdapter {
    ArrayList<BeaconModel> list;
    Context context;

    public CustomBeaconAdapter(ArrayList<BeaconModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_beacon, null);

        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvDist = (TextView) view.findViewById(R.id.tv_dist);


        tvName.setText(list.get(position).getBeaconName());
        tvDist.setText(list.get(position).getDistance());

        return view;
    }
}
