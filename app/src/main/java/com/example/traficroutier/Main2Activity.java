package com.example.traficroutier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Main2Activity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private static final int REQUEST_LOCATION = 123;

    private Marker marker;

    private MapFragment mapFragment;
    private GoogleMap googleMap;

    Double lattitude , longitudee;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FragmentManager fragmentManager = getFragmentManager();

        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);


        Intent intent = getIntent();

       lattitude= intent.getDoubleExtra("latitude",0);
         longitudee=intent.getDoubleExtra("longitude",0);

         loadMap();



    }
    public void checkPermissions()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },REQUEST_LOCATION);

            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        }
        if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,10000,0,this);
        }
        if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,10000,0,this);
        }

        loadMap();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_LOCATION)
        {
            checkPermissions();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(locationManager != null)
        {
            locationManager.removeUpdates(this);
        }
    }

    private void loadMap()
    {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Main2Activity.this.googleMap = googleMap;
                //Allow to the user to do Zooms
                // googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));
                //Enable the location
                googleMap.setMyLocationEnabled(true);
                //get a marker in the location of the user
                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lattitude,longitudee))
                        .title("USER"));
                //show the traffic situation
                googleMap.setTrafficEnabled(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();

        double longitude = location.getLongitude();

        //Toast.makeText(getApplicationContext(),"Coordonnees: "+latitude + "/"+ longitude,Toast.LENGTH_LONG).show();

        if(googleMap != null)
        {
            LatLng googlePosition = new LatLng(latitude,longitude);
            //googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));
            //marker.setPosition(googlePosition);

        }

    }

}
