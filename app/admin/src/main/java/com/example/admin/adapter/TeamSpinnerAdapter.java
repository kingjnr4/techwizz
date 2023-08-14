package com.example.admin.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.Model.League;
import com.example.admin.Model.Team;
import com.example.admin.R;

import java.util.ArrayList;


public class TeamSpinnerAdapter extends ArrayAdapter<Team> {

    public TeamSpinnerAdapter(Context context, ArrayList<Team> teams) {
        super(context, 0, teams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(
                    R.layout.country_spinner_row, parent, false
            );
        }
        Team currentItem = getItem(position);
        if (currentItem != null) {
            ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
            TextView textViewName = convertView.findViewById(R.id.text_view_name);
            Glide.with(getContext())
                    .load(currentItem.getImage())
                    .into(imageViewFlag);
            textViewName.setText(currentItem.getName());
        }

        return convertView;
    }


}
