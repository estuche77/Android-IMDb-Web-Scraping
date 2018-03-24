package cr.ac.itcr.jlatouche.imdbwebscraping;

import android.graphics.Bitmap;

/**
 * Created by estuche on 21/03/18.
 */

public class Movie {

    private String title;
    private float rating;
    private int metaScore;
    private Bitmap cover;
    private String coverAddress;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(int metaScore) {
        this.metaScore = metaScore;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public String getCoverAddress() { return coverAddress; }

    public void setCoverAddress(String coverAddress) { this.coverAddress = coverAddress; }
}
