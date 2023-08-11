package com.example.myapplication.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MatchFixture;
import com.example.myapplication.Player;
import com.example.myapplication.R;
import com.example.myapplication.model.Fixture;


import java.time.format.DateTimeFormatter;
import java.util.List;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder> {

    private List<Fixture> fixtures;
    private Context context;

    private View view;

    public FixtureAdapter(Context context, List<Fixture> fixtures) {
        this.context = context;
        this.fixtures = fixtures;
    }

    @NonNull
    @Override
    public FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todays_fixture, parent, false);
        return new FixtureViewHolder(view);
    }

    public void onBindViewHolder(@NonNull FixtureViewHolder holder, int position) {
        Fixture fixture = fixtures.get(position);

        holder.awayName.setText(fixture.getAway());
        holder.homeName.setText(fixture.getHome());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        holder.matchDate.setText(fixture.getDate().format(dateFormatter));
        holder.matchTime.setText(fixture.getDate().format(timeFormatter));

        holder.itemView.setOnClickListener(e->{
            System.out.println("clicking");
            Intent intent = new Intent(view.getContext(), Player.class);
            startActivity(view.getContext(),intent,null);
        });
    }

    @Override
    public int getItemCount() {
        return fixtures.size();
    }

    static class FixtureViewHolder  extends RecyclerView.ViewHolder{

        TextView homeName;
        TextView awayName;
        TextView matchDate;
        TextView matchTime;
        FixtureViewHolder (@NonNull View itemView){
            super(itemView);
            homeName = itemView.findViewById(R.id.home_team_name);
            awayName = itemView.findViewById(R.id.away_team_name);
            matchDate = itemView.findViewById(R.id.match_date);
            matchTime = itemView.findViewById(R.id.match_time);

        }
    }
}
