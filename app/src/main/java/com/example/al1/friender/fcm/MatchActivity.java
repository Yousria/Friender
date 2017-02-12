package com.example.al1.friender.fcm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.al1.friender.R;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        ArrayList<String> matchs = new ArrayList<String>();
        Bundle b = getIntent().getExtras();
        if(b != null){
            matchs = b.getStringArrayList("match");
        }

        ListView listView = (ListView) findViewById(R.id.list_match);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MatchActivity.this, android.R.layout.simple_list_item_1, matchs);
        listView.setAdapter(adapter);


    }

}
