package com.example.al1.friender.server;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by yous on 12/02/2017.
 */

public class SendMessageRequest extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        //send Notification
        try {
            URL url = new URL(DBUtils.URL_FCM);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setRequestProperty("Authorization", "key=" + DBUtils.AUTH_KEY_FCM);
            httpsURLConnection.setRequestProperty("Content-Type", "application/json");
            JSONObject json = new JSONObject();
            json.put("message", params[1]);
            JSONObject json2 = new JSONObject();
            json2.put("to", params[0]);
            json2.put("data", json);
            OutputStreamWriter out = new OutputStreamWriter(httpsURLConnection.getOutputStream());
            out.write(json2.toString());
            out.flush();
            out.close();
            int responseCode = httpsURLConnection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + json2);
            System.out.println("Response Code : " + responseCode);
            httpsURLConnection.disconnect();
        }catch(IOException | JSONException e){
            e.printStackTrace();
        }
        return "message envoyé";
    }
}
