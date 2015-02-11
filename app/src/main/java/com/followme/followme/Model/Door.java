package com.followme.followme.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Robinson on 11/02/15.
 */
@Parcel
public class Door {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
    @SerializedName("sensor1")
    private RFSensor sensor1;
    @SerializedName("sensor2")
    private RFSensor sensor2;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RFSensor getSensor1() {
        return sensor1;
    }

    public RFSensor getSensor2() {
        return sensor2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSensor1(RFSensor sensor1) {
        this.sensor1 = sensor1;
    }

    public void setSensor2(RFSensor sensor2) {
        this.sensor2 = sensor2;
    }



}
