package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView listView = (ListView) findViewById(R.id.list);
        Adapter adapter = new Adapter();
        listView.setAdapter(adapter);
    }

    private class Adapter extends BaseAdapter{

        private Vector<String> urls;

        public void add(String url){
            urls.add(url);
        }
        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object getItem(int i) {
            return urls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.i("getView","To do");
            return null;
        }
    }

    private class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, String> {

        private ImageView image;
        public AsyncFlickrJSONDataForList(ImageView image) {
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