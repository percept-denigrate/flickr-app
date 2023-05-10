package com.example.flickrapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Vector;

public class Adapter extends BaseAdapter {

    private Vector<String> urls = new Vector<String>();

    public void add(String url) {
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
        Log.i("getView", "To do");
        return null;
    }
}
