package com.example.eyeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Product extends AppCompatActivity {
    int proid;
    String Pname;
    int quntity;
    int price;
    String image;
    String  longdec;
    String ptype;
    String gtype;


    Button btnadd;
    int pid=2;
    EditText productId;
    EditText productName;
    EditText productPrice;
    EditText productIMG;
    EditText productQty;
    EditText productDesc;
    EditText productType;
    EditText glasstype;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        productId=findViewById(R.id.productId);
        productName=findViewById(R.id.productname);
        productPrice=findViewById(R.id.productprice);
        productIMG=findViewById(R.id.productimg);
        productQty=findViewById(R.id.productqty);
        productDesc=findViewById(R.id.productdesc);
        productType=findViewById(R.id.producttype);
        glasstype=findViewById(R.id.glassType);



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Product_master");

        btnadd=findViewById(R.id.btnadd);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pid=pid+1;
                proid=Integer.parseInt(String.valueOf(productId.getText()));
                Pname= String.valueOf(productName.getText());
                price=Integer.parseInt(String.valueOf(productPrice.getText()));
                image= String.valueOf(productIMG.getText());
                quntity=Integer.parseInt(String.valueOf(productQty.getText()));
                longdec=String.valueOf(productDesc.getText());
                ptype=String.valueOf(productType.getText());
                gtype=String.valueOf(glasstype.getText());
                //glassstype=String.valueOf(glasstype.getText());
                myRef.child(String.valueOf(pid)).child("pid").setValue(proid);
                myRef.child(String.valueOf(pid)).child("pName").setValue(Pname);
                myRef.child(String.valueOf(pid)).child("pPrice").setValue(price);
                myRef.child(String.valueOf(pid)).child("image").setValue(image);
                myRef.child(String.valueOf(pid)).child("Quntity").setValue(quntity);
                myRef.child(String.valueOf(pid)).child("pLongdes").setValue(longdec);
                myRef.child(String.valueOf(pid)).child("ptype").setValue(ptype);
                myRef.child(String.valueOf(pid)).child("glasstype").setValue(gtype);
                Toast.makeText(getApplicationContext(), "Product added to cart", Toast.LENGTH_SHORT).show();

                 }
        });

    }
}