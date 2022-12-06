package com.example.mikedrop;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiningActivity extends AppCompatActivity {
//  get restaurants with places api
//  TODO:I need to know how to return a list from another thread in run()

    private void updateResterauntList(MapResultArrayAdapter adapter) {
        final String API_KEY = BuildConfig.API_KEY;
        final double lat = 30.407770339447527;
        final double lon = -91.17940238659425;
        final String latLongString = lat + "," + lon;
        final String radius = "5000";

        if (!Places.isInitialized()) {
            // Initialize the SDK
            Places.initialize(getApplicationContext(), API_KEY);
        }

        OkHttpClient client = new OkHttpClient();
        // Create a new PlacesClient instance
//        PlacesClient placesClient = Places.createClient(this);

        String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addQueryParameter("location", latLongString);
        urlBuilder.addQueryParameter("radius", radius);
        urlBuilder.addQueryParameter("type", "restaurant");
        urlBuilder.addQueryParameter("key", API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                final String myResponse = response.body().string();

                DiningActivity.this.runOnUiThread(() -> {
                    try {
                        JSONObject json = new JSONObject(myResponse);
                        int jsonLength = json.getJSONArray("results").length();

                        for (int i = 0; i < jsonLength; i++) {
                            JSONObject result = json.getJSONArray("results").getJSONObject(i);
                            String name = result.getString("name");
                            String address = result.getString("vicinity");
                            double lon = result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                            double lat = result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                            adapter.add(new Restaurants(name, address, lon, lat));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);

        MapResultArrayAdapter adapter = new MapResultArrayAdapter(this, R.layout.restaurant_card);
        updateResterauntList(adapter);
        ListView listView = findViewById(R.id.restaurant_list);
        listView.setAdapter(adapter);
    }
}

class MapResultArrayAdapter extends BaseAdapter {
    private static final String TAG = "dickbutt";
    private List<Restaurants> dataset = new LinkedList<>();
    private Context context;
    private int resource;

    public MapResultArrayAdapter(@NonNull Context context, int resource) {
        this.context = context;
        this.resource = resource;
    }
    
    public void add(Restaurants item){
        dataset.add(item);
    }
    
    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Restaurants getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataset.get(position).hashCode();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        TextView nameView = convertView.findViewById(R.id.label);
        Restaurants restaurant = getItem(position);
        
        nameView.setText(restaurant.getName() + "\n" + restaurant.getAddress());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + restaurant.getLat());
                Log.e(TAG, "onClick: " + restaurant.getLon());

                Intent intent = new Intent("gotothisfuckinglocation");
                intent.putExtra("long", restaurant.getLon());
                intent.putExtra("lat", restaurant.getLat());
                intent.putExtra("markerName", restaurant.getName());

                startActivity(context, intent, null);
            }
        });
        return convertView;
    }
}