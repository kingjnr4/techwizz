package com.example.admin.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admin.Model.Country;
import com.example.admin.R;

import java.util.List;

public class CountryRecyclerViewAdapter extends RecyclerView.Adapter<CountryRecyclerViewAdapter.CountryViewHolder> {

    private List<Country> countries;

    private Context context;

    private View view;

    public CountryRecyclerViewAdapter(Context context, List<Country> country) {
        this.context = context;
        this.countries = country;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);

        holder.countryName.setText(country.getName());
        Glide.with(view.getContext())
                .load(country.getImage())
                .into(holder.countryFlag);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{
        ImageView countryFlag;
        TextView countryName;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryFlag = view.findViewById(R.id.country_flag);
            countryName = view.findViewById(R.id.country_name);
        }
    }

}


