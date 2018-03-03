package com.kubiesit.projekta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button geoButton;
    private LocationManager locationManager;
    private String provider;
    private MyLocationListener mylistener;
    private Criteria criteria;
    private MediaPlayer mp;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetControls();
        geoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
                    criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.
                    criteria.setSpeedRequired(true); // Chose if speed for first location fix is required.
                    criteria.setAltitudeRequired(true); // Choose if you use altitude.
                    criteria.setBearingRequired(false); // Choose if you use bearing.
                    criteria.setCostAllowed(true); // Choose if this provider can waste money :-)

                    provider = LocationManager.NETWORK_PROVIDER;
                    // the last known location of this provider
                    Location location = locationManager.getLastKnownLocation(provider);

                    String lati = "" + location.getLatitude();
                    String longti = "" + location.getLongitude();
                    Toast.makeText(getApplicationContext(), lati + "\n" + longti, Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void GetControls() {
        geoButton = findViewById(R.id.getGeoButton);
    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // Initialize the location fields
            Toast.makeText(MainActivity.this, "" + location.getLatitude() + location.getLongitude(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(MainActivity.this, provider + "'s status changed to " + status + "!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, "Provider " + provider + " enabled!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, "Provider " + provider + " disabled!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

