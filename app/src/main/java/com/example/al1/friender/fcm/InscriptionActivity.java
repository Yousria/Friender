package com.example.al1.friender.fcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.al1.friender.R;
import com.example.al1.friender.server.InsertUser;
import com.example.al1.friender.server.PseudoExists;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class InscriptionActivity extends AppCompatActivity {

    private static final String TAG = "InscriptionAcitivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button button = (Button) findViewById(R.id.valider);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger();

            }
        });
    }

    public void logger(){
        EditText editText1 = (EditText) findViewById(R.id.email1);
        String email = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.mdp1);
        String mdp = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.pseudo);
        String pseudo = editText3.getText().toString();
        if(createUser(email, mdp, pseudo, editText1, editText2, editText3)){
            new InsertUser(InscriptionActivity.this).execute("192.168.1.12", pseudo, email, mdp, FirebaseInstanceId.getInstance().getToken());
            SharedPreferences user = getSharedPreferences("MyId", 0);
            SharedPreferences.Editor editor = user.edit();
            editor.putString("pseudo", pseudo);
            editor.apply();
            System.out.println(user.getString("pseudo", "p"));
            authUser(email, mdp);
        }

        //Database.insertIntoUsers(email, "Yousria", FirebaseInstanceId.getInstance().getToken());
    }

    public boolean createUser(String email, String mdp, String pseudo, EditText t1, EditText t2, EditText t3){

        if(isValide(email, mdp, pseudo, t1, t2, t3)) {
            MainActivity.mAuth.createUserWithEmailAndPassword(email, mdp).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Toast.makeText(InscriptionActivity.this, "FAILED CREATEUSER", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(InscriptionActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            return false;
        }
        return true;
    }

    public boolean isValide(String email, String password, String pseudo, EditText t1, EditText t2, EditText t3) {
        boolean valide = true;
        try {
            if (TextUtils.isEmpty(pseudo)) {
                t3.setError("Required!");
                valide = false;
            } else {
                boolean exists = new PseudoExists().execute(pseudo).get();
                if (exists) {
                    t3.setError("Already exists !!");
                    valide = false;
                } else {
                    t3.setError(null);
                }
            }
        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        if(TextUtils.isEmpty(email)){
            t1.setError("Required!");
            valide = false;
        }else{
            t1.setError(null);
        }

        if(TextUtils.isEmpty(password) || password.length() < 8){
            t2.setError("Required (at least 8 chars)");
            valide = false;
        }else{
            t2.setError(null);
        }
        return valide;
    }

    public void authUser(String email, String mdp){

        //connexion
        MainActivity.mAuth.signInWithEmailAndPassword(email, mdp)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if(!task.isSuccessful()){
                            Toast.makeText(InscriptionActivity.this, "FAILED AUTH", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        Intent intent = new Intent(this, ChoicesActivity.class);
        startActivity(intent);

    }



}
