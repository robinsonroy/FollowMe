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
    private int braceletID;

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

    public int getBraceletID() {
        return braceletID;
    }

    public void setBraceletID(int braceletID) {
        this.braceletID = braceletID;
    }
}
