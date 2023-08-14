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
import com.example.myapplication.PlayerActivity;
import com.example.myapplication.model.Player;
import com.example.myapplication.R;
import com.example.myapplication.model.Fixture;

import java.util.List;


public class TopScoreAdapter extends RecyclerView.Adapter<TopScoreAdapter.TopScoreViewHolder> {

    private List<Player> players;

    private Context context;

    private View view;

    public TopScoreAdapter(Context context, List<Player> fixtures) {
        this.context = context;
        this.players = fixtures;
    }


    @NonNull
    @Override
    public TopScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        return new TopScoreAdapter.TopScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopScoreViewHolder holder, int position) {
        Player player = players.get(position);
        holder.playerName.setText(player.getSpacedFullName());
        holder.playerPosition.setText(player.getPosition());
        Glide.with(view.getContext()).load(player.getImage()).into(holder.playerImage);
        holder.itemView.setOnClickListener(e->{
            Intent intent = new Intent(view.getContext(), PlayerActivity.class);
            startActivity(view.getContext(),intent,null);
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class TopScoreViewHolder extends RecyclerView.ViewHolder{

        TextView playerName,playerPosition;
        ImageView playerImage;

        public TopScoreViewHolder(@NonNull View itemView) {

            super(itemView);
            playerName = itemView.findViewById(R.id.player_name);
            playerPosition = itemView.findViewById(R.id.player_position);
            playerImage=itemView.findViewById(R.id.player_image);
        }
    }
}
