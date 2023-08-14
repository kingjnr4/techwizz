package com.example.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admin.Model.Admin;
import com.example.admin.Model.Feedback;
import com.example.admin.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    private List<Admin> admins;
    private Context context;
    public  AdminAdapter(Context context,List<Admin> list){
        this.context=context;
                this.admins=list;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
       Admin item = admins.get(position);
       holder.textView.setText(item.getUsername());
    }

    @Override
    public int getItemCount() {
        return admins.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.admin_name);
        }
    }
}
