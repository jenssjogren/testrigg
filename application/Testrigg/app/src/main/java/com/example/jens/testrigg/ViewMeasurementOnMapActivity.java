package com.example.jens.testrigg;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewMeasurementOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Coordinate user, gps, google, opencellid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_measurement_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Uri data = intent.getData();
        user = (Coordinate) intent.getSerializableExtra("user");
        gps = (Coordinate) intent.getSerializableExtra("gps");
        google = (Coordinate) intent.getSerializableExtra("google");
        opencellid = (Coordinate) intent.getSerializableExtra("opencellid");
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));


        if(user != null) {
            LatLng userll = coordinateToLatLng(user);
            mMap.addMarker(new MarkerOptions().position(userll).title("User"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userll));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userll,17 ));
        }
        if(gps != null) {
            LatLng gpsll = coordinateToLatLng(gps);
            mMap.addMarker(new MarkerOptions().position(gpsll).title("GPS"));
        }
        if(google != null) {
            LatLng googlell = coordinateToLatLng(google);
            mMap.addMarker(new MarkerOptions().position(googlell).title("Google"));
        }
        if(opencellid != null) {
            LatLng opencellidll = coordinateToLatLng(opencellid);
            mMap.addMarker(new MarkerOptions().position(opencellidll).title("Opencellid"));
        }
    }

    private LatLng coordinateToLatLng(Coordinate coordinate) {
        double lat = Double.valueOf(coordinate.lat);
        double lng = Double.valueOf(coordinate.lng);
        LatLng latLng = new LatLng(lat, lng);
        return latLng;
    }
}
