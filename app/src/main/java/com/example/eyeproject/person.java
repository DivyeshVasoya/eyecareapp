package com.example.eyeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class person extends AppCompatActivity {
    EditText user,address,pincode,number;
    Button Submit;
    String uname,aderss,num;


    int pid,pn;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser mUser;
    FirebaseAuth mauth;
    String uid;
    String userid;
    Integer total, Quntity, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        user=findViewById(R.id.username);
        address=findViewById(R.id.Address);
        pincode=findViewById(R.id.Pincode);
        number=findViewById(R.id.Number);
        database = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();
        mUser=mauth.getCurrentUser();
        uid=mUser.getUid();
        myRef = database.getReference("user");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            total = Integer.valueOf(String.valueOf(extras.getInt("Total")));
            Toast.makeText(this, total.toString(), Toast.LENGTH_SHORT).show();
            Quntity = extras.getInt("Quntity");
            //Toast.makeText(this, Quntity.toString(), Toast.LENGTH_SHORT).show();
            price = extras.getInt("price");
            //Toast.makeText(this, price.toString(), Toast.LENGTH_SHORT).show();
            pid = extras.getInt("pid");
        }


        Submit=findViewById(R.id.submit);
        //myRef2=database.getReference("user");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot zoneSnapshot : snapshot.getChildren()) {
                  //  userid.add(zoneSnapshot.child("pid").getValue(Integer.class));
                    userid = zoneSnapshot.child("uid").getValue(String.class);
                    Toast.makeText(person.this, userid, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = String.valueOf(user.getText());
                aderss = String.valueOf(address.getText());
                num = String.valueOf(number.getText());
                pn = Integer.parseInt(String.valueOf(pincode.getText()));


                pid = pid + 1;

                // address.getText().toString();
                // pincode.getText().toString();
                // number.getText().toString();
                myRef.child(String.valueOf(pid)).child("uid").setValue(uid);
                myRef.child(String.valueOf(pid)).child("uname").setValue(uname);
                myRef.child(String.valueOf(pid)).child("pincode").setValue(pn);
                myRef.child(String.valueOf(pid)).child("number").setValue(num);
                Toast.makeText(person.this, "Data Enter successfully", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putInt("Total", total);
                bundle.putInt("price", price);
                bundle.putInt("Quntity",Quntity);
                bundle.putInt("pid",pid );


                Intent i = new Intent(person.this, payment_gateway.class);
                i.putExtras(bundle);
                startActivity(i);

            }

        });
    }
}