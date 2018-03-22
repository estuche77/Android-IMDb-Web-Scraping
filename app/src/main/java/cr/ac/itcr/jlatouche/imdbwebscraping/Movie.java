package cr.ac.itcr.jlatouche.imdbwebscraping;

import android.graphics.Bitmap;

/**
 * Created by estuche on 21/03/18.
 */

public class Movie {

    private short id;
    private String title;
    private short year;
    private float rating;
    private short metascore;
    private Bitmap cover;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public short getMetascore() {
        return metascore;
    }

    public void setMetascore(short metascore) {
        this.metascore = metascore;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }
}
