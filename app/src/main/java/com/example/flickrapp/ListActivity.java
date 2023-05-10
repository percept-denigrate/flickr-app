package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

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
            return null;
        }
    }
}