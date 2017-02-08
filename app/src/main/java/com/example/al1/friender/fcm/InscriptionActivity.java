package com.example.al1.friender.fcm;

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

public class InscriptionActivity extends AppCompatActivity {

    private static final String TAG = "InscriptionAcitivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button button = (Button) findViewById(R.id.Valider);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger();
                //sendUserToServer();
            }
        });
    }

    public void logger(){
        final EditText editText1 = (EditText) findViewById(R.id.email1);
        final String email = editText1.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.mdp1);
        final String mdp = editText2.getText().toString();
        createUser(email, mdp, editText1, editText2);
        //Database.insertIntoUsers(email, "Yousria", FirebaseInstanceId.getInstance().getToken());
    }

    public void createUser(String email, String mdp, EditText t1, EditText t2){

        if(isValide(email, mdp, t1, t2)) {
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
        }
    }

    public boolean isValide(String email, String password, EditText t1, EditText t2){
        boolean valide = true;

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

    /*public void sendUserToServer(){
        EditText editText1 = (EditText) findViewById(R.id.pseudo);
        String pseudo = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.email1);
        String email = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.mdp1);
        String mdp = editText3.getText().toString();
        Connexion conn = new Connexion();
        JSONObject json = new JSONObject();
        try {
            json.put("pseudo", pseudo);
            json.put("email", email);
            json.put("mdp", mdp);
            json.put("token", FirebaseInstanceId.getInstance().getToken());
            conn.execute("10.0.2.2");
            conn.socket = new Socket(InetAddress.getByName("10.0.2.2"), 8080);
            OutputStreamWriter out = new OutputStreamWriter(conn.socket.getOutputStream());
            out.write(json.toString());
            out.flush();
            out.close();
            conn.socket.close();
        }catch(JSONException | IOException e){
            e.printStackTrace();
        }
    }*/

}
