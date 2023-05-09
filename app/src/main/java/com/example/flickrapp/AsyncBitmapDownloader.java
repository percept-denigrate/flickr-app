package com.example.flickrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    private ImageView image;

    public AsyncBitmapDownloader(ImageView image) {
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        int n = strings.length;
        Bitmap bitmap = null;
        for (int i = 0; i < n; i++) {
            try {
                URL url = new URL(strings[i]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream s = new BufferedInputStream(urlConnection.getInputStream());
                    bitmap = BitmapFactory.decodeStream(s);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isCancelled()) break;
        }
        return bitmap;
    }
}

