package com.example.eyeproject;

import static android.content.ContentValues.TAG;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Homepage extends AppCompatActivity  {

    EditText edtSearch;
    public static  Button btn;
    private LinearLayout mylayout=null;
    ImageView iglass,igogles,icomputerglass;
    Button btnSearch,btnman,btnwoman,btnkids;
    private Context context;
    FirebaseAuth mAuth;
    //String searchName = "";
    private static List<Integer> productid = new ArrayList<Integer>();

    private static List<String> image = new ArrayList<String>();
    private static List<String> longdesc = new ArrayList<String>();
    private static List<String> pName = new ArrayList<String>();
    private static List<String> pType = new ArrayList<String>();
    private static List<Integer> pPrice = new ArrayList<Integer>();
    private static List<Integer> p = new ArrayList<Integer>();


    FirebaseDatabase database;
    DatabaseReference myRef;
    GridView grid;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
       // mActivity = Homepage.this;

        mylayout=(LinearLayout) findViewById(R.id.idRLHome);
        grid=findViewById(R.id.homeactivity);
        edtSearch=findViewById(R.id.edtSearch);
        btnSearch=findViewById(R.id.btnSearch);
        btnman=findViewById(R.id.btnman);
        btnwoman=findViewById(R.id.btnwoman);
        btnkids=findViewById(R.id.btnkids);
        icomputerglass=findViewById(R.id.eyecom);
        iglass=findViewById(R.id.eyegl);
        mylayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(),"touch",Toast.LENGTH_SHORT).show();
                showMessage(" Do you Want to see Face Type Suggestion");


                return true;
            }
        });

        igogles=findViewById(R.id.eyego);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSearch.getText() != null) {
                    if (edtSearch.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Nothing to search", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(Homepage.this, SearchActivity.class);
                        i.putExtra("searchName", edtSearch.getText().toString());

                        startActivity(i);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nothing to search", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent im=new Intent(Homepage.this,womandata.class);
                im.putExtra("data","man");
                startActivity(im);
            }
        });
        btnwoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iw=new Intent(Homepage.this,womandata.class);
                iw.putExtra("data","woman");
                startActivity(iw);
            }
        });
        btnkids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik=new Intent(Homepage.this,womandata.class);
                ik.putExtra("data","kids");
                startActivity(ik);
            }
        });
        icomputerglass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ceye=new Intent(Homepage.this,mandata.class);
                ceye.putExtra("edata","computerglasses");
                startActivity(ceye);
            }
        });
        igogles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geye=new Intent(Homepage.this,mandata.class);
                geye.putExtra("edata","goggles");
                startActivity(geye);
            }
        });
        iglass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ieye=new Intent(Homepage.this,mandata.class);
                ieye.putExtra("edata","eyeglasses");
                startActivity(ieye);
            }
        });
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Product_master");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productid.clear();
                longdesc.clear();
                image.clear();
                pName.clear();
                pPrice.clear();
                pType.clear();

                //int id = 1;


                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren())

                {
                    p.add(zoneSnapshot.child("pid").getValue(Integer.class));


                    productid.add(zoneSnapshot.child("pid").getValue(Integer.class));
                    pType.add(zoneSnapshot.child("ptype").getValue(String.class));
                    image.add(zoneSnapshot.child("image").getValue(String.class));
                    longdesc.add(zoneSnapshot.child("pLongdes").getValue(String.class));
                    pName.add(zoneSnapshot.child("pName").getValue(String.class));
                    pPrice.add(zoneSnapshot.child("pPrice").getValue(Integer.class));




                }



                setAdapter();

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

        mainActivityadpter a=new mainActivityadpter(Homepage.this,productid,pType,image,longdesc,pName,pPrice);
        grid.setAdapter(a);
        //Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, a.toString(), Toast.LENGTH_SHORT).show();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String st = ((TextView)view.findViewById(R.id.).getText().toString();
                //Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Homepage.this,product.class);
                intent.putExtra("id",p.get(i));
                startActivity(intent);
//                Intent ia=new Intent(getApplicationContext(),product.class);
//                        ia.putExtra("search", (Parcelable) productid);
//                        startApctivity(ia);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.lin:
                Intent intent2 = new Intent(Homepage.this, MainActivity.class);
                //intent.putExtra("id", (spi));
                startActivity(intent2);
                return true;
            case R.id.yocrt:

                    Intent intent = new Intent(Homepage.this, showdata.class);
                    //intent.putExtra("id", (spi));
                    startActivity(intent);

                return true;

            case R.id.lout:
                Intent inten = new Intent(Homepage.this, MainActivity.class);
                //intent.putExtra("id", (spi));
                startActivity(inten);
                    Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void showMessage(String message) {
        // Create and show an AlertDialog with the specified message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(Homepage.this,facetype.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });





        builder.create().show();
    }
}