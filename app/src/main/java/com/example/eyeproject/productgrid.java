package com.example.eyeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class productgrid extends BaseAdapter{
    private Context mContext;

    private static List<Integer> productid = new ArrayList<Integer>();
    private static List<Integer> brandid = new ArrayList<Integer>();
    private static List<String> image = new ArrayList<String>();
    private static List<String> longdesc = new ArrayList<String>();
    private static List<String> pName = new ArrayList<String>();
    private static List<Integer> pPrice = new ArrayList<Integer>();

    public productgrid(product product, List<Integer> productid, List<String> image, List<String> longdesc, List<String> pName, List<Integer> pPrice) {
        mContext = product;

        this.productid = productid;
        // this.brandid = brandid;
        this.image = image;
        this.longdesc = longdesc;
        this.pName = pName;
        this.pPrice = pPrice;
    }


    @Override
    public int getCount() {
        return productid.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        grid = new View(mContext);
        grid = inflater.inflate(R.layout.activity_productgrid, null);
        TextView txt_pName = (TextView) grid.findViewById(R.id.txt_pNamegrid);
        TextView txt_price = (TextView) grid.findViewById(R.id.txt_pricegrid);
        ImageView productImage = (ImageView) grid.findViewById(R.id.productImage);
        TextView txt_pLongDesc = (TextView) grid.findViewById(R.id.txt_pLongDescgrid);

        txt_pName.setText(pName.get(position));
        txt_price.setText("₹ " + pPrice.get(position).toString());
        txt_pLongDesc.setText(longdesc.get(position));
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