package com.example.al1.friender.fcm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.al1.friender.R;
import com.example.al1.friender.server.GetToken;
import com.example.al1.friender.server.SendMessageRequest;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MatchActivity.this,
                android.R.layout.simple_list_item_1, matchs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String pseudo = parent.getItemAtPosition(position).toString();
                System.out.println("PSEUDO="+pseudo);
                sendMessageto(pseudo, view);
                SharedPreferences sharedPreferences = getSharedPreferences("destinataires", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("dest1", pseudo);
                editor.apply();
            }
        });
    }

    public void sendMessageto(String pseudo, View view){
        //adding the fragment message
        MessageFragment messageFragment = new MessageFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, messageFragment);
        fragmentTransaction.commit();
    }

}
