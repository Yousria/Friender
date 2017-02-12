package com.example.al1.friender.server;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.al1.friender.fcm.InscriptionActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by yous on 09/02/2017.
 */

public class InsertUser extends AsyncTask<String, Void, String> {

    private Context context;

    public InsertUser(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String ... params) {
        try {
            Connection conn = null;
            String url = "jdbc:mysql://" + params[0] + ":3306/frienderdata";
            String user = "esgi";
            String passwd = "esgi2017";
            conn = DriverManager.getConnection(url, user, passwd);
            String query1 = "SELECT id FROM users WHERE token='"+params[4]+"'";

            String query = "INSERT INTO users VALUES (null, '" + params[1] + "', '" +
                    params[2] + "', '" + params[3] + "', '" + params[4] + "')";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            if(rs.next()){
                st.close();
                conn.close();
                return "USER deja existant";
            }else {
                st.execute(query);
            }
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User Ajouté avec succès";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
