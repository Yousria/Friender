package com.example.al1.friender.fcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.al1.friender.R;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Created by yous on 07/02/2017.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static FirebaseAuth mAuth = Database.mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new Databases();
        //new Connexion().execute("10.0.2.2");
        //System.out.println(FirebaseInstanceId.getInstance().getToken());
        final Intent i2 = new Intent(this, InscriptionActivity.class);
        Button inscr = (Button) findViewById(R.id.inscription);
        inscr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });
        final Intent i = new Intent(this, ConnexionActivity.class);
        Button conex = (Button) findViewById(R.id.connex);
        conex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);

            }
        });

    }







}


