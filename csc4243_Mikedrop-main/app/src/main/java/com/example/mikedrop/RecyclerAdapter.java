package com.example.mikedrop;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<restaurantViewHolder> {
    List<Restaurants> list = Collections.emptyList();
    Context context;

    public RecyclerAdapter(List<Restaurants> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public restaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View photoView = inflater.inflate(R.layout.restaurant_card, parent, false);
        restaurantViewHolder viewHolder = new restaurantViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final restaurantViewHolder viewHolder, final int position) {
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.name.setText(list.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
