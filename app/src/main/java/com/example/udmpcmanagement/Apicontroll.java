package com.example.udmpcmanagement;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Apicontroll{

    // Apicontroll.getLastId() 로 사용
    public static int getLastId() {

        try {
            URL url = new URL("http://:8000/pcno/1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String str;

            if (conn.getResponseCode() == conn.HTTP_OK) {
                System.out.println("SUCCESS");

                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                while ((str = reader.readLine()) != null) {
                    return Integer.parseInt(str.replace("[","").replace("]",""));
                }
            } else {
                System.out.println(conn.getResponseCode() + "ERROR");
            }

        } catch (IOException ignored) { }
        return -1;
    }

    // Apicontroll.getPC(pcId) 로 사용
    public static List<String> getPC(int pcId) {
        try {
            URL url = new URL("http://:8000/pcread/" + pcId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            List<String> pc = new ArrayList<>();
            String str;

            if (conn.getResponseCode() == conn.HTTP_OK) {
                System.out.println("SUCCESS");

                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                    pc.add(str);
                }
                System.out.println("receiveMsg : " + buffer.toString());
                reader.close();
            } else {
                System.out.println(conn.getResponseCode() + "ERROR");
            }

            return pc;
        } catch (IOException e) { }
        return null;
    }

    // Apicontroll.addPC(pcId, username, cpu, ram, gpu) 로 사용
    public static void addPC(int pcId, String username, String cpu, String ram, String gpu) {
        try {
            String params = "?username=" + username + "&cpu=" + cpu + "&ram=" + ram + "&gpu=" + gpu;
            URL url = new URL("http:///pcadd/" + pcId + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String str;

            if (conn.getResponseCode() == conn.HTTP_OK) {
                System.out.println("SUCCESS");
            } else {
                System.out.println(conn.getResponseCode() + "ERROR");
            }
        } catch (IOException e) {}
    }
}
