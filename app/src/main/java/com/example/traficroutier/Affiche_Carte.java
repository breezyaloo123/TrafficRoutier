package com.example.traficroutier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Affiche_Carte extends AppCompatActivity{

private TextView textView;

private Button button;
    private FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche__carte);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        textView = findViewById(R.id.result);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(Affiche_Carte.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(Affiche_Carte.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null)
                        {
                            textView.setText(location.toString());
                        }
                    }
                });
            }
        });

    }

    public void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }


}
