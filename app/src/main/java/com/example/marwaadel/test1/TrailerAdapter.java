package com.example.marwaadel.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marwaadel.test1.datamodel.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Marwa Adel on 1/13/2016.
 */
public class TrailerAdapter extends BaseAdapter {



    public Context mContext;
    public ArrayList<Trailer> trailer;
    LayoutInflater inflater ;
    public TrailerAdapter(Context mContext, ArrayList<Trailer> mData) {
        this.mContext = mContext;
        this.trailer = mData;
        inflater=(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        if(trailer == null) return 0;
        return trailer.size();
    }

    @Override
    public Trailer getItem(int position) {
        return trailer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //  final Trailer trailer = (Trailer) trailer.get(position);
        final Trailer trailer= getItem(position);
       final ImageView imageView;
       final TextView nameView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.traileritem,null);
        }
        nameView = (TextView) convertView.findViewById(R.id.trailer_name);
        String url= "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";
        imageView= (ImageView)convertView.findViewById(R.id.trailer_image);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mContext).load(url).fit().into(imageView);

        nameView.setText(trailer.getName());
        return convertView;
    }



}
