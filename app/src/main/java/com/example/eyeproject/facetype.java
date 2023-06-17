package com.example.eyeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class facetype extends AppCompatActivity {
    String data;
    String[] City = {
            "oval",
            "round",
            "square",
            "diamond",
            "heart",
            "pear",
            "oblong",

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facetype);
        Spinner spinnerLanguages = findViewById(R.id.spinner);
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, City);
        spinnerLanguages.setAdapter(ada);
        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("item", (String) parent.getItemAtPosition(position));
                data = (String) parent.getItemAtPosition(position);
                Toast.makeText(facetype.this, data, Toast.LENGTH_SHORT).show();
                Intent ik=new Intent(facetype.this,facedata.class);
                ik.putExtra("dat",data);
                startActivity(ik);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


}