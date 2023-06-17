package com.example.eyeproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class payment_gateway extends AppCompatActivity  implements PaymentResultListener {
    int i = 1;
    String amoun="";
    Button btpay;
    String uid;
    FirebaseDatabase database;
    DatabaseReference myRef, myref1;
    FirebaseUser mUser;
    Integer total, Quntity, price;
    FirebaseAuth mauth;
    Integer  Pincode, pid;
    String uname, address,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(getApplicationContext());
        setContentView(R.layout.activity_payment_gateway);
        btpay = findViewById(R.id.bt_pay);
        mauth = FirebaseAuth.getInstance();
        mUser = mauth.getCurrentUser();
        uid = mUser.getUid();
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
        database = FirebaseDatabase.getInstance();
        myref1 = database.getReference("user");
        myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //int id = 1;


                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    uname = zoneSnapshot.child("uid").getValue(String.class);
                    number = zoneSnapshot.child("number").getValue(String.class);
                    Pincode = zoneSnapshot.child("pincode").getValue(Integer.class);
                    address = zoneSnapshot.child("address").getValue(String.class);


                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                // Toast.makeText(MainActivity.this, "Azhar", Toast.LENGTH_SHORT).show();
            }
        });
        amoun=String.valueOf(total);
       String sa="4000";
       Integer amount;

         amount = Math.round(Float.parseFloat(amoun)*100);
        Toast.makeText(this, amount.toString(), Toast.LENGTH_SHORT).show();

        btpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_rblP8rFNkXyttl");
                checkout.setImage(R.drawable.rlogo);
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                JSONObject object = new JSONObject();
                try {
                    object.put("name", name);
                    object.put("description", "Payment to Eye care");
                    object.put("theme.color", "#0093DD");
                    object.put("currency", "INR");

                    object.put("prefill.contact", number);
                    checkout.open(payment_gateway.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

   // private void PaymentNow(String amount) {
       // final Activity activity = this;

   // }

    @Override
    public void onPaymentSuccess(String s) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(new Date());
            //SharedPreferences sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
            Toast.makeText(payment_gateway.this, "Payment Successfull", Toast.LENGTH_SHORT).show();
            database = FirebaseDatabase.getInstance();
            //String email=sharedPreferences.getString("userid","");


            myRef = database.getReference("tbl_order");
            myRef.child(String.valueOf(pid)).child("totalamount").setValue(amoun);
            myRef.child(String.valueOf(pid)).child("customerid").setValue(uid);
            myRef.child(String.valueOf(pid)).child("date").setValue(date);
            myRef.child(String.valueOf(pid)).child("deliveryaddrss").setValue(address);
            myRef.child(String.valueOf(pid)).child("productid").setValue(pid);
            myRef.child(String.valueOf(pid)).child("quantity").setValue(String.valueOf(Quntity));
            Toast.makeText(payment_gateway.this, "Your order is placed", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(payment_gateway.this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show();

    }
}
