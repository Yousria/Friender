package com.example.al1.friender.server;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by bench on 09/02/2017.
 */

public class InsertPref extends AsyncTask<String, Void, String> {

    private Context context;

    public InsertPref(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Connection conn = null;
            String url = "jdbc:mysql://10.0.2.2:3306/frienderdata";
            String user = "root";
            String passwd = "esgi2017";
            conn = DriverManager.getConnection(url, user, passwd);
            String query1 = "SELECT pseudo FROM pref WHERE pseudo='"+params[0]+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            if(rs.next()){
                //already exists so update
                String query2 = "UPDATE pref SET resto='"+params[1]+"', sallesport='"+params[2]+
                                "', cinema='"+params[3]+"', theatre='"+params[4]+
                                "', footing='"+params[5]+"', boitenuit='"+params[6]+"' WHERE pseudo='"+params[0]+"'";
                st.executeUpdate(query2);
                st.close();
                conn.close();
                return "preferences modifiées";
            }else{
                //create a new line in the database
                String query3 = "INSERT INTO pref VALUES ('"+params[0]+"', '"+params[1]+"', '"+params[2]+"', '"+
                                params[3]+"', '"+params[4]+"', '"+params[5]+"', '"+params[6]+"')";
                st.executeUpdate(query3);
                st.close();
                conn.close();
                return "preferences enregistrées";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return "erreur dans l'enregistrement des préférences";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

    }
}
