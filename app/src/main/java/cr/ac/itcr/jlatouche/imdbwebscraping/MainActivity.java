package cr.ac.itcr.jlatouche.imdbwebscraping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            TextView metascoreView = view.findViewById(R.id.metaScoreTextView);

            titleView.setText(movies.get(i).getTitle());
            ratingBar.setRating(movies.get(i).getRating());
            metascoreView.setText(movies.get(i).getMetascore());

            return view;
        }
    }
}
