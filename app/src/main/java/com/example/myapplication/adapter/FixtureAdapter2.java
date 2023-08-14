package com.example.myapplication.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Club;
import com.example.myapplication.R;
import com.example.myapplication.model.Fixture;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Locale;

public class FixtureAdapter2 extends RecyclerView.Adapter<FixtureAdapter2.FixtureViewHolder> {

    private List<Fixture> fixtures;
    private Context context;

    private View view;

    public FixtureAdapter2(Context context, List<Fixture> fixtures) {
        this.context = context;
        this.fixtures = fixtures;
    }

    @NonNull
    @Override
    public FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_card2, parent, false);
        return new FixtureViewHolder(view);
    }

    public void onBindViewHolder(@NonNull FixtureViewHolder holder, int position) {
        Fixture fixture = fixtures.get(position);



        if (fixture != null) {


            holder.awayTeamName.setText(fixture.getAwayTeam().getName());
            holder.homeTeamName.setText(fixture.getHomeTeam().getName());


            Glide.with(view.getContext())
                    .load(fixture.getHomeTeam().getImage())
                    .into(holder.homeTeamLogo);

            Glide.with(view.getContext())
                    .load(fixture.getAwayTeam().getImage())
                    .into(holder.awayTeamLogo);


            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("dd MMM ")
                    .parseDefaulting(ChronoField.YEAR, 2023)
                    .appendPattern("HH:mm")
                    .toFormatter(Locale.ENGLISH);

            TemporalAccessor temporal = formatter.parse(fixture.getDate());

            // Extracting date and time components
            String extractedDate = DateTimeFormatter.ofPattern("dd MMM").format(temporal);
            String extractedTime = DateTimeFormatter.ofPattern("HH:mm").format(temporal);

            holder.matchDate.setText(extractedDate);
            holder.matchTime.setText(extractedTime);

        }
        holder.itemView.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), Club.class);
            startActivity(view.getContext(),intent,null);
        });
    }

    @Override
    public int getItemCount() {
        return fixtures.size();
    }

    static class FixtureViewHolder  extends RecyclerView.ViewHolder{

        TextView homeTeamName, awayTeamName, matchDate, matchTime;
        ImageView awayTeamLogo, homeTeamLogo;
        FixtureViewHolder (@NonNull View itemView){
            super(itemView);
            homeTeamName = itemView.findViewById(R.id.home_team_name);
            awayTeamName = itemView.findViewById(R.id.away_team_name);
            matchDate = itemView.findViewById(R.id.match_date);
            matchTime = itemView.findViewById(R.id.match_time);
            awayTeamLogo = itemView.findViewById(R.id.away_team_logo);
            homeTeamLogo = itemView.findViewById(R.id.home_team_logo);

        }
    }
}
