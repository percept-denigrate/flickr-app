package com.example.flickrapp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.arch.core.internal.SafeIterableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetImageOnClickListener implements View.OnClickListener {

    private ImageView image;
    GetImageOnClickListener(ImageView image) {
        this.image = image;
    }
        @Override
    public void onClick(View view) {
        new AsyncFlickrJSONData(this.image).execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
    }

    private class AsyncFlickrJSONData extends AsyncTask<String, Void, String> {

        private ImageView image;
        public AsyncFlickrJSONData(ImageView image) {
            this.image = image;
        }

            @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            String jsonString = result.substring("jsonFlickrFeed(".length(), result.length() - 1);
            Log.d("JSON Response", jsonString);
            JSONObject json = null;
            try {
                json = new JSONObject(jsonString);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            String url = null;
            try {
                url = json
                        .getJSONArray("items")
                        .getJSONObject(1)
                        .getJSONObject("media")
                        .getString("m");
                new AsyncBitmapDownloader(this.image).execute(url);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Log.i("url: ", url);
        }
    }
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
