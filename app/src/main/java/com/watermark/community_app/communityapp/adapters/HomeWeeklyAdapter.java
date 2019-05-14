package com.watermark.community_app.communityapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watermark.community_app.communityapp.R;
import com.watermark.community_app.communityapp.data.PostItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeWeeklyAdapter extends RecyclerView.Adapter<HomeWeeklyAdapter.WeeklyViewHolder> {

    private ArrayList<PostItem> entries = new ArrayList<>();
    private HomeWeeklyCallbacks callbacks;

    public void swapData(@NonNull List<PostItem> entries) {
        this.entries.clear();
        this.entries.addAll(entries);
        notifyDataSetChanged();
    }

    public void setCallbacks(HomeWeeklyCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public WeeklyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeeklyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_weekly_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyViewHolder holder, int position) {
        final PostItem postItem = entries.get(position);

        holder.title.setText(entries.get(position).getTitle());
        // TODO: Setting the background to an image is slow. This is causeing an out of memory exception.
        //holder.title.setBackground(entries.get(position).getPostImage());
        holder.title.setBackgroundColor(Color.BLACK);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.onItemClicked(postItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public interface HomeWeeklyCallbacks {
        void onItemClicked(PostItem postItem);
    }

    static class WeeklyViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public WeeklyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.weekly_item_title);
        }
    }
}
