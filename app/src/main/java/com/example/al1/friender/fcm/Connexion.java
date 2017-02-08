package com.example.al1.friender.fcm;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Created by yous on 06/02/2017.
 */

public class Connexion extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {

        try {
            Socket socket = new Socket(InetAddress.getByName(urls[0]), 8080);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            out.write("bonjour je suis le client\n");
            //System.out.println(in.readLine());
            //socket.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}




