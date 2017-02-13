package com.example.al1.friender.server;

import android.os.AsyncTask;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by yous on 12/02/2017.
 */

public class GetToken extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        Driver d;
        try {
            d = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(d);
            Connection conn = null;
            String url = "jdbc:mysql://192.168.1.12:3306/frienderdata";
            String user = "esgi";
            String passwd = "esgi2017";
            conn = DriverManager.getConnection(url, user, passwd);
            String query = "SELECT token FROM users WHERE pseudo='"+params[0]+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                String token = rs.getNString("token");
                st.close();
                conn.close();
                return token;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "ERROR token";
    }
}
