package com.followme.followme.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Robinson on 18/03/15.
 */
@Parcel
public class PlayMusic {

    public PlayMusic() {
    }

    public PlayMusic(Music music, User user) {
        this.music = music;
        this.user = user;
    }

    @SerializedName("music")
    private Music music;

    @SerializedName("user")
    private User user;


}
