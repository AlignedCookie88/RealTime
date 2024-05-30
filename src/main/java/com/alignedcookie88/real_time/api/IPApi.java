package com.alignedcookie88.real_time.api;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPApi {


    public static APIResponse makeRequest(String ip) {
        try {
            URL url = new URL(String.format("http://ip-api.com/json/%s", ip));
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

    public static APIResponse makeRequestMe() {
        IPify.APIResponse myIp = IPify.makeRequest();
        return makeRequest(myIp.ip);
    }

    public static class APIResponse {

        public String query;

        public String status;

        public String country;

        public String countryCode;

        public String region;

        public String regionName;

        public String city;

        public String zip;

        public Float lat;

        public Float lon;

        public String timezone;

        public String isp;

        public String org;

        public String as;

    }

}
