package com.example.al1.friender.server;

import android.os.AsyncTask;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by yous on 10/02/2017.
 */

public class PseudoExists extends AsyncTask <String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        Driver d;
        try {
            d = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(d);
            Connection conn = null;
            String url = "jdbc:mysql://192.168.1.12:3306/frienderdata";
            String user = "esgi";
            String passwd = "esgi2017";
            conn = DriverManager.getConnection(url, user, passwd);
            Statement st = conn.createStatement();
            String query = "SELECT pseudo FROM users WHERE pseudo='"+params[0]+"'";
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                st.close();
                conn.close();
                return true;
            }else{
                st.close();
                conn.close();
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
