package com.example.marwaadel.test1;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.marwaadel.test1.data.MovieContract;
import com.example.marwaadel.test1.datamodel.Mymovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private int mPosition = GridView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";
    ImageAdapter mMovieAdapter;
    ArrayList<String> list = new ArrayList<>();
    GridView g;
    String SortBy;
//    private static final String favourite = "favorite";

    TrailerAdapter mTrailerAdapter;
    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_IMAGE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_DATE
    };

    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_IMAGE = 3;
    public static final int COL_OVERVIEW = 4;
    public static final int COL_RATING = 5;
    public static final int COL_DATE = 6;


    public MainActivityFragment() {
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        void onItemSelected(Mymovie movie);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != GridView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }


    private void updateMovie(String sortBy) {
//        FetchMovieTask movieTask = new FetchMovieTask();
//        movieTask.execute();
        if (!sortBy.contentEquals("favourite")) {
            new FetchMovieTask().execute(sortBy);
        } else {
            new FetchFavoriteMoviesTask(getActivity()).execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        g = (GridView) rootView.findViewById(R.id.gridview);
        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Mymovie m = mMovieAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(m);
                //String forecast = mMovieAdapter.getItem(position);
//                Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();

                //  Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("object",m);

                //  .putExtra(Intent.EXTRA_TEXT, forecast);
                //  startActivity(intent);
                mPosition = position;
            }
        });

        // If there's instance state, mine it for useful information.
        // The end-goal here is that the user never knows that turning their device sideways
        // does crazy lifecycle related things.  It should feel like some stuff stretched out,
        // or magically appeared to take advantage of room, but data or place in the app was never
        // actually *lost*.
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SortBy = prefs.getString(getString(R.string.pref_sorting_key),
                getString(R.string.pref_sorting_default_value));
        updateMovie(SortBy);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
//
//            FetchMovieTask movieTask = new FetchMovieTask();
//            movieTask.execute();

            updateMovie(SortBy);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Mymovie>> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected ArrayList<Mymovie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try {
                //  URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=fe3e7286750acc526fce7a8cbdc9e7c1");
                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String sort_by = "sort_by";
                final String api_key = "api_key";

//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                String SortBy = prefs.getString(getString(R.string.pref_sorting_key),
//                        getString(R.string.pref_sorting_default_value));
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(sort_by, params[0])
                        .appendQueryParameter(api_key, "fe3e7286750acc526fce7a8cbdc9e7c1")
                        .build();
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException j) {
                Log.e(LOG_TAG, "JSON Error", j);
            }
            return null;
        }

        private ArrayList<Mymovie> getMovieDataFromJson(String forecastJsonStr)
                throws JSONException {
            JSONObject movieJson = new JSONObject(forecastJsonStr);
            JSONArray movieArray = movieJson.getJSONArray("results");
            ArrayList<Mymovie> urls = new ArrayList<>();
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);
//                urls.add("http://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));
//                for (String s : urls) {
//                    Log.v(LOG_TAG, "Movie entry" + s);
//                }

                Mymovie movieModel = new Mymovie(movie);
                urls.add(movieModel);
            }
            return urls;
        }


        @Override
        protected void onPostExecute(ArrayList<Mymovie> movies) {
            super.onPostExecute(movies);
            if (movies != null) {
                mMovieAdapter = new ImageAdapter(getActivity(), movies);
                g.setAdapter(mMovieAdapter);
                mMovieAdapter.notifyDataSetChanged();
                if (mPosition != ListView.INVALID_POSITION) {
                    // If we don't need to restart the loader, and there's a desired position to restore
                    // to, do so now.
                    g.smoothScrollToPosition(mPosition);
                }
            }
        }
    }


    class FetchFavoriteMoviesTask extends AsyncTask<Void, Void, ArrayList<Mymovie>> {
        private Context mContext;

        public FetchFavoriteMoviesTask(Context context) {
            mContext = context;
        }

        @Override
        protected ArrayList<Mymovie> doInBackground(Void... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null
            );
            return getFavoriteMoviesDataFromCursor(cursor);
        }

        private ArrayList<Mymovie> getFavoriteMoviesDataFromCursor(Cursor cursor) {
            ArrayList<Mymovie> results = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Mymovie movie = new Mymovie(cursor);
                    results.add(movie);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<Mymovie> movies) {
            super.onPostExecute(movies);
            if (movies != null) {
                mMovieAdapter = new ImageAdapter(getActivity(), movies);
                g.setAdapter(mMovieAdapter);
                mMovieAdapter.notifyDataSetChanged();
                if (mPosition != ListView.INVALID_POSITION) {
                    // If we don't need to restart the loader, and there's a desired position to restore
                    // to, do so now.
                    g.smoothScrollToPosition(mPosition);
                }
            }
        }
    }
}

