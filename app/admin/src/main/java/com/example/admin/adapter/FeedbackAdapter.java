package com.example.admin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admin.Model.Feedback;
import com.example.admin.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private List<Feedback> feedbacks;

    private Context context;

    private View view;

    public  FeedbackAdapter(Context context, List<Feedback> feedbacks) {
        this.context = context;
        this.feedbacks = feedbacks;
    }

    @NonNull
    @NotNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeedbackAdapter.FeedbackViewHolder holder, int position) {
        Feedback feedback=feedbacks.get(position);
        Log.d("feed",feedback.toString());
        holder.feedbackMessage.setText(feedback.getMessage());
        holder.feedbackEmail.setText(feedback.getUser().getEmail());
        holder.feedbackDate.setText(feedback.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return feedbacks.size();
    }
    public class FeedbackViewHolder extends RecyclerView.ViewHolder{
        TextView feedbackMessage,feedbackEmail,feedbackDate;
        public FeedbackViewHolder(@NonNull View itemView){
            super(itemView);
            feedbackEmail=view.findViewById(R.id.feedback_email);
            feedbackMessage=view.findViewById(R.id.feedback_message);
            feedbackDate=view.findViewById(R.id.feedback_date);
        }
    }
}
