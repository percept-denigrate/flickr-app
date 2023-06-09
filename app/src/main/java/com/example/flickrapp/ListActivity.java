package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView listView = (ListView) findViewById(R.id.list);
        Adapter adapter = new Adapter();
        listView.setAdapter(adapter);
        String url = "https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json";
        new AsyncFlickrJSONDataForList(adapter).execute(url);
    }

    public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, String> {

        private ListView list;
        private Adapter adapter;
        public AsyncFlickrJSONDataForList(Adapter adapter) {
            this.list = list;
            this.adapter = adapter;
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
                JSONArray array = json.getJSONArray("items");
                int i;
                for (i = 0; i < array.length(); i++){
                    url = array
                        .getJSONObject(1)
                        .getJSONObject("media")
                        .getString("m");
                    Log.i("url: ", url);
                    adapter.add(url);
                }
                //new AsyncBitmapDownloader(this.image).execute(url);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
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