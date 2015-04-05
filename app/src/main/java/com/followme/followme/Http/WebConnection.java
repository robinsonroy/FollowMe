package com.followme.followme.Http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Robinson on 15/02/15.
 */
public class WebConnection {
    private ApiService api;

    public WebConnection()
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://172.20.10.10/web/app.php")//"http://followme.techpaf.com")//
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        api = adapter.create(ApiService.class);
    }

    public ApiService getApi() {
        return api;
    }
}
