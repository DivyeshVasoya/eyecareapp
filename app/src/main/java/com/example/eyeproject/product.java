package com.example.eyeproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class product extends AppCompatActivity {
    private static List<Integer> productid1 = new ArrayList<Integer>();
    //private static List<Integer> brandid = new ArrayList<Integer>();
    private static List<String> image = new ArrayList<String>();
    private static List<String> longdesc = new ArrayList<String>();
    private static List<String> pName = new ArrayList<String>();
    private static List<Integer> pPrice = new ArrayList<Integer>();
    private static List<Integer> Total = new ArrayList<Integer>();
    private static int qty ;
    private static int p ;
    private static String i ;
    private static int pr ;
    FirebaseUser mUser;
    FirebaseAuth mauth;

    Integer id=3;
    int addpro=1;
    TextView que;

    Button Addcart,Plus,minus,buynow;
    EditText qty1;
    //private static List<Integer> sellerId = new ArrayList<Integer>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef1,myRef2;
    GridView grid;
    String userid;
    String uid;
    int total=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        mauth = FirebaseAuth.getInstance();
        mUser=mauth.getCurrentUser();
        uid=mUser.getUid();
        grid=findViewById(R.id.productgridview);
        Bundle extras = getIntent().getExtras();
        int spi=extras.getInt("id");
        buynow=findViewById(R.id.buynow);
        Toast.makeText(this, String.valueOf(spi), Toast.LENGTH_SHORT).show();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Product_master");
        myRef1 = database.getReference("tbl_cart");
        myRef2= database.getReference("user");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot zoneSnapshot : snapshot.getChildren()) {
                    //  userid.add(zoneSnapshot.child("pid").getValue(Integer.class));
                    userid = zoneSnapshot.child("uid").getValue(String.class);
                    Toast.makeText(product.this, userid, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productid1.clear();
                longdesc.clear();
                image.clear();
                pName.clear();
                pPrice.clear();
                //sellerId1.clear();



                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    Integer productid=zoneSnapshot.child("pid").getValue(Integer.class);
                    //          Toast.makeText(product.this, productid.toString(), Toast.LENGTH_SHORT).show();
                    //String prodductname=productid1.
                    //String productname = zoneSnapshot("productid").getValue(String.class).toLowerCase(Locale.ROOT);
                    // String productlongdesc = zoneSnapshot.child("productlongdesc").getValue(String.class).toLowerCase(Locale.ROOT);
                    //String productsortdesc = zoneSnapshot.child("productsortdesc").getValue(String.class).toLowerCase(Locale.ROOT);
                    // In If-else statements you can use the contains() method
                    String s=String.valueOf(productid);
                     userid = zoneSnapshot.child("uid").getValue(String.class);
                    //Toast.makeText(product.this, s, Toast.LENGTH_SHORT).show();
                   // Toast.makeText(product.this, spi, Toast.LENGTH_SHORT).show();


                    if (s.contains(String.valueOf(spi)))
                    {

                        productid1.add(id);
                        id++;
                        //brandid1.add(zoneSnapshot.child("brandid").getValue(Integer.class));
                        image.add(zoneSnapshot.child("image").getValue(String.class));
                        longdesc.add(zoneSnapshot.child("pLongdes").getValue(String.class));
                        pName.add(zoneSnapshot.child("pName").getValue(String.class));
                        pPrice.add(zoneSnapshot.child("pPrice").getValue(Integer.class));
                       // pSize.add(zoneSnapshot.child("productsize").getValue(Double.class));
                       // shortDesc.add(zoneSnapshot.child("productsortdesc").getValue(String.class));
                        //sellerId1.add(zoneSnapshot.child("sellerid").getValue(Integer.class));
                        qty=zoneSnapshot.child("Quntity").getValue(Integer.class);
                        p=zoneSnapshot.child("pid").getValue(Integer.class);
                        pr=zoneSnapshot.child("pPrice").getValue(Integer.class);
                        i=zoneSnapshot.child("image").getValue(String.class);
                        total=total+addpro*pr;
                        que.setText(String.valueOf(qty));
                        int T;
                        T=qty*pr;
                        Total.add(T);

                    }
                    else
                    {
                        //  Toast.makeText(product.this, "Azhar", Toast.LENGTH_SHORT).show();
                        System.out.println("The Keyword :example: is not found in the string");
                    }
                }
                setAdapter();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        que=findViewById(R.id.que);
        que.setText(String.valueOf(qty));
        qty1=findViewById(R.id.data);
        qty1.setText(String.valueOf(addpro));
        Plus=findViewById(R.id.btnplus);
        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(qty==addpro)
                {
                    //  Toast.makeText(product.this, "Qunity is more", Toast.LENGTH_SHORT).show();
                    qty1.setText(String.valueOf(addpro));
                }
                else{
                    addpro++;
                    //  data.setText(addpro);
                    qty1.setText(String.valueOf(addpro));
                }



            }
        });
        minus=findViewById(R.id.btnminus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addpro>1) {
                    addpro--;
                }
                qty1.setText(String.valueOf(addpro));

            }
        });



        Addcart=findViewById(R.id.addcart);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        int productid = spi;

        Addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // SharedPreferences sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
               // if(sharedPreferences.getString("userid","").isEmpty()){
                    //Intent intent = new Intent(product.this,LoginActivity.class);
               //     Intent intent = new Intent(product.this,mainActivityadpter.class);
               //     startActivity(intent);
               // }else {

                   // SharedPreferences sharedPreferences1 = getSharedPreferences("mypref",MODE_PRIVATE);
                   // String userid=sharedPreferences1.getString("userid","");
                    myRef1.child(String.valueOf(p)).child("date").setValue(date);
                    myRef1.child(String.valueOf(p)).child("pid").setValue(productid);
                    myRef1.child(String.valueOf(p)).child("quantity").setValue(addpro);
                    myRef1.child(String.valueOf(p)).child("image").setValue(i);
                    myRef1.child(String.valueOf(p)).child("price").setValue(pr);
                    myRef1.child(String.valueOf(p)).child("user").setValue(uid);
                  //  myRef1.child(String.valueOf(p)).child("uid").setValue(userid);
                    Toast.makeText(getApplicationContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(product.this, showdata.class);
                intent.putExtra("id", (spi));
                startActivity(intent);
                    // firestore.collection("tbl_cart").document(auth.getCurrentUser().getUid());


                    //  Intent in=new Intent(product.this,autocad.class);
                    // in.putExtra("id",String.valueOf(spi));
                    //startActivity(in);
                //}
            }
        });
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(uid.equals(userid)){
                    Toast.makeText(product.this, "you alredy Ragiseter", Toast.LENGTH_SHORT).show();
                    //Intent tn=new Intent(product.this,payment_gateway.class);
                    //tn.putExtra("total",total);

                    //startActivity(tn);
                    Bundle bundle = new Bundle();
                    bundle.putInt("Total", total);
                    bundle.putInt("price", pr);
                    bundle.putInt("Quntity",addpro);
                    bundle.putInt("pid",p );


                    Intent i = new Intent(product.this, payment_gateway.class);
                    i.putExtras(bundle);
                    startActivity(i);
                } else if (userid==null) {
                    Intent i=new Intent(product.this,person.class);
                    startActivity(i);

                }
            }
        });


    }
    private void setAdapter() {
        // ProductCustomGrid adter=new ProductCustomGrid(product.this,productid,image,longdesc,pName,pPrice,pSize,shortDesc);
        productgrid adpater=new productgrid(product.this,productid1,image,longdesc,pName,pPrice);
        grid.setAdapter(adpater);
    }
}