package com.followme.followme.Http;

import com.followme.followme.Model.Door;
import com.followme.followme.Model.Room;
import com.followme.followme.Model.Speaker;
import com.followme.followme.Model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Robinson on 11/02/15.
 */
public interface ApiService {


    /// ----  Room  ----  ///

    //@Header("Cache-Control: This is an awesome header")

    /**
     * Get all rooms
     * @return list of room
     */
    @GET("/api/rooms")
    public List<Room> getRooms();

    /**
     * Get a room with an id
     *
     * @param id
     * @return room
     */
    @GET("/api/rooms/{id}")
    public Room getRoom(@Path("id") Integer id); //, @Query("test") String strTest);

    /**
     * Post a room
     *
     * @param room
     *          Room to post
     * @param b
     *      Callback
     * @return room
     */
    @POST("/api/rooms")
    public Room postRoom(@Body Room room, Callback<Room> b);

    /**
     * Put a room
     *
     * @param room
     *          Room to put
     * @param cb
     *       Callback
     * @return room put
     */
    @PUT("/api/rooms")
    public Room putRoom(@Body Room room, Callback<Room> cb);

    /**
     * Delete room with an id
     * @param id
     *       id of room delete
     * @return true if delete ok, false else
     */
    @DELETE("/api/rooms/{id}")
    public boolean deleteRoom(@Path("id") Integer id);


    /// ----  User  ----  ///


    /**
     * Get all users
     * @return list of user
     */
    @GET("/api/users")
    public List<User> getUsers();

    /**
     * Get an user with an id
     *
     * @param id
     *       id of user
     * @return user
     */
    @GET("/api/users/{id}")
    public User getUser(@Path("id") Integer id);

    /**
     * Post an user
     *
     * @param user
     *          user to post
     * @param b
     *      Callback
     * @return user
     */
    @POST("/api/users")
    public User postUser(@Body User user, Callback<User> b);

    /**
     * Put an user
     *
     * @param user
     *          user to put
     * @param cb
     *       Callback
     * @return user put
     */
    @PUT("/api/users")
    public User putUser(@Body User user, Callback<Room> cb);

    /**
     * Delete user with an id
     * @param id
     *       id of user delete
     * @return true if delete ok, false else
     */
    @DELETE("/api/users/{id}")
    public boolean deleteUser(@Path("id") Integer id);



    /// ----  Speaker  ----  ///

    /**
     * Get all speakers
     * @return list of speaker
     */
    @GET("/api/speakers")
    public List<Speaker> getSpeakers();

    /**
     * Get a speaker with an id
     *
     * @param id
     *       id of speaker
     * @return speaker
     */
    @GET("/api/speaker/{id}")
    public Speaker getSpeaker(@Path("id") Integer id);

    /**
     * Post a speaker
     *
     * @param speaker
     *          speaker to post
     * @param b
     *      Callback
     * @return speaker
     */
    @POST("/api/speakers")
    public Speaker postSpeaker(@Body Speaker speaker, Callback<User> b);

    /**
     * Put a speaker
     *
     * @param speaker
     *          speaker to put
     * @param cb
     *       Callback
     * @return speaker put
     */
    @PUT("/api/speakers")
    public Speaker putSpeaker(@Body Speaker speaker, Callback<Room> cb);

    /**
     * Delete speaker with an id
     * @param id
     *       id of speaker delete
     * @return true if delete ok, false else
     */
    @DELETE("/api/speakers/{id}")
    public boolean deleteSpeaker(@Path("id") Integer id);




    /// ----  Door  ----  ///

    /**
     * Get all doors
     * @return list of door
     */
    @GET("/api/doors")
    public List<Door> getDoors();

    /**
     * Get a door with an id
     *
     * @param id
     *       id of door
     * @return door
     */
    @GET("/api/doors/{id}")
    public Door getDoor(@Path("id") Integer id);

    /**
     * Post a door
     *
     * @param door
     *          door to post
     * @param b
     *      Callback
     * @return door
     */
    @POST("/api/doors")
    public Door postDoor(@Body Door door, Callback<User> b);

    /**
     * Put a door
     *
     * @param door
     *          door to put
     * @param cb
     *       Callback
     * @return door put
     */
    @PUT("/api/doors")
    public Door putDoor(@Body Door door, Callback<Room> cb);

    /**
     * Delete door with an id
     * @param id
     *       id of door delete
     * @return true if delete ok, false else
     */
    @DELETE("/api/doors/{id}")
    public boolean deleteDoor(@Path("id") Integer id);





}
