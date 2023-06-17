package com.example.eyeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class showadpater extends BaseAdapter {
    private Context mContext;



    private static List<Integer> productid = new ArrayList<Integer>();

    private static List<String> image = new ArrayList<String>();
    private static List<Integer> Quntity = new ArrayList<>();
    private static List<Integer> pPrice = new ArrayList<Integer>();
    private static List<Integer> uid = new ArrayList<Integer>();
    private static List<Integer> price_total = new ArrayList<Integer>();
    // private static List<String> data = new ArrayList<String>();
    String u;
    Integer p;

    public showadpater(showdata showdata, List<Integer> productid, List<String> image, List<Integer> qunity, List<Integer> pPrices, String u, Integer p, List<Integer> price_total) {

        mContext = showdata;

        this.productid = productid;
        // this.brandid = brandid;
        this.image = image;
        this.Quntity=qunity;
        this.pPrice=pPrices;
        this.u=u;
        this.p=p;
        this.price_total= price_total;



    }


    @Override
    public int getCount() {
        return  productid.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        grid = new View(mContext);
        grid = inflater.inflate(R.layout.activity_showadpater, null);
        //TextView txt_pName = (TextView) grid.findViewById(R.id.txt_pName);
        //TextView txt_pShortDesc = (TextView) grid.findViewById(R.id.txt_pShortDesc);
        TextView txt_quntity = (TextView) grid.findViewById(R.id.txt_quntityshow);
        // TextView txt_pLongDesc = (TextView) grid.findViewById(R.id.txt_pLongDesc);
        TextView txt_pPrice = (TextView) grid.findViewById(R.id.txt_priceshow);
        TextView txt_total = (TextView) grid.findViewById(R.id.txt_total);
        ImageView productImage = (ImageView) grid.findViewById(R.id.productImage);
        Button btnremove=(Button)grid.findViewById(R.id.Remove);

        // txt_pName.setText(pName.get(position));
        // txt_pShortDesc.setText(shortDesc.get(position));
        txt_quntity.setText( Quntity.get(position).toString());
        txt_total.setText( price_total.get(position).toString());
        //  txt_pLongDesc.setText(longdesc.get(position));
        txt_pPrice.setText("₹ " +pPrice.get(position).toString());
        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database;
                DatabaseReference myRef;
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("tbl_cart");
                myRef.child(String.valueOf(p)).child("quantity").removeValue();
                myRef.child(String.valueOf(p)).child("date").removeValue();
                myRef.child(String.valueOf(p)).child("user").removeValue();
                myRef.child(String.valueOf(p)).child("pid").setValue(null);
                myRef.child(String.valueOf(p)).child("image").setValue(null);
                myRef.child(String.valueOf(p)).child("price").setValue(null);
            }
        });
        String images = image.get(position);

        Glide.with(mContext)
                .load(images)
                .error(R.drawable.no_image_available1)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .centerCrop()
                .into(productImage);

        System.out.println("Iiiiiiiimmmmmmaaaaagggggggeeeee: " + images);
        return grid;


    }
}