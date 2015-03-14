package com.followme.followme.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Robinson on 11/02/15.
 */
@Parcel
public class RFSensor {

    @SerializedName("id")
    private long id;
    @SerializedName("room")
    private Room room;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
