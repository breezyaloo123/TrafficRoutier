package com.example.traficroutier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.GeoApiContext;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private static final int REQUEST_LOCATION = 1;
    private RadioButton male,female;

    private String latitude,longitude;

    private Double lat,longi;

    private LocationManager locationManager;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add permissions
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION);
        editText = findViewById(R.id.input);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        button = findViewById(R.id.create_button);
        OnGPS();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPost();
                ProvideLocation();
            }
        });

    }

    public void ProvideLocation()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Check if gps is enable or not

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write function which enable it

            OnGPS();
        }
        else
        {
            //Gps is already On then

            getLocation();
        }
    }

    private void getLocation() {

        //Check permissions again

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //Add permissions
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else
        {
            Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if(locationGps != null)
            {
                 lat=locationGps.getLatitude();
                 longi = locationGps.getLongitude();

                latitude = String.valueOf(lat);

                longitude = String.valueOf(longi);


            }
            else if(locationNetwork != null)
            {
                Double lat=locationNetwork.getLatitude();
                Double longi = locationNetwork.getLongitude();

                latitude = String.valueOf(lat);

                longitude = String.valueOf(longi);

            }
            else if(locationPassive != null)
            {
                Double lat=locationPassive.getLatitude();
                Double longi=locationPassive.getLongitude();

                latitude = String.valueOf(lat);

                longitude = String.valueOf(longi);

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Can't get your location",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void OnGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }



    public void LoginPost()
    {
        //final String sexe = editText.getText().toString();

        //verifier la validation des inputs
        if(TextUtils.isEmpty(editText.getText().toString()))
        {
            editText.setError("Please enter an correct AGE");
            editText.requestFocus();
        }
        else
        {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://d47f524c.ngrok.io/Traffic/send.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                    intent.putExtra("latitude",lat);
                    intent.putExtra("longitude",longi);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),"Verify the fields",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //final String id=Imei();
                    Map<String,String> param = new HashMap<>();
                    param.put("imei",Imei());
                    param.put("sexe",checked());
                    param.put("Ages",editText.getText().toString());
                    param.put("longitude",longitude);
                    param.put("latitude",latitude);


                    return param;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
        }

    }
    //Recuperer les radios

    public String checked()
    {
        String result=null;
        if(male.isChecked())
        {
            result=male.getText().toString();
        }
        else if (female.isChecked())
        {
            result=female.getText().toString();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Veuillez choisir le sexe",Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    public String Imei()
    {
        String ImeiValue =  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        return ImeiValue;
    }







}
