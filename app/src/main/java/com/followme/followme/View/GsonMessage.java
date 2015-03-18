package com.followme.followme.View;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by Robinson on 18/03/15.
 */
public class GsonMessage {

    public GsonMessage(){}

    public static String getMessage(RetrofitError error){
        String json = new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jobject = jelement.getAsJsonObject();
        String message = jobject.getAsJsonPrimitive("message").toString();
        return message;
    }
}
