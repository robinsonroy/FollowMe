package com.followme.followme.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Robinson on 11/02/15.
 */
@Parcel
public class User {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("braceletID")
    private long braceletID;

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBraceletID() {
        return braceletID;
    }

    public void setBraceletID(long braceletID) {
        this.braceletID = braceletID;
    }
}
