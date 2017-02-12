package com.example.al1.friender.server;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by yous on 08/02/2017.
 */

public class DBUtils extends AsyncTask<String, Void, Connection>{

    public static String AUTH_KEY_FCM =  "AAAA8nPS-Xw:APA91bEYmc9DA5NQrN_DDxDz5xA3hy3qDBPc6GQKAiuXqOgKYeLty3-QiICrbRpwhpN2TZUm49dZi-Jg2jA6Nk66Ev3fGYEujCqWwN0f3kfWOPCGajofjtoegd37elxjLm8HxkML3E6zQ2_pWsT7bSspUmjaoXN5Xw";
    public static String URL_FCM = "https://fcm.googleapis.com/fcm/send";
    public static String API_KEY;

    @Override
    protected Connection doInBackground(String... params) {
        Connection conn = null;
        Driver d;
        try {
            d = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(d);
            String url = "jdbc:mysql://"+params[0]+":3306/frienderdata";
            String user = "esgi";
            String passwd = "esgi2017";
            conn = (Connection) DriverManager.getConnection(url, user, passwd);
            System.out.println("Connexion effective !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
