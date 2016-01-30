package com.example.marwaadel.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.marwaadel.test1.datamodel.Mymovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Marwa Adel on 12/25/2015.
 */
public class ImageAdapter extends BaseAdapter{

    Context mcontext;
    ArrayList<Mymovie> movie;
    LayoutInflater inflater;

    public ImageAdapter(Context mcontext, ArrayList<Mymovie> movie) {
        this.mcontext = mcontext;
        this.movie = movie;
        inflater=(LayoutInflater)mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return movie.size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Mymovie getItem(int position) {
        return movie.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Mymovie Movie = getItem(position);
           //String url =movie.get(position);
            ImageView imagemovie;
          if(convertView==null) {
            convertView = inflater.inflate(R.layout.movieitem, null);
        }
        String url= "http://image.tmdb.org/t/p/w185" + Movie.getPosterPath();
        imagemovie= (ImageView)convertView.findViewById(R.id.imageview);
        imagemovie.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mcontext).load(url).fit().into(imagemovie);
//        Log.e("urls", url);


        return convertView;
    }

}
