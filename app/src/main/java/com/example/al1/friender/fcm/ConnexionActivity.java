package com.example.al1.friender.fcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ConnexionActivity extends AppCompatActivity {

    private static final String TAG = "ConnexionAcitivty" ;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);


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

        Button button = (Button) findViewById(R.id.entrer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText1 = (EditText) findViewById(R.id.email2);
                final String email = editText1.getText().toString();
                final EditText editText2 = (EditText) findViewById(R.id.mdp2);
                final String mdp = editText2.getText().toString();
                authUser(email, mdp);
            }
        });
    }


    public void authUser(String email, String mdp){

        //connexion
        MainActivity.mAuth.signInWithEmailAndPassword(email, mdp)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if(!task.isSuccessful()){
                            Toast.makeText(ConnexionActivity.this, "FAILED AUTH", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        //new Connexion().execute("10.0.2.2");
        Intent intent = new Intent(this, ChoicesActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            MainActivity.mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
