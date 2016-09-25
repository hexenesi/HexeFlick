package com.hexenesi.hexeflick.requests;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hexenesi.hexeflick.events.SearchEvent;
import com.hexenesi.hexeflick.model.Photos;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by jpcazarez on 25/09/16.
 */

public class FlickrSearches {

    private static final String key = "fxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1";
    public static final String RECENTS = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=" + key + "&format=json&nojsoncallback=1";
    public static final String SEARCHTAG = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" + key + "&tags=%1$s&format=json&nojsoncallback=1";


    private static final OkHttpClient client = new OkHttpClient();
    public void get(String url, final Class<?> clase) {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                switch (response.code()) {
                    case 200:

                        String string = response.body().string();
                        if (clase != null) {
                            Gson gson = new Gson();
                            Log.d("Resultado", string);

                            try {
                                EventBus.getDefault().post(new SearchEvent(gson.fromJson(string,Photos.class)));
                            } catch (JsonSyntaxException | IllegalStateException e) {
                                e.printStackTrace();

                            }

                        }

                        break;
                    default:

                        break;
                }

            }
        });
    }
}
