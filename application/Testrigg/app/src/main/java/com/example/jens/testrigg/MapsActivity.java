package com.example.jens.testrigg;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Coordinate coordinate = null;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_FINE_LOCATION = 1;


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);






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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc,17 ));
                        mMap.addMarker(new MarkerOptions()
                                .position(userLoc));

                        //Pass the coordinates
                        coordinate = new Coordinate(new Double(userLoc.latitude).toString(), new Double(userLoc.longitude).toString());
                        Intent data = new Intent();
                        data.putExtra("Coordinate", coordinate);
                        if (coordinate != null) {
                            setResult(RESULT_OK, data);
                        }

                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(point)
                        .snippet("").title(new Double(point.latitude).toString() + " "
                                + new Double(point.longitude).toString()));
                coordinate = new Coordinate(new Double(point.latitude).toString(), new Double(point.longitude).toString());

                //Pass the coordinates
                Intent data = new Intent();
                data.putExtra("Coordinate", coordinate);
                if (coordinate != null) {
                    setResult(RESULT_OK, data);
                    Log.d("Maps", "Coordinate: " + coordinate.lat + ", " + coordinate.lng);
                }
            }
        });

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

}
