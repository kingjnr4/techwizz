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
import com.example.admin.Model.Feedback;
import com.example.admin.Model.League;
import com.example.admin.Model.Team;
import com.example.admin.R;

import java.util.List;

public class LeagueRecyclerViewAdapter extends RecyclerView.Adapter<LeagueRecyclerViewAdapter.LeagueViewHolder> {

    private List<League> leagues;

    private Context context;

    private View view;

    public  LeagueRecyclerViewAdapter(Context context, List<League> league) {
        this.context = context;
        this.leagues = league;
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.league_item, parent, false);
        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder holder, int position) {
        League league = leagues.get(position);

        holder.leagueName.setText(league.getName());
        Glide.with(view.getContext())
                .load(league.getImage())
                .into(holder.leagueLogo);
    }

    @Override
    public int getItemCount() {
        return leagues.size();
    }

    public void filterList(List<League> filteredList) {
        leagues = filteredList;
        notifyDataSetChanged();
    }

    public class LeagueViewHolder extends RecyclerView.ViewHolder{
        ImageView leagueLogo;
        TextView leagueName;

        public LeagueViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueLogo = view.findViewById(R.id.league_image);
            leagueName = view.findViewById(R.id.league_name);
        }
    }

}


