package com.example.eyeproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showdata extends AppCompatActivity {
    private static List<Integer> productid = new ArrayList<Integer>();
    private static List<String> image = new ArrayList<String>();
    private static List<Integer> Qunity = new ArrayList<>();
    private static List<Integer> pPrices = new ArrayList<Integer>();
    private static List<Integer> price_total = new ArrayList<Integer>();
    private static List<String> Data = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button btnbuynow;
    GridView grid;
    Integer p;
    String u;
    FirebaseUser mUser;
    FirebaseAuth mauth;
    String uid;
    Integer q;
    int total=0;
    int data=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);
        grid=findViewById(R.id.productGridshow);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("tbl_cart");
        mauth = FirebaseAuth.getInstance();
        mUser=mauth.getCurrentUser();
        uid=mUser.getUid();
        btnbuynow=findViewById(R.id.btnbnow);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productid.clear();
                image.clear();
                Qunity.clear();
                Data.clear();
                price_total.clear();
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {

                    String userid = zoneSnapshot.child("user").getValue(String.class);
                  //  Toast.makeText(showdata.this, userid, Toast.LENGTH_SHORT).show();


                    if(userid.equals(uid)) {
                        //Toast.makeText(showdata.this, "data", Toast.LENGTH_SHORT).show();

                        u = zoneSnapshot.child("image").getValue(String.class);

                        p  = zoneSnapshot.child("pid").getValue(Integer.class);
                        q  = zoneSnapshot.child("quantity").getValue(Integer.class);



                        productid.add(zoneSnapshot.child("pid").getValue(Integer.class));
                        total = total +q* zoneSnapshot.child("price").getValue(Integer.class);
                        data=data+total;
                        //Toast.makeText(showdata.this, total, Toast.LENGTH_SHORT).show();

                        image.add(zoneSnapshot.child("image").getValue(String.class));
                        Qunity.add(zoneSnapshot.child("quantity").getValue(Integer.class));
                        pPrices.add(zoneSnapshot.child("price").getValue(Integer.class));
                    } else if (userid==null) {

                        Toast.makeText(showdata.this, "data", Toast.LENGTH_SHORT).show();

                        u = zoneSnapshot.child("image").getValue(String.class);
                        p  = zoneSnapshot.child("pid").getValue(Integer.class);

                        productid.add(zoneSnapshot.child("pid").getValue(Integer.class));


                        image.add(zoneSnapshot.child("image").getValue(String.class));
                        Qunity.add(zoneSnapshot.child("quantity").getValue(Integer.class));
                        pPrices.add(zoneSnapshot.child("price").getValue(Integer.class));
                    }else{
                        Toast.makeText(showdata.this, "First you Login", Toast.LENGTH_SHORT).show();
                    }
                    price_total.add(total);

                }

//                Toast.makeText(showdata.this, price_total.toString(), Toast.LENGTH_SHORT).show();
                setAdapter();
                btnbuynow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                // Toast.makeText(MainActivity.this, "Azhar", Toast.LENGTH_SHORT).show();
            }


        });



    }
    private void setAdapter() {
         showadpater showadpater=new showadpater(showdata.this,productid,image,Qunity,pPrices,u,p,price_total);
        grid.setAdapter(showadpater);



    }
}