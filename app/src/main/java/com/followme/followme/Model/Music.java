package com.followme.followme.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Robinson on 18/03/15.
 */
@Parcel
public class Music {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("artist")
    private String artiste;

    @SerializedName("url")
    private String url;

    public Music() {
    }

    @Override
    public String toString() {
        return  artiste + " | " +
                name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtiste() {
        return artiste;
    }
}
