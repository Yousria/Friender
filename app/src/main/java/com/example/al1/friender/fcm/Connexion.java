package com.example.al1.friender.fcm;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by yous on 06/02/2017.
 */

public class Connexion extends AsyncTask<String, Void, String> {

    public static String FCM = "https://fcm.googleapis.com/fcm/send";
    public static String API_KEY = " AIzaSyD7hny8c95qI1ar8QFVCWHUqTuVzu3Aa44";
    public static String SERVER_KEY = "AAAA8nPS-Xw:APA91bEYmc9DA5NQrN_DDxDz5xA3hy3qDBPc6GQKAiuXqOgKYeLty3-QiICrbRpwhpN2TZUm49dZi-Jg2jA6Nk66Ev3fGYEujCqWwN0f3kfWOPCGajofjtoegd37elxjLm8HxkML3E6zQ2_pWsT7bSspUmjaoXN5Xw";
    @Override
    protected String doInBackground(String... urls) {

        try {
            Socket socket = new Socket(InetAddress.getByName(urls[0]), 8080);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream theOutputStream = new PrintStream(socket.getOutputStream());
            theOutputStream.println("bonjour je suis le client\n");
            System.out.println(in.readLine());
            socket.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}



