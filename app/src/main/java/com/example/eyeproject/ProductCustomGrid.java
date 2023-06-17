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

public class ProductCustomGrid extends BaseAdapter {
    private Context mContext;

    private static List<Integer> productid = new ArrayList<Integer>();
    private static List<Integer> brandid = new ArrayList<Integer>();
    private static List<String> image = new ArrayList<String>();
    private static List<String> longdesc = new ArrayList<String>();
    private static List<String> pName = new ArrayList<String>();
    private static List<Integer> pPrice = new ArrayList<Integer>();

    public ProductCustomGrid(SearchActivity searchActivity, List<Integer> productid, List<String> image, List<String> longdesc, List<String> pName, List<Integer> pPrice) {
        mContext = searchActivity;

        this.productid = productid;
        this.image = image;
        this.longdesc = longdesc;
        this.pName = pName;
        this.pPrice = pPrice;
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
        grid = inflater.inflate(R.layout.activity_product_custom_grid, null);
        TextView txt_pName = (TextView) grid.findViewById(R.id.txt_pNamegrid);
        TextView txt_price = (TextView) grid.findViewById(R.id.txt_pricegrid);
        TextView txt_pLongDesc = (TextView) grid.findViewById(R.id.txt_pLongDescgrid);
        ImageView productImage = (ImageView) grid.findViewById(R.id.searchproductImage);

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