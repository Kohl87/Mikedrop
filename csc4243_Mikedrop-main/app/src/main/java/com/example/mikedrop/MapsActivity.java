package com.example.mikedrop;

import androidx.fragment.app.FragmentActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.renderscript.Element;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mikedrop.databinding.ActivityMapsBinding;

import kotlinx.coroutines.channels.BroadcastChannel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private double lat = 30.407770339447527d;
    private double lon = -91.17940238659425d;
    private String markerName = "Patrick F. Taylor";

    public MapsActivity() {
        Log.i(TAG, "MapsActivity: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            Log.i(TAG, "onCreate: something started this application");
            lon = intent.getDoubleExtra("long", -91.17940238659425d);
            lat = intent.getDoubleExtra("lat", 30.407770339447527d);
            markerName = intent.getStringExtra("markerName");

            //-91.17940238659425    30.407770339447527
            //We need map here
        }

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng centerMarker = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(centerMarker, 17f, 0f, 90f)));
        mMap.addMarker(new MarkerOptions().position(centerMarker).title(markerName));
        Log.i(TAG, "onMapReady: " + mMap.getCameraPosition().target.toString());
    }
}