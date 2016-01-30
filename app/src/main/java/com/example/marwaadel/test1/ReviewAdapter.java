package com.example.marwaadel.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marwaadel.test1.datamodel.Review;

import java.util.ArrayList;

/**
 * Created by Marwa Adel on 1/13/2016.
 */
public class ReviewAdapter extends BaseAdapter {


    public Context mContext;
    public ArrayList<Review> review;
    LayoutInflater inflater ;
    public ReviewAdapter(Context mContext, ArrayList<Review> review) {
        this.mContext = mContext;
        this.review = review;
        inflater=(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return review.size();
    }

    @Override
    public Review getItem(int position) {
        return review.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final TextView authorView;
        final TextView contentView;
        final Review review= getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.reviewitem,null);
        }
        authorView = (TextView) convertView.findViewById(R.id.review_author);
        contentView = (TextView) convertView.findViewById(R.id.review_content);
        authorView.setText(review.getAuthor());
        contentView.setText(review.getContent());

        return convertView;
    }
}
