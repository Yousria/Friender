package com.example.al1.friender.server;

import android.os.AsyncTask;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by bench on 09/02/2017.
 */

public class SendRequetMatch extends AsyncTask <String, Void, ArrayList<String>>{

    @Override
    protected ArrayList doInBackground(String...params) {
        ArrayList match = new ArrayList<String>();

        try {
            Connection conn = null;
            String url = "jdbc:mysql://192.168.1.12:3306/frienderdata";
            String user = "esgi";
            String passwd = "esgi2017";
            conn = DriverManager.getConnection(url, user, passwd);
            Statement st = conn.createStatement();
            //change request !!!!!!!!!!!!!
            String query2 = "SELECT pseudo FROM pref WHERE ((resto='"+params[0]+"' AND resto='true' AND pseudo<>'"+params[6]+"') OR (footing='"+params[1]+
                            "' AND footing='true' AND pseudo<>'"+params[6]+"') OR (theatre='"+params[2]+"' AND theatre='true' AND pseudo<>'"+params[6]+"') OR (boitenuit='"+params[3]+
                            "' AND boitenuit='true' AND pseudo<>'"+params[6]+"') OR (cinema='"+params[4]+
                            "' AND cinema='true' AND pseudo<>'"+params[6]+"') OR (sallesport='"+params[5]+"' AND sallesport='true' AND pseudo<>'"+params[6]+"'))";
            ResultSet rs = st.executeQuery(query2);
            while(rs.next()){
                match.add(rs.getNString("pseudo"));
            }
            st.close();
            conn.close();

            //send Notification
            URL url2 = new URL(DBUtils.URL_FCM);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url2.openConnection();
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setRequestProperty("Authorization", "key="+DBUtils.AUTH_KEY_FCM);
            httpsURLConnection.setRequestProperty("Content-Type", "application/json");
            JSONObject json = new JSONObject();
            json.put("to", FirebaseInstanceId.getInstance().getToken());
            JSONObject json2 = new JSONObject();
            json2.put("title", "Nombre de match");
            json2.put("body", "vous avez "+match.size()+" matchs");
            json.put("notification", json2);
            OutputStreamWriter out = new OutputStreamWriter(httpsURLConnection.getOutputStream());
            out.write(json.toString());
            out.flush();
            out.close();
            int responseCode = httpsURLConnection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url2);
            System.out.println("Post parameters : " + json);
            System.out.println("Response Code : " + responseCode);
            httpsURLConnection.disconnect();
        }catch(SQLException | IOException | JSONException e){
            e.printStackTrace();
        }
        return match;
    }
}
