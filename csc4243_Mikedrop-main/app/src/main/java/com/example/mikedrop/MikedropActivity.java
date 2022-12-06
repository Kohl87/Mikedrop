package com.example.mikedrop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MikedropActivity extends AppCompatActivity {
    private ImageButton mapButton;
    private ImageButton eventsButton;
    private ImageButton diningButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapButton = (ImageButton) findViewById(R.id.mapButton);
        eventsButton = (ImageButton) findViewById(R.id.eventsButton);
        diningButton = (ImageButton) findViewById(R.id.diningButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MikedropActivity.this, MapsContainer.class));
            }
        });

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://calendar.lsu.edu/";
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url)));
            }
        });

        diningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MikedropActivity.this, DiningContainer.class));
            }
        });
    }
}