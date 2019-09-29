package com.evolve.server.vk;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@Component
public class Getter {

    public HashMap getFriends(Integer userId){
        try {
            URL uri = new URL("https://api.vk.com/method/friends.get?user_id=" +
                    userId + //"68098233" +
                    "&access_token=54bf2ba554bf2ba554bf2ba54654d2312e554bf54bf2ba5093127cb0781d5dcb8a8e80e" +
                    "&v=5.52");
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
//            System.out.println(response.toString());
            Gson g = new Gson();
            return g.fromJson(response.toString(), HashMap.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new HashMap();
    }

    void sendAndReceive(String url){
        try{
        URL uri = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
//        return null;
    } catch (Exception e){
        e.printStackTrace();
    }
    }
}

class JSONGetter {

    protected HashMap getJSON(String url_name) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(url_name);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status = connection.getResponseCode();
            String resultJSON = "";
            switch (status) {
                // Successful cases
                case 0:
                case 200:
                case 201:
                case 203:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    System.out.println(sb.toString());
                    br.close();
                    Gson g = new Gson();
                    return g.fromJson(sb.toString(), HashMap.class);
            }
        } catch (MalformedURLException ex) {
            System.out.println(" URL EXCEPTION!!!!! " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println(" IO EXCEPTION!!!!! " + ex.getMessage());
        } finally {
            if (connection != null)
                try {
                    connection.disconnect();
                } catch (Exception ex) {
                    System.out.println("CONNECTION EXCEPTION!!! " + ex.getMessage() + "at" + ex.getStackTrace());
                }
        }
        return null;
    }
}
