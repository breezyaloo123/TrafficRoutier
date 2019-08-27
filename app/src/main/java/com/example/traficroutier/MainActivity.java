package com.example.traficroutier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.inscription);
        //recuperer l'ID de l'utilisteur

        String id= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        //Create the Map
        Map<String,String> value = new HashMap<>();
        value.put("longitude","12");
        value.put("latittude","9");
        value.put("vitesse","23");


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("utilisateur");
        //push() method will automaticly generate an id
        myRef.push().setValue(id);

        //When we create click on this button an other activity will come
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }
}
