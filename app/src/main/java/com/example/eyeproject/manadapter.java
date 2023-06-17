package com.example.eyeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class manadapter extends BaseAdapter {

    private Context mContext;

    private static List<Integer> productid = new ArrayList<Integer>();
    private static List<String> PType = new ArrayList<String>();
    private static List<String> image = new ArrayList<String>();
    private static List<String> longdesc = new ArrayList<String>();
    private static List<String> pName = new ArrayList<String>();
    private static List<Integer> pPrice = new ArrayList<Integer>();

    public manadapter(mandata mainActivity, List<Integer> productid, List<String> pType, List<String> image, List<String> longdesc, List<String> pName, List<Integer> pPrice) {
        mContext = mainActivity;

        this.productid=productid;
        this.PType=pType;
        this.image=image;
        this.longdesc=longdesc;
        this.pName=pName;
        this.pPrice=pPrice;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return productid.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        grid = new View(mContext);
        grid = inflater.inflate(R.layout.activity_main_activityadpter, null);
        TextView txt_pName = (TextView) grid.findViewById(R.id.txt_pName);
        //TextView txt_pShortDesc = (TextView) grid.findViewById(R.id.txt_pShortDesc);
        TextView txt_price = (TextView) grid.findViewById(R.id.txt_price);
        //TextView txt_pLongDesc = (TextView) grid.findViewById(R.id.txt_pLongDesc);
        //TextView txt_pSize = (TextView) grid.findViewById(R.id.txt_pSize);
        ImageView productImage = (ImageView) grid.findViewById(R.id.productImage);

        //txt_pName.setText(pName.get(position));
        //txt_pShortDesc.setText(shortDesc.get(position));
        txt_price.setText("₹ " + pPrice.get(position).toString());
        //txt_pLongDesc.setText(longdesc.get(position));
        txt_pName.setText(pName.get(position).toString());
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