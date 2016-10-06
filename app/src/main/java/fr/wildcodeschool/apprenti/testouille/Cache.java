package fr.wildcodeschool.apprenti.testouille;

/**
 * Created by Shooty31 on 06/10/2016.
 */

public class Cache {
    private String hint;
    private Float lat;
    private Float lon;

    public Cache() {
    }

    public Cache(Float lat, Float lon, String hint) {
        this.hint = hint;
        this.lat = lat;
        this.lon = lon;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getHint() {
        return hint;
    }


    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }
}




