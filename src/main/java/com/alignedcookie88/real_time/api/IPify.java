package com.alignedcookie88.real_time.api;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPify { // https://api.ipify.org?format=json


    public static APIResponse makeRequest() {
        try {
            URL url = new URL("https://api.ipify.org?format=json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Gson gson = new Gson();
            return gson.fromJson(in, APIResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static class APIResponse {
        public String ip;

    }
}
