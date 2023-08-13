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
import com.example.admin.Model.Feedback;
import com.example.admin.Model.Team;
import com.example.admin.R;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private List<Team> teams;

    private Context context;

    private View view;

    public  TeamAdapter(Context context, List<Team> teams) {
        this.context = context;
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_item, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teams.get(position);

        holder.teamName.setText(team.getName());
        Glide.with(view.getContext())
                .load(team.getImage())
                .into(holder.teamLogo);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder{
        ImageView teamLogo;
        TextView teamName;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            teamLogo = view.findViewById(R.id.club_image);
            teamName = view.findViewById(R.id.club_name);
        }
    }

}
