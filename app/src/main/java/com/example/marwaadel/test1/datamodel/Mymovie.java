package com.example.marwaadel.test1.datamodel;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.marwaadel.test1.MainActivityFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marwa Adel on 12/25/2015.
 */
public class Mymovie implements Parcelable
{



private String posterPath;



private String overview;

private String releaseDate;
    private Integer id;



private String title;


private String voteAverage;



    public Mymovie(JSONObject movie) throws JSONException {
        this.id = movie.getInt("id");
        this.title=movie.getString("original_title");
        this.overview = movie.getString("overview");
        this.posterPath = movie.getString("poster_path");
        this.voteAverage = movie.getString("vote_average");
        this.releaseDate = movie.getString("release_date");


    }


    private Mymovie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Mymovie> CREATOR = new Creator<Mymovie>() {
        @Override
        public Mymovie createFromParcel(Parcel in) {
            return new Mymovie(in);
        }

        @Override
        public Mymovie[] newArray(int size) {
            return new Mymovie[size];
        }
    };

    public Mymovie(Cursor cursor) {

        this.id = cursor.getInt(MainActivityFragment.COL_MOVIE_ID);
        this.title = cursor.getString(MainActivityFragment.COL_TITLE);
        this.posterPath = cursor.getString(MainActivityFragment.COL_IMAGE);

        this.overview = cursor.getString(MainActivityFragment.COL_OVERVIEW);
        this.voteAverage = cursor.getString(MainActivityFragment.COL_RATING);
        this.releaseDate = cursor.getString(MainActivityFragment.COL_DATE);


    }


    /**
     * @return The posterPath
     */

    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath The poster_path
     */

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }



    /**
     * @return The overview
     */

    public String getOverview() {
        return overview;
    }

    /**
     * @param overview The overview
     */

    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate The release_date
     */

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    /**
     * @return The id
     */

    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */

    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * @return The title
     */

    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */

    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return The voteAverage
     */

    public String getVoteAverage() {
        return voteAverage;
    }

    /**
     * @param voteAverage The vote_average
     */

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
    }


}

