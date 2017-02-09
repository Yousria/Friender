package com.example.al1.friender.fcm;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.al1.friender.R;
import com.example.al1.friender.server.InsertPref;

/**
 * Created by yous on 07/02/2017.
 */
public class ChoicesActivity extends AppCompatActivity {

    public static String resto = "false";
    public static String cinema = "false";
    public static String sport = "false";
    public static String footing = "false";
    public static String theatre = "false";
    public static String boite = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        Button button = (Button) findViewById(R.id.validerChoix);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCheck();
            }
        });

    }


    public void verifyCheck(){
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.resto);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.cinema);
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.sport);
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.theatre);
        CheckBox checkBox5 = (CheckBox) findViewById(R.id.footing);
        CheckBox checkBox6 = (CheckBox) findViewById(R.id.boite);

        if(checkBox1.isChecked()){
            resto = "true";
        }else{
            resto = "false";
        }
        if(checkBox2.isChecked()){
            cinema = "true";
        }else{
            cinema = "false";
        }
        if(checkBox3.isChecked()){
            sport = "true";
        }else{
            sport = "false";
        }
        if(checkBox4.isChecked()){
            theatre = "true";
        }else{
            theatre = "false";
        }
        if(checkBox5.isChecked()){
            footing = "true";
        }else{
            footing = "false";
        }
        if(checkBox6.isChecked()){
            boite = "true";
        }else{
            boite = "false";
        }
        SharedPreferences user = getSharedPreferences("MyId", 0);
        String pseudo = user.getString("pseudo", "ps");
        new InsertPref(ChoicesActivity.this).execute(pseudo, resto, sport, cinema, theatre, footing, boite);

    }
}
