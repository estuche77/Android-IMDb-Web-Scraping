package cr.ac.itcr.jlatouche.imdbwebscraping;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final static String link = "http://www.imdb.com/list/ls064079588/";
    private final static int columnCount = 3;

    private final ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView moviesView = findViewById(R.id.moviesView);
        moviesView.setNumColumns(columnCount);

        moviesView.setAdapter(new MoviesAdapter());

        try {
            String html = new HttpGetRequest().execute(link).get();

            Log.d("HTML", "onCreate: " + html);

            Document document = Jsoup.parse(html);

            Elements titlesParagraph = document.select("div.lister-item-content h3 a");
            Elements ratingsBars = document.select("div.lister-item-content div div strong");
            Elements metaScoresNumber = document.select("span.metascore.favorable");
            Elements coversSources = document.select("div.lister-item-image a img");

            for (Element e: metaScoresNumber) {
                Log.d("Meta", "onCreate: " + e.text());
                Log.d("size", "onCreate: " + String.valueOf(metaScoresNumber.size()));
            }

            for (int i = 0; i < titlesParagraph.size(); i++) {
                Movie movie = new Movie();

                String title = titlesParagraph.get(i).text();
                float rating = Float.parseFloat(ratingsBars.get(i).text());
                //int metaScore = Integer.parseInt(metaScoresNumber.get(i).text());

                movie.setTitle(title);
                movie.setRating(rating);
                movie.setMetascore(6);

                movies.add(movie);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public class GetBitmap extends AsyncTask<String, Void, Bitmap> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected Bitmap doInBackground(String... urls){
            String stringUrl = urls[0];
            Bitmap data;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                data = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                data = null;
            }

            return data;


        }
    }

    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... urls){
            String stringUrl = urls[0];
            String data;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                data = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                data = null;
            }
            return data;
        }
    }

    public class MoviesAdapter extends BaseAdapter {

        public MoviesAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return movies.size();
        }

        @Override
        public Object getItem(int i) {
            return movies.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            if (view == null) {
                view = inflater.inflate(R.layout.movie_item, null);
            }

            ImageView coverView = view.findViewById(R.id.coverImageView);
            TextView titleView = view.findViewById(R.id.titleTextView);
            RatingBar ratingBar = view.findViewById(R.id.ratingBar);
            TextView metaScoreView = view.findViewById(R.id.metaScoreTextView);

            titleView.setText(movies.get(i).getTitle());
            ratingBar.setRating(movies.get(i).getRating() / 2);
            metaScoreView.setText(String.valueOf(movies.get(i).getMetascore()));

            return view;
        }
    }
}
