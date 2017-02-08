package com.example.al1.friender.fcm;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yous on 06/02/2017.
 */
@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public String token;

    public User(){

    }
    public User(String n, String e, String token){
        name = n;
        email = e;
        this.token = token;

    }
}
