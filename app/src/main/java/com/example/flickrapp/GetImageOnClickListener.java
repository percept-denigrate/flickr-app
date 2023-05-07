package com.example.flickrapp;

import android.os.AsyncTask;
import android.view.View;


public class GetImageOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        new AsyncFlickrJSONData().execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
    }

    private class AsyncFlickrJSONData extends AsyncTask<String, Void, String> {


    }
}
