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
import com.example.admin.Model.Country;
import com.example.admin.R;

import java.util.ArrayList;


public class CountryAdapter extends ArrayAdapter<Country> {

        public CountryAdapter(Context context, ArrayList<Country> countryList) {
        super(context, 0, countryList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            return initialSelection(false);
        }
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            return initialSelection(false);
        }
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView != null && !(convertView instanceof TextView)
                ? convertView : LayoutInflater.from(getContext()).inflate(
                    R.layout.country_spinner_row, parent, false
            );
        position=position-1;
        Country currentItem = getItem(position);
        if (currentItem != null) {
            ImageView imageViewFlag = row.findViewById(R.id.image_view_flag);
            TextView textViewName = row.findViewById(R.id.text_view_name);
            Glide.with(getContext())
                    .load(currentItem.getImage())
                    .into(imageViewFlag);
            textViewName.setText(currentItem.getName());
        }

        return row;
    }

    private View initialSelection(boolean dropdown) {
        // Just an example using a simple TextView. Create whatever default view
        // to suit your needs, inflating a separate layout if it's cleaner.
        TextView view = new TextView(getContext());
        view.setText(R.string.select_one);
        int spacing = getContext().getResources().getDimensionPixelSize(R.dimen.spacing_smaller);
        view.setPadding(0, spacing, 0, spacing);
        if (dropdown) { // Hidden when the dropdown is opened
            view.setHeight(0);
        }
        return view;
    }


}
