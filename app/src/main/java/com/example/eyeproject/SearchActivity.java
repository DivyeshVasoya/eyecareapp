package com.example.eyeproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    String searchName = "";
    private static List<Integer> productid = new ArrayList<Integer>();
    private static List<String> image = new ArrayList<String>();
    private static List<String> longdesc = new ArrayList<String>();
    private static List<String> pName = new ArrayList<String>();
    private static List<Integer> pPrice = new ArrayList<Integer>();

    FirebaseDatabase database;
    DatabaseReference myRef;
    GridView grid;
    private static List<Integer> p = new ArrayList<Integer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        grid = (GridView) findViewById(R.id.productGrid);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            searchName = extras.getString("searchName").toLowerCase(Locale.ROOT);
            //Toast.makeText(getApplicationContext(), searchName, Toast.LENGTH_SHORT).show();
        }

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
                int id = 1;

                for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                    String productname = zoneSnapshot.child("pName").getValue(String.class).toLowerCase(Locale.ROOT);
                    String productlongdesc = zoneSnapshot.child("pLongdes").getValue(String.class).toLowerCase(Locale.ROOT);
                    // In If-else statements you can use the contains() method



                    if (productname.contains(searchName) || productlongdesc.contains(searchName) ) {

                        productid.add(id);
                        id++;
                        image.add(zoneSnapshot.child("image").getValue(String.class));
                        longdesc.add(zoneSnapshot.child("pLongdes").getValue(String.class));
                        pName.add(zoneSnapshot.child("pName").getValue(String.class));
                        pPrice.add(zoneSnapshot.child("pPrice").getValue(Integer.class));
                        p.add(zoneSnapshot.child("pid").getValue(Integer.class));





                    }

                    else {
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
    }

//    private class CustomAdaptor extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return productid.toArray().length;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            View view1 = getLayoutInflater().inflate(R.layout.activity_product,null);
//            TextView name = view1.findViewById(R.id.txt_pName);
//            return  view1;
//        }
//    }

    private void setAdapter() {
        ProductCustomGrid adapter = new ProductCustomGrid(SearchActivity.this, productid,  image, longdesc, pName, pPrice);

        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //String st = ((TextView)view.findViewById(R.id.).getText().toString();
                //Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SearchActivity.this,product.class);
              //  Toast.makeText(SearchActivity.this, p.toString(), Toast.LENGTH_SHORT).show();



                    intent.putExtra("id", p.get(i));
                    startActivity(intent);
//                Toast.makeText(getApplicationContext(), "Azhar", Toast.LENGTH_SHORT).show();
//                Intent ia=new Intent(getApplicationContext(),product.class);
//                        ia.putExtra("search", (Parcelable) productid);
//                        startActivity(ia);

            }
        });

    }
}