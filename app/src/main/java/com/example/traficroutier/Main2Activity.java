package com.example.traficroutier;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {
    private TextView textView;

    private RadioButton male_check;

    private RadioButton female_check;

    private LinearLayout color;

    private Button button;
    private EditText editText;

    public String age1;
    public String gender;
    public ArrayList<Utilisateur> value;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

            textView = findViewById(R.id.edit);

            editText = findViewById(R.id.input);

            button = findViewById(R.id.create_button);

        value = new ArrayList<>();

//getting device ID
        final String id= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        textView.setText(id);


        male_check = findViewById(R.id.male);

        female_check = findViewById(R.id.female);

        color = findViewById(R.id.back);

        male_check.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(Main2Activity.this,"good",Toast.LENGTH_LONG).show();
            }
        });

        female_check.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(Main2Activity.this,"Sad",Toast.LENGTH_LONG).show();
            }
        });

        //recuperer les valeurs saisie

        String id_imei=textView.getText().toString();


          age1 = editText.getText().toString();
            gender = checkedValue();
        //Create the Map



        Utilisateur user = new Utilisateur("23","30","25",gender,editText.getText().toString());

        value.add(user);



        //when we click in the create button

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("utilisateur");
                myRef.child(id).setValue(value);

                Intent intent=new Intent(Main2Activity.this,Affiche_Carte.class);

                startActivity(intent);


            }
        });


    }

    public String checkedValue()
    {
        String sexe = null;
        if (male_check.isChecked()) {
            sexe = male_check.getText().toString();
        } else if (female_check.isChecked()) {
            sexe = female_check.getText().toString();
        } else {
            Toast.makeText(getApplicationContext(), "Veuillez selectionner un type", Toast.LENGTH_LONG).show();
        }
        return sexe;
    }


    }

