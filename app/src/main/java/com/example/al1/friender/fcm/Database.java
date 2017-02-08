package com.example.al1.friender.fcm;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by yous on 06/02/2017.
 */

public class Database {

    public static FirebaseDatabase DB = FirebaseDatabase.getInstance();
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static int cpt = 0;

    public static void insertIntoUsers(String email, String name, String token){
        DatabaseReference ref = DB.getReference("users");
        User user = new User(name, email, token);
        ref.child(String.valueOf(cpt)).setValue(user);
        cpt++;

    }
}
