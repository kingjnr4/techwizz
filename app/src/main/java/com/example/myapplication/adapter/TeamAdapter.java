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

import com.example.myapplication.model.Club;
import com.example.myapplication.PlayerActivity;
import com.example.myapplication.model.Player;
import com.example.myapplication.R;
import com.example.myapplication.model.Fixture;

import java.util.List;


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private List<Club> clubs;

    private Context context;

    private View view;

    public TeamAdapter(Context context, List<Club> clubs) {
        this.context = context;
        this.clubs = clubs;
    }


    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_item, parent, false);
        return new TeamAdapter.TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Club club = clubs.get(position);
        holder.clubName.setText(club.getClubName());

        holder.itemView.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), PlayerActivity.class);
            startActivity(view.getContext(),intent,null);
        });
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder{

        TextView clubName;

        public TeamViewHolder(@NonNull View itemView) {

            super(itemView);
            clubName = itemView.findViewById(R.id.club_name);
        }
    }
}
