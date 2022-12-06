package com.example.mikedrop;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class restaurantViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView address;
    View view;

    restaurantViewHolder(View itemView) {
        super (itemView);
//        name = (TextView) itemView.findViewById(R.id.name);
//        address = (TextView) itemView.findViewById(R.id.address);
        view = itemView;
    }
}
