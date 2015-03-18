package com.followme.followme.Http;

import com.followme.followme.Model.Door;
import com.followme.followme.Model.Music;
import com.followme.followme.Model.PlayMusic;
import com.followme.followme.Model.Room;
import com.followme.followme.Model.Speaker;
import com.followme.followme.Model.Test;
import com.followme.followme.Model.User;
import com.followme.followme.MyMusicActivity;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Robinson on 11/02/15.
 */
public interface ApiService {


    /// ----  Room  ----  ///

    //@Header("Cache-Control: This is an awesome header")


    /**
     *
     * @param cb
     *      Callback
     */
    @GET("/api/rooms")///api/rooms")
    public void getRooms(Callback<List<Room>> cb);

    /**
     * Get a room with an id
     *
     * @param id
     *       room id
     * @param cb
     *        Callaback
     */
    @GET("/api/room/{id}")
    public void getRoom(@Path("id") Integer id, Callback<Room> cb); //, @Query("test") String strTest);

    /**
     * Modify aroom
     *
     * @param room
     *          Room to modify
     * @param cb
     *        Callback
     * @return
     */
    @POST("/api/room")
    public void postRoom(@Body Room room, Callback<Room> cb);

    /**
     *
     * put a new room
     *
     * @param newRoom
     *              Room to add
     * @param cb
     *          callback
     */
    @PUT("/api/room")
    public void putRoom(@Body Room newRoom, Callback<Room> cb);

    /**
     * Delete a room
     * @param id
     *         room id
     * @param cb
     *         Callback
     */
    @DELETE("/api/room/{id}")
    public void deleteRoom(@Path("id") Integer id, Callback<Room> cb);


    /// ----  User  ----  ///

    /**
     * get all users
     * @param cb
     *          callback with the user list
     */
    @GET("/api/users")
    public void getUsers(Callback<List<User>> cb);

    /**
     * Get an user with id
     * @param id
     *       user id
     * @param cb
     *       Callback
     */
    @GET("/api/user/{id}")
    public void getUser(@Path("id") Integer id, Callback<User> cb);

    /**
     * modify user
     * @param user
     *      new user information
     * @param cb
     *      Callback
     */
    @POST("/api/user")
    public void postUser(@Body User user, Callback<User> cb);

    /**
     * Put an user
     *
     * @param user
     *          user to put
     * @param cb
     *       Callback
     */
    @PUT("/api/user")
    public void putUser(@Body User user, Callback<User> cb);

    /**
     * Delete an user
     * @param id
     *      user id
     * @param cb
     *       Callback
     */
    @DELETE("/api/user/{id}")
    public void deleteUser(@Path("id") Integer id, Callback<User> cb);



    /// ----  Speaker  ----  ///

    /**
     * Get all rooms
     */
    @GET("/api/speakers")
    public void getspeakers(Callback<List<Speaker>> cb);

    /**
     ** Get a speaker with an id
     * @param id
     *       id of speaker
     * @param cb
     */
    @GET("/api/speaker/{id}")
    public void getSpeaker(@Path("id") Integer id, Callback<Speaker> cb);

    /**
     * Post a speaker
     *
     * @param speaker
     *          speaker to post
     * @param cb
     *      Callback
     */
    @POST("/api/speaker")
    public void postSpeaker(@Body Speaker speaker, Callback<Speaker> cb);

    /**
     * Put a speaker
     *
     * @param speaker
     *          speaker to put
     * @param cb
     *      Callback
     */
    @PUT("/api/speaker")
    public void putSpeaker(@Body Speaker speaker, Callback<Speaker> cb);

    /**
     * Delete speaker with an id
     * @param id
     *       id of speaker delete
     * @param cb
     *      Callback
     */
    @DELETE("/api/speaker/{id}")
    public void deleteSpeaker(@Path("id") Integer id, Callback<Speaker> cb);

    /// ----  Door  ----  ///

    /**
     * Get all doors
     * @return list of door
     */
    @GET("/api/doors")
    public void getDoors(Callback<List<Door>> cb);

    /**
     * Get a door with an id
     *
     * @param id
     *       id of door
     * @param cb
     *      Callback
     */
    @GET("/api/door/{id}")
    public void getDoor(@Path("id") Integer id, Callback<Door> cb);

    /**
     * Post a door
     *
     * @param door
     *          door to post
     * @param cb
     *      Callback
     */
    @POST("/api/door")
    public void postDoor(@Body Door door, Callback<Door> cb);

    /**
     * Put a door
     *
     * @param door
     *          door to put
     * @param cb
     *       Callback
     */
    @PUT("/api/door")
    public void putDoor(@Body Door door, Callback<Room> cb);

    /**
     * Delete door with an id
     * @param id
     *         door id
     * @param cb
     *       Callback
     */
    @DELETE("/api/door/{id}")
    public void deleteDoor(@Path("id") Integer id, Callback<Room> cb);

    /**
     * Get all music
     * @param cb
     *         Callback
     */
    @GET("/api/musics")
    public void getMusics(Callback<List<Music>> cb);

    /**
     * POST a music to play
     * @param playMusic
     *          Object with user who wants to play music and the music
     * @param cb
     */
    @POST("/api/music/play_song")
    public void playMusic(@Body PlayMusic playMusic, Callback<PlayMusic> cb);

    /**
     * Play or pause for music playing
     * @param userID
     *       id of user who wants to stop/play music
     * @param cb
     *      Callback
     */
    @GET("/api/music/play_pause/{id}")
    public void playPause(@Path("id") int userID, Callback<Object> cb);

}
