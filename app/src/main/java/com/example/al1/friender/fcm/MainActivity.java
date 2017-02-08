package com.example.al1.friender.fcm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
/**
 * Created by yous on 07/02/2017.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static FirebaseAuth mAuth = Database.mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Connexion().execute("10.0.2.2");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user connected
                    Log.d(TAG, "onAuthStateChanged:Signed_in" + user.getUid());
                }else{
                    //user disconnected
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };


        //new Databases();
        //new Connexion().execute("10.0.2.2");
        //System.out.println(FirebaseInstanceId.getInstance().getToken());

        Button inscr = (Button) findViewById(R.id.inscription);
        inscr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText1 = (EditText) findViewById(R.id.email);
                final String email = editText1.getText().toString();
                final EditText editText2 = (EditText) findViewById(R.id.mdp);
                final String mdp = editText2.getText().toString();
                createUser(email, mdp, editText1, editText2);
                Database.insertIntoUsers(email, "Yousria", FirebaseInstanceId.getInstance().getToken());
            }
        });

        Button conex = (Button) findViewById(R.id.connex);
        conex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText1 = (EditText) findViewById(R.id.email);
                final String email = editText1.getText().toString();
                final EditText editText2 = (EditText) findViewById(R.id.mdp);
                final String mdp = editText2.getText().toString();
                authUser(email, mdp);

            }
        });

    }

    public void createUser(String email, String mdp, EditText t1, EditText t2){

        if(isValide(email, mdp, t1, t2)) {
            mAuth.createUserWithEmailAndPassword(email, mdp).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "FAILED CREATEUSER", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void authUser(String email, String mdp){

        //connexion
        mAuth.signInWithEmailAndPassword(email, mdp)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "FAILED AUTH", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Intent intent = new Intent(this, ChoicesActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
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


}


